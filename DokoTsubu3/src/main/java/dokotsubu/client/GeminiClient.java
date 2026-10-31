/*
 * Program Name: GeminiClient
 * Language: Java
 * Function: Call Gemini generateContent and return a short Japanese comment
 * Created: 2026-09-29
 * Last Updated: 2026-10-08
 * Author: Takashi Oikawa
 * AI: Cursor Grok 4.7
 * Memo: Phase 1 DokoTsubu3 FR-009. Prompt includes display names only. API key is a header only. Sampling parameters are omitted.
 */

package dokotsubu.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dokotsubu.model.UserProfile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class GeminiClient {

    public static final String FAILURE_MESSAGE = "本日AIお休みさせていただいております。。。";

    private static final int MAX_COMMENT_CODE_POINTS = 50;
    private static final Duration HTTP_TIMEOUT = Duration.ofSeconds(30);
    private static final String PROMPT_PREFIX =
            "次のつぶやきを読み、内容に合わせて自然に反応する日本語の言葉を返してください。"
                    + "共感・励まし・軽いツッコミ等を組み合わせてください。毎回同じ言い回しや型にはめ込まないでください。"
                    + "50文字以内。余計な説明・引用符・箇条書きは付けないでください。\n\n";

    private final String apiKey;
    private final String model;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GeminiClient(
            @Value("${dokotsubu.gemini.api-key:}") String apiKey,
            @Value("${dokotsubu.gemini.model}") String model) {
        this.apiKey = apiKey;
        this.model = model;
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(HTTP_TIMEOUT)
                .build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(HTTP_TIMEOUT);
        this.restClient = RestClient.builder().requestFactory(requestFactory).build();
        this.objectMapper = new ObjectMapper();
    }

    public String generateShortComment(String mutterText, UserProfile profile) {
        if (profile == null || isBlank(apiKey) || isBlank(model)) {
            return FAILURE_MESSAGE;
        }
        String safeText = mutterText == null ? "" : mutterText;
        try {
            String responseBody = restClient.post()
                    .uri(URI.create(endpoint()))
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestJson(safeText, profile))
                    .retrieve()
                    .body(String.class);
            String comment = extractComment(responseBody);
            if (comment == null) {
                return FAILURE_MESSAGE;
            }
            return comment;
        } catch (Exception ex) {
            restoreInterrupt(ex);
            return FAILURE_MESSAGE;
        }
    }

    private String endpoint() {
        return "https://generativelanguage.googleapis.com/v1beta/models/"
                + model
                + ":generateContent";
    }

    private String requestJson(String userText, UserProfile profile) {
        Map<String, Object> part = Map.of("text", promptText(userText, profile));
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> generationConfig = Map.of("maxOutputTokens", 64);
        Map<String, Object> root = Map.of(
                "contents", List.of(content),
                "generationConfig", generationConfig);
        return objectMapper.writeValueAsString(root);
    }

    private String promptText(String userText, UserProfile profile) {
        StringBuilder prompt = new StringBuilder(PROMPT_PREFIX);
        prompt.append("性別と年齢感覚は、回答の表現・バリエーション調整の参考情報として扱ってください。");
        if (profile.isPrivateGender()) {
            prompt.append("性別を推測しないでください。");
        }
        prompt.append("年齢感覚は自己申告の感覚であり、実年齢ではありません。実年齢として扱わないでください。");
        prompt.append("性別と年齢感覚から、性格、職業、能力、価値観、その他プロフィールに存在しない属性を決めつけたり推定したりしないでください。");
        prompt.append("\n\n");
        prompt.append("性別: ").append(profile.genderDisplayName()).append('\n');
        prompt.append("年齢感覚: ").append(profile.ageFeelingDisplayName()).append("\n\n");
        prompt.append(userText);
        return prompt.toString();
    }

    private String extractComment(String jsonBody) {
        if (jsonBody == null || jsonBody.isEmpty()) {
            return null;
        }
        JsonNode root = objectMapper.readTree(jsonBody);
        JsonNode candidates = root.get("candidates");
        if (candidates == null || !candidates.isArray() || candidates.isEmpty()) {
            return null;
        }
        JsonNode textNode = candidates.get(0).path("content").path("parts").path(0).get("text");
        if (textNode == null || !textNode.isTextual()) {
            return null;
        }
        String text = textNode.asText();
        if (isBlank(text)) {
            return null;
        }
        return limitCodePoints(text.trim(), MAX_COMMENT_CODE_POINTS);
    }

    private static String limitCodePoints(String value, int maxCodePoints) {
        int count = value.codePointCount(0, value.length());
        if (count <= maxCodePoints) {
            return value;
        }
        int end = value.offsetByCodePoints(0, maxCodePoints);
        return value.substring(0, end);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void restoreInterrupt(Throwable error) {
        Throwable current = error;
        while (current != null) {
            if (current instanceof InterruptedException) {
                Thread.currentThread().interrupt();
                return;
            }
            current = current.getCause();
        }
    }
}
