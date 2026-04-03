/*
 * DATE		: 2026/04/03
 * Author	: Takashi Oikawa
 * Function	: 外部設定ファイル ai-config.json（apiKey / model / failureMessage）を読み込む
 */
package model;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@code /Users/takashioikawa/Dev/ai-config.json} を Gson で読み込み、設定値へマッピングする。
 * 読み込み失敗時は呼び出し側が {@link #fallbackConfig()} の内容へフォールバックできる。
 */
public class AiConfigLoader {

  /** 仕様で固定の外部設定パス（本番接続は後続で Main 等から委譲する想定） */
  public static final Path DEFAULT_CONFIG_PATH =
      Paths.get("/Users/takashioikawa/Dev/ai-config.json");

  /** 設定ファイルが読めない場合や項目欠落時に使う既定の失敗メッセージ */
  public static final String DEFAULT_FAILURE_MESSAGE =
      "本日AIお休みさせていただいております。。。";

  private final Gson gson =
      new GsonBuilder().disableHtmlEscaping().create();

  /**
   * 外部JSONの項目。キー名は ai-config.json と一致させる。
   */
  public static class AiConfig {
    private String apiKey;
    private String model;
    private String failureMessage;

    /** フォールバック用の設定インスタンス（内部でのみフィールド代入） */
    static AiConfig forFallback(String failureMessage) {
      AiConfig cfg = new AiConfig();
      cfg.apiKey = "";
      cfg.model = "";
      cfg.failureMessage = failureMessage;
      return cfg;
    }

    static AiConfig copyWithFailure(AiConfig base, String failureMessage) {
      AiConfig cfg = new AiConfig();
      cfg.apiKey = base.getApiKey();
      cfg.model = base.getModel();
      cfg.failureMessage = failureMessage;
      return cfg;
    }

    public String getApiKey() {
      return apiKey;
    }

    public String getModel() {
      return model;
    }

    public String getFailureMessage() {
      return failureMessage;
    }
  }

  /**
   * 既定パス {@link #DEFAULT_CONFIG_PATH} から設定を読み込む。
   *
   * @throws IOException ファイルが存在しない、読めない、JSONが不正な場合
   */
  public AiConfig load() throws IOException {
    return load(DEFAULT_CONFIG_PATH);
  }

  /**
   * 指定パスから設定を読み込む。
   *
   * @param path 設定ファイルの絶対パス
   * @throws IOException ファイルが存在しない、読めない、JSONが不正な場合
   */
  public AiConfig load(Path path) throws IOException {
    try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      AiConfig cfg = gson.fromJson(reader, AiConfig.class);
      if (cfg == null) {
        throw new IOException("設定JSONの解析結果が空です: " + path);
      }
      return cfg;
    }
  }

  /**
   * 読み込みに失敗した場合でも例外にせず、{@link #fallbackConfig()} を返す。
   * Main 接続前の土台として、API 層から安全に呼べるようにする。
   */
  public AiConfig loadOrFallback(Path path) {
    try {
      AiConfig cfg = load(path);
      if (isBlank(cfg.getFailureMessage())) {
        return AiConfig.copyWithFailure(cfg, DEFAULT_FAILURE_MESSAGE);
      }
      return cfg;
    } catch (IOException | RuntimeException ex) {
      // ログはまだServletに接続しないため標準エラーのみ（必要なら後でロガーへ）
      ex.printStackTrace();
      return fallbackConfig();
    }
  }

  /**
   * ファイル無し・APIキー無しのときに使うフォールバック設定。
   */
  public AiConfig fallbackConfig() {
    return AiConfig.forFallback(DEFAULT_FAILURE_MESSAGE);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
}
