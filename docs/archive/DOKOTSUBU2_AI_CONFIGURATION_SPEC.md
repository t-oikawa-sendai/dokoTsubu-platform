# AI設定仕様書（DokoTsubu2 / Gemini API）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | LEGACY-005 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-04-12 |
| Last Updated（最終更新日） | 2026-09-12 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | None |

## 1. 目的

`DokoTsubu2` の AI 連携（Gemini API 呼び出し）について、**現行ソースおよび実在設定ファイル**から確認できる範囲で、設定値・表示仕様・エラー時挙動・未対応事項を整理する。

---

## 2. 対象クラス（対象モジュール）

- `DokoTsubu2/src/main/java/model/AiConfigLoader.java`
- `DokoTsubu2/src/main/java/model/GeminiApiClient.java`
- `DokoTsubu2/src/main/java/servlet/Main.java`
- `DokoTsubu2/src/main/webapp/WEB-INF/jsp/main.jsp`

---

## 3. 設定ファイルの場所

### 3-1. 設定ファイル（実在）

- **絶対パス**: `/Users/takashioikawa/Dev/ai-config.json`
- **参照元**: `model.AiConfigLoader.DEFAULT_CONFIG_PATH`

### 3-2. 設定キー（実在）

`ai-config.json` には少なくとも次のキーが存在する。

- `apiKey`
- `model`
- `failureMessage`

---

## 4. APIキー参照方法（現行実装）

- `servlet.Main` が `AiConfigLoader.loadOrFallback(AiConfigLoader.DEFAULT_CONFIG_PATH)` を呼び出す
- `AiConfigLoader` が `ai-config.json` を Gson で読み込み、`AiConfigLoader.AiConfig`（`apiKey` / `model` / `failureMessage`）へマッピングする
- `GeminiApiClient.generateShortComment(mutterText, config)` が `config.getApiKey()` を参照し、Gemini API 呼び出し URL の `key` クエリに設定する

**注意（秘匿情報）**

- `apiKey` は秘匿値のため、本書では値そのものは記載しない（現行ファイルに存在することのみを記載する）

---

## 5. Gemini API 呼び出し仕様（現行実装）

### 5-1. エンドポイント

`GeminiApiClient` は次の REST エンドポイントを組み立てて呼び出す。

- `https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent?key={apiKey}`

ここで `model` と `apiKey` は `ai-config.json` の値を URL エンコードして埋め込む。

### 5-2. リクエストボディ（概要）

- `contents[0].parts[0].text` に、固定の指示文＋ユーザー入力（つぶやき本文）を連結して送る
- `generationConfig` に一部パラメータを設定する（後述）

### 5-3. 返却値（概要）

- レスポンス JSON の `candidates[0].content.parts[0].text` を抽出する
- 抽出結果は **最大50文字程度**に切り詰める（コードポイント単位）

---

## 6. 生成設定パラメータ一覧（指定有無・値）

本節は、要求されているパラメータ名について、**現行実装で明示指定しているか**を区別して記載する。

### 6-1. パラメータ一覧（結論）

| パラメータ | 現行実装で使用中か | 設定値（使用中の場合） | 根拠（どこで確認できるか） |
|---|---|---:|---|
| `model` | 使用中 | `gemini-2.5-flash-lite` | `/Users/takashioikawa/Dev/ai-config.json` |
| `temperature` | 使用中 | `0.7` | `model/GeminiApiClient.java`（`generationConfig.temperature`） |
| `topP` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |
| `topK` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |
| `maxOutputTokens` | 使用中 | `64` | `model/GeminiApiClient.java`（`generationConfig.maxOutputTokens`） |
| `candidateCount` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |
| `responseMimeType` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |
| `systemInstruction` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |
| `safetySettings` | 未使用 | - | 現行実装では明示指定なし。APIデフォルト動作に依存 |

### 6-2. 各項目の意味（本書で扱う範囲）

- `model`
  - **意味**: 呼び出すモデル名（`v1beta/models/{model}:generateContent` の `{model}` 部分）
  - **現行**: `ai-config.json` から読み込んで使用
- `temperature`
  - **意味**: 生成のランダム性（高いほど出力が多様になりやすい）
  - **現行**: `generationConfig.temperature = 0.7` を固定で指定
- `topP`
  - **意味**: nucleus sampling の確率しきい値（一般的に \(0 \sim 1\)）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**
- `topK`
  - **意味**: top-k sampling の候補数（一般的に整数）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**
- `maxOutputTokens`
  - **意味**: 生成の最大トークン数
  - **現行**: `generationConfig.maxOutputTokens = 64` を固定で指定
- `candidateCount`
  - **意味**: 生成候補の数（複数候補を返す設定）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**
- `responseMimeType`
  - **意味**: レスポンス形式（例: JSON / text 等）を指定するための項目（仕様上の名称）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**
- `systemInstruction`
  - **意味**: システム指示（ロール/制約）を別枠で指定するための項目（仕様上の名称）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**
- `safetySettings`
  - **意味**: 安全設定（カテゴリや閾値など）を指定するための項目（仕様上の名称）
  - **現行**: **現行実装では明示指定なし。APIデフォルト動作に依存**

---

## 7. 表示仕様（現行実装）

### 7-1. 表示条件

- `main.jsp` は、リクエスト属性 `aiMsg` が **null でない場合のみ** AI コメント表示ブロックを描画する
- `aiMsg` は `servlet.Main` の **投稿（POST）処理**で設定される

### 7-2. 表示内容

- ラベル: `AI：`
- 本文: `aiMsg` の文字列（HTMLエスケープ後）

### 7-3. 表示演出

- `main.jsp` 側で **1文字ずつ表示**（タイプライター風）する JavaScript を実行する
- 文字列は `textContent` に流し込み、DOM 挿入前に JSP 側で `& < >` をエスケープする

---

## 8. エラー時挙動（現行実装）

### 8-1. 設定読込失敗時

`AiConfigLoader.loadOrFallback(...)` は例外時にフォールバック設定を返す。

- `failureMessage` が未設定/空の場合も、既定文へ補完される
- フォールバック時の既定文は `AiConfigLoader.DEFAULT_FAILURE_MESSAGE`

### 8-2. API呼び出し失敗時

`GeminiApiClient.generateShortComment(...)` は、失敗時に必ずフォールバック文字列を返す。

失敗として扱う条件（現行実装に存在するもの）:

- `config == null`
- `apiKey` または `model` が空（blank）
- HTTP ステータスが 2xx 以外
- レスポンス JSON から `candidates[0].content.parts[0].text` を抽出できない
- 通信/解析/実行時例外（`IOException` / `InterruptedException` / `RuntimeException`）

### 8-3. 画面側の挙動

- `servlet.Main` は AI 呼び出し部を `try` で囲み、`RuntimeException` を捕捉した場合は `aiMsg` に `AiConfigLoader.DEFAULT_FAILURE_MESSAGE` を設定する
- `aiMsg` が設定されていれば `main.jsp` で表示される（失敗文も表示対象）

---

## 9. 未対応事項（現行実装で明示的に無い/未使用）

- `topP` / `topK` / `candidateCount` / `responseMimeType` / `systemInstruction` / `safetySettings`
  - **現行実装では明示指定なし。APIデフォルト動作に依存**
- API呼び出し成功/失敗の詳細ログや監視の仕組み（専用ロガー等）
  - 現行実装は `printStackTrace()` のみ

---

## 10. 将来拡張候補（現行実装から読み取れる方向性）

本節は「現行のクラス/フィールド/呼び出し構造が既にある」ことを前提に、追加しやすい拡張点を列挙する。

- `ai-config.json` の設定項目拡張
  - `generationConfig` の追加パラメータ（`topP` / `topK` 等）を **設定ファイルから**渡せるようにする
- `systemInstruction` 相当の導入
  - プロンプト本文とは別枠での制約定義（API仕様に沿って追加）
- `safetySettings` の明示指定
  - APIの既定挙動依存をやめ、要件に合わせた安全設定を固定化する
- 表示の出し分け
  - AI失敗時の UI（エラーバナー/再試行リンク等）を `main.jsp` で分岐表示する

