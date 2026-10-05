# Gemini Integration Specification（Gemini連携仕様書）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | GEMINI-SPEC-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-10-05 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../../README.md) / [Design Documents Index（設計書一覧）](../design/README.md) / [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) / [03_DATA_AND_SECURITY_DESIGN.md](../design/03_DATA_AND_SECURITY_DESIGN.md) / [05_ARCHITECTURE_DESIGN.md](../design/05_ARCHITECTURE_DESIGN.md) / [API_SPEC.md](./API_SPEC.md) |

---

## 1. Purpose（目的）

Version 3（`DokoTsubu3/`）の Gemini 連携を定義する。

- 本書は Version 3 の Target Specification（目標仕様）である
- `GENDER` / `AGE_FEELING` の Gemini 送信、およびそのための DB からの Profile 取得は設計確定済み・未実装である
- 現在の実装状況は [Project README](../../README.md) の「2.2 Feature Comparison（機能比較）」を参照する
- 要件の正は [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) の FR-009 とする
- 本書は設計正本と矛盾する内容を持たない。矛盾がある場合は `docs/design/` を正とする
- DokoTsubu2 の旧 AI 設定仕様（`docs/archive/DOKOTSUBU2_AI_CONFIGURATION_SPEC.md`）は現行正本ではない。固定絶対パスの設定ファイルは使用しない

---

## 2. Integration Overview（連携概要）

| Item（項目） | Specification（仕様） |
|---|---|
| Trigger（呼び出し契機） | `POST /Main` の投稿成功後 |
| Invocation（呼び出し方式） | 同期呼び出し |
| Caller（呼び出し元） | Post Service → Gemini Client |
| Profile Source（プロフィール取得元） | Gemini 生成時に DB から有効 User（`USERS.DELETED_AT IS NULL`）の `GENDER` / `AGE_FEELING` を取得する。Session からは取得しない |
| Output（出力） | 短い日本語コメント。`aiMsg` として SCR-005 に表示する |

---

## 3. Gemini Model and Endpoint（モデル・エンドポイント）

| Item（項目） | Specification（仕様） |
|---|---|
| Model | `gemini-3.5-flash-lite` |
| API | Gemini `generateContent`。HTTPS REST |
| Endpoint | `https://generativelanguage.googleapis.com/v1beta/models/{model}:generateContent` |
| API Key 送信 | HTTP header `x-goog-api-key`。URL には含めない |
| Sampling | `temperature` / `top_p` / `top_k` は明示指定しない。モデル既定値を使用する |

---

## 4. Prompt Composition（プロンプト構成）

| Element（要素） | Content（内容） |
|---|---|
| 指示文 | 投稿内容に自然に反応する短い日本語コメントを求める |
| Mutter本文 | 投稿された本文 |
| GENDER | 利用者向け表示名で渡す（例: `性別: ヒミツ`） |
| AGE_FEELING | 利用者向け表示名で渡す（例: `年齢感覚: そこそこ若い`） |

DB 内部コードではなく、利用者向けの意味が分かる値として渡す。表示名は [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) の FR-001 登録項目を正とする。

Profile の扱い:

- `GENDER` / `AGE_FEELING` は回答の表現・バリエーション調整の参考情報として扱う
- `GENDER = PRIVATE` の場合、性別を推測させない
- `GENDER` / `AGE_FEELING` から、性格、職業、能力、価値観、その他プロフィールに存在しない属性を推定させない
- `AGE_FEELING` は自己申告の感覚であり、実年齢として扱わせない

---

## 5. Data Transfer（送信データ）

### 5.1 Data Sent to Gemini（Geminiへ送信するデータ）

- Mutter本文
- `GENDER`
- `AGE_FEELING`

### 5.2 Data Not Sent to Gemini（Geminiへ送信しないデータ）

- password
- password hash
- User ID
- username
- Session情報
- DB 接続情報、その他秘密情報

---

## 6. Response Handling（応答処理）

- 応答の最初の候補の text を使用する
- 前後の空白を除去し、50 文字以内に切り詰める
- 投稿直後の画面に `aiMsg` として表示する

---

## 7. Error Handling（エラー処理）

| Case（ケース） | Behavior（動作） |
|---|---|
| API key または model が未設定 | Gemini を呼び出さず、失敗文を返す |
| 通信エラー・タイムアウト・HTTP エラー | 失敗文を返す |
| 応答が空、または text を取得できない | 失敗文を返す |

- 失敗しても投稿は rollback しない
- 失敗文も `aiMsg` として SCR-005 に表示する
- ログ・画面に API key を出さない

---

## 8. API Key and External Configuration（API Key管理・外部設定）

| Item（項目） | Specification（仕様） |
|---|---|
| API key 設定名 | `DOKOTSUBU_GEMINI_API_KEY` |
| Model 設定 | `application.properties` の `dokotsubu.gemini.model`。秘密値ではない |
| Local | Git 管理外の `.local-secrets/` から実行環境へ渡す。手順は [ENVIRONMENT_SETUP_GUIDE.md](../ENVIRONMENT_SETUP_GUIDE.md) |
| Production | Vercel Environment Variables。手順は [DEPLOYMENT_AND_OPERATION_GUIDE.md](../DEPLOYMENT_AND_OPERATION_GUIDE.md) |
| 禁止事項 | 実値を source / Git / 文書 / ログへ記載しない。固定絶対パスの設定ファイルを使わない |
