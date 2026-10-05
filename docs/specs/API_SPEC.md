# API Specification（API仕様書）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | API-SPEC-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-10-05 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../../README.md) / [Design Documents Index（設計書一覧）](../design/README.md) / [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) / [03_DATA_AND_SECURITY_DESIGN.md](../design/03_DATA_AND_SECURITY_DESIGN.md) / [04_UI_AND_FLOW_DESIGN.md](../design/04_UI_AND_FLOW_DESIGN.md) / [GEMINI_INTEGRATION_SPEC.md](./GEMINI_INTEGRATION_SPEC.md) |

---

## 1. Purpose（目的）

Version 3（`DokoTsubu3/`）の Spring MVC アプリケーションエンドポイントを定義する。

- 本書は Version 3 の Target Specification（目標仕様）である
- 設計確定済み・未実装の仕様を含む（`gender` / `ageFeeling` の登録項目、論理削除、論理削除条件による有効データ判定）
- 現在の実装状況は [Project README](../../README.md) の「2.2 Feature Comparison（機能比較）」を参照する
- 要件の正は [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) の FR-001〜FR-009、API-001〜API-007 とする
- 本書は設計正本と矛盾する内容を持たない。矛盾がある場合は `docs/design/` を正とする
- 外部公開 REST API は無い
- DokoTsubu2 の旧 API 仕様（`docs/archive/DOKOTSUBU2_API_SPEC.md`）は現行正本ではない

---

## 2. Common Rules（共通ルール）

| Item（項目） | Rule（ルール） |
|---|---|
| Base Path | context path `/dokoTsubu`。Controller / JSP に固定文字列として書かない |
| Response | JSP View または redirect。JSON API は無い |
| Login Required | `/Main`、`/SearchMutter`、`/UpdateMutter`、`/DeleteMutter`。Spring MVC Interceptor で一元判定する。未ログイン時は `/Login` へ redirect する |
| Session | キーは `loginUser`。保存内容は user id と user name のみ。password、`GENDER`、`AGE_FEELING` は保存しない |
| CSRF | Session 保存型 token。パラメーター名は `csrfToken`。対象は `POST /Main`、`POST /UpdateMutter`、`POST /DeleteMutter`。token なし・不一致は状態変更せず 403 |
| Authorization | 編集・削除は、ログイン済み、`MUTTERS.USER_ID == loginUser.id`、Mutter と User が論理削除されていない、をすべて満たす場合だけ許可する。最終判定はサーバー側 |
| Active Data | 通常処理の有効判定は `DELETED_AT IS NULL` |

---

## 3. Endpoint List（エンドポイント一覧）

| API ID | Method | Path | Controller | 認証 | CSRF対象 | 状態変更 |
|---|---|---|---|---|---|---|
| API-001 | GET | `/Register` | `RegisterController` | 不要 | ― | なし |
| API-001 | POST | `/Register` | `RegisterController` | 不要 | ― | あり（`USERS` 登録） |
| API-002 | GET | `/Login` | `LoginController` | 不要 | ― | なし |
| API-002 | POST | `/Login` | `LoginController` | 不要 | ― | あり（Session に `loginUser` 保存） |
| API-003 | GET | `/Logout` | `LogoutController` | 不要 | ― | あり（Session 破棄） |
| API-004 | GET | `/Main` | `MainController` | 要 | ― | なし |
| API-004 | POST | `/Main` | `MainController` | 要 | ○ | あり（`MUTTERS` 登録） |
| API-005 | GET | `/SearchMutter` | `SearchMutterController` | 要 | ― | なし |
| API-006 | GET | `/UpdateMutter` | `UpdateMutterController` | 要 | ― | なし |
| API-006 | POST | `/UpdateMutter` | `UpdateMutterController` | 要 | ○ | あり（`MUTTERS` 更新） |
| API-007 | POST | `/DeleteMutter` | `DeleteMutterController` | 要 | ○ | あり（`MUTTERS` 論理削除） |

`GET /DeleteMutter` は存在しない。User 削除 API は存在しない。

---

## 4. Endpoint Details（エンドポイント詳細）

### 4.1 GET /Register

| Item | Value |
|---|---|
| Request parameter | なし |
| Session利用 | なし |
| Response / View | `registerView`（SCR-002） |

### 4.2 POST /Register

| Item | Value |
|---|---|
| Request parameter | `username` / `password` / `gender` / `ageFeeling` |
| Session利用 | なし |
| Validation | 4 項目すべて必須。`gender` は `MALE` / `FEMALE` / `OTHER` / `PRIVATE` のみ。`ageFeeling` は `YOUNG` / `FAIRLY_YOUNG` / `MIDDLE` / `PRE_SENIOR` / `SENIOR` のみ |
| Duplicate | `USERS.NAME` は論理削除済み User を含めて一意。重複時は `そのユーザー名は登録済です...`。有効 User と論理削除済み User でメッセージを変えない |
| Response / View | 成功: `registerResult`（SCR-003）。失敗: `registerView`（SCR-002） |
| 状態変更 | `USERS` へ登録。password は BCrypt hash のみ保存する |

値の意味と画面表示は [02_REQUIREMENTS_DEFINITION.md](../design/02_REQUIREMENTS_DEFINITION.md) の FR-001 登録項目を正とする。

### 4.3 GET /Login

| Item | Value |
|---|---|
| Request parameter | なし |
| Response / View | ログイン入口（SCR-001） |

### 4.4 POST /Login

| Item | Value |
|---|---|
| Request parameter | `name` / `pass` |
| Validation | 両方必須 |
| 認証 | 入力 password と保存済み BCrypt hash を照合する。対象は `USERS.DELETED_AT IS NULL` の User のみ |
| Session利用 | 成功時に `loginUser`（user id / user name のみ）を保存する |
| Response / View | `loginResult`（SCR-004）。成功・失敗の両方 |

### 4.5 GET /Logout

| Item | Value |
|---|---|
| Request parameter | なし |
| Session利用 | Session を破棄する |
| Response / View | `logout`（SCR-007） |

### 4.6 GET /Main

| Item | Value |
|---|---|
| Request parameter | なし |
| Session利用 | `loginUser` |
| Response / View | `main`（SCR-005）。`MUTTERS.DELETED_AT IS NULL` かつ `USERS.DELETED_AT IS NULL` の投稿を ID 降順 |

### 4.7 POST /Main

| Item | Value |
|---|---|
| Request parameter | `text` / `csrfToken` |
| Validation | `text` 必須。未入力時は `main` にエラーを表示する |
| Session利用 | `loginUser.id` を投稿者として使用する。CSRF token を照合する |
| Response / View | `main`（SCR-005）。投稿成功後は `aiMsg` を表示する |
| Gemini | 投稿成功後に同期呼び出し。失敗しても投稿は rollback しない。詳細は [GEMINI_INTEGRATION_SPEC.md](./GEMINI_INTEGRATION_SPEC.md) |

### 4.8 GET /SearchMutter

| Item | Value |
|---|---|
| Request parameter | `keyword` |
| Session利用 | `loginUser` |
| Response / View | `main`（SCR-005）。`MUTTERS.TEXT LIKE` で絞り込み、一覧と同じ論理削除条件を適用して ID 降順 |

### 4.9 GET /UpdateMutter

| Item | Value |
|---|---|
| Request parameter | `id` |
| Authorization | 本人かつ有効な Mutter のみ |
| Response / View | 許可時: `updateMutter`（SCR-006）。拒否時: `/Main` へ redirect |

### 4.10 POST /UpdateMutter

| Item | Value |
|---|---|
| Request parameter | `id` / `text` / `csrfToken` |
| Validation | `id` / `text` 必須 |
| Authorization | 本人かつ有効な Mutter のみ。SQL は `ID` と `USER_ID` の両方を条件にする |
| Response / View | 成功: `/Main` へ redirect。失敗: `updateMutter`（SCR-006）で入力を保持。拒否時: 更新せず `/Main` へ redirect |
| 状態変更 | `TEXT` と `UPDATED_AT` を更新する |

### 4.11 POST /DeleteMutter

| Item | Value |
|---|---|
| Request parameter | `id` / `csrfToken` |
| Authorization | 本人かつ有効な Mutter のみ。SQL は `ID` と `USER_ID` の両方を条件にする |
| Response / View | `/Main` へ redirect |
| 状態変更 | 論理削除。`MUTTERS.DELETED_AT` と `UPDATED_AT` を設定する。物理 DELETE は実行しない |

---

## 5. Related Documents（関連文書）

- 画面遷移: [04_UI_AND_FLOW_DESIGN.md](../design/04_UI_AND_FLOW_DESIGN.md)
- データ・認証・認可: [03_DATA_AND_SECURITY_DESIGN.md](../design/03_DATA_AND_SECURITY_DESIGN.md)
- 構成: [05_ARCHITECTURE_DESIGN.md](../design/05_ARCHITECTURE_DESIGN.md)
- ローカル起動: [ENVIRONMENT_SETUP_GUIDE.md](../ENVIRONMENT_SETUP_GUIDE.md)
