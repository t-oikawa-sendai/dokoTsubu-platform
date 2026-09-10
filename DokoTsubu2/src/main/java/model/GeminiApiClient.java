/*
 * DATE		: 2026/04/03
 * Author	: Takashi Oikawa
 * Function	: Gemini API を同期（ブロッキング）呼び出しし、短い一言コメントを返す
 */
package model;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * つぶやき本文をプロンプトに載せ、Gemini の generateContent を1回呼び出す。
 * 通信・解析のいずれかで失敗した場合は {@link AiConfigLoader.AiConfig#getFailureMessage()}（なければ既定文）へフォールバックする。
 * 本クラスは Servlet からはまだ呼ばず、後続フェーズで Main から接続する。
 */
public class GeminiApiClient {

  private static final int MAX_COMMENT_CHARS = 50; // Update:20260404 表示上限を50文字へ
  private static final Duration HTTP_TIMEOUT = Duration.ofSeconds(30);

  private final HttpClient httpClient =
      HttpClient.newBuilder().connectTimeout(HTTP_TIMEOUT).build();

  /**
   * つぶやきに対する短い一言を返す。失敗時は必ずフォールバック文字列（失敗メッセージ）を返す。
   *
   * @param mutterText 投稿本文（null の場合は空文字として扱う）
   * @param config     {@link AiConfigLoader} で読み込んだ設定（null の場合は {@link AiConfigLoader#DEFAULT_FAILURE_MESSAGE}）
   * @return 50文字以内程度に切り詰めた返答、または失敗メッセージ
   */
  public String generateShortComment(String mutterText, AiConfigLoader.AiConfig config) {
    String safeText = mutterText == null ? "" : mutterText;
    String fallback =
        config != null && notBlank(config.getFailureMessage())
            ? config.getFailureMessage()
            : AiConfigLoader.DEFAULT_FAILURE_MESSAGE;

    if (config == null) {
      return fallback;
    }
    if (isBlank(config.getApiKey()) || isBlank(config.getModel())) {
      return fallback;
    }

    try {
      String body = buildRequestJson(safeText);
      String url = buildEndpointUrl(config.getModel(), config.getApiKey());
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(url))
              .timeout(HTTP_TIMEOUT)
              .header("Content-Type", "application/json; charset=UTF-8")
              .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
              .build();

      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

      if (response.statusCode() < 200 || response.statusCode() >= 300) {
        return fallback;
      }

      String text = extractTextFromResponse(response.body());
      if (isBlank(text)) {
        return fallback;
      }
      return limitApproxChars(text.trim(), MAX_COMMENT_CHARS);
    } catch (IOException | InterruptedException | RuntimeException ex) {
      ex.printStackTrace();
      if (ex instanceof InterruptedException) {
        Thread.currentThread().interrupt();
      }
      return fallback;
    }
  }

  /** REST: {@code v1beta/models/{model}:generateContent} */
  private static String buildEndpointUrl(String model, String apiKey) {
    String encModel = URLEncoder.encode(model, StandardCharsets.UTF_8);
    String encKey = URLEncoder.encode(apiKey, StandardCharsets.UTF_8);
    return "https://generativelanguage.googleapis.com/v1beta/models/"
        + encModel
        + ":generateContent?key="
        + encKey;
  }

  /** リクエストJSONを組み立て（Gson の JsonObject で十分なため POJO は使わない） */
  private static String buildRequestJson(String userText) {
    JsonObject root = new JsonObject();

    JsonArray contents = new JsonArray();
    JsonObject content = new JsonObject();
    JsonArray parts = new JsonArray();
    JsonObject part = new JsonObject();
    // Update:20260404 50文字以内・多様な一言を期待するプロンプト（文字数の最終調整は limitApproxChars）
    part.addProperty(
        "text",
        "次のつぶやきを読み、内容に合わせて自然に反応する日本語の言葉を返してください。"
            + "共感・励まし・軽いツッコ等を組み合わせてください。毎回同じ言い回しや型にはめ込まないでください。"
            + "50文字以内。余計な説明・引用符・箇条書きは付けないでください。\n\n"
            + userText);
    parts.add(part);
    content.add("parts", parts);
    contents.add(content);
    root.add("contents", contents);

    JsonObject gen = new JsonObject();
    gen.addProperty("maxOutputTokens", 64);
    gen.addProperty("temperature", 1.5);	// Update:20260404 設定値0.0-2.0 defort:1.0 試験的に1.5
    root.add("generationConfig", gen);

    return root.toString();
  }

  /**
   * レスポンスJSONから最初の candidates[0].content.parts[0].text を取り出す。
   * 構造が異なる場合は null。
   */
  private static String extractTextFromResponse(String jsonBody) {
    if (jsonBody == null || jsonBody.isEmpty()) {
      return null;
    }
    JsonObject root = JsonParser.parseString(jsonBody).getAsJsonObject();
    if (root.has("error")) {
      return null;
    }
    if (!root.has("candidates")) {
      return null;
    }
    JsonArray candidates = root.getAsJsonArray("candidates");
    if (candidates.size() == 0) {
      return null;
    }
    JsonObject first = candidates.get(0).getAsJsonObject();
    if (!first.has("content")) {
      return null;
    }
    JsonObject content = first.getAsJsonObject("content");
    if (!content.has("parts")) {
      return null;
    }
    JsonArray parts = content.getAsJsonArray("parts");
    if (parts.size() == 0) {
      return null;
    }
    JsonObject p0 = parts.get(0).getAsJsonObject();
    if (!p0.has("text")) {
      return null;
    }
    return p0.get("text").getAsString();
  }

  /** おおよそ max 文字以内（サロゲートペアを壊さないようコードポイント単位で切る） */
  private static String limitApproxChars(String s, int maxChars) {
    if (s == null || maxChars <= 0) {
      return "";
    }
    int cpCount = s.codePointCount(0, s.length());
    if (cpCount <= maxChars) {
      return s;
    }
    int end = s.offsetByCodePoints(0, maxChars);
    return s.substring(0, end);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static boolean notBlank(String s) {
    return !isBlank(s);
  }
}
