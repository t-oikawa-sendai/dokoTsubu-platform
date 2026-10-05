# Requirements Definition（要件定義）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | REQS-001 |
| Version（バージョン） | 1.5 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../../README.md) / [README.md](./README.md) / [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) / [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) / [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md) / [05_ARCHITECTURE_DESIGN.md](./05_ARCHITECTURE_DESIGN.md) / [API_SPEC.md](../specs/API_SPEC.md) / [GEMINI_INTEGRATION_SPEC.md](../specs/GEMINI_INTEGRATION_SPEC.md) |

---

## Table of Contents（目次）

1. [Purpose（目的）](#1-purpose目的)
2. [Scope（対象範囲）](#2-scope対象範囲)
3. [Out of Scope（対象外範囲）](#3-out-of-scope対象外範囲)
4. [Assumptions（前提条件）](#4-assumptions前提条件)
5. [Definition Details（定義内容）](#5-definition-details定義内容)
6. [Open Issues（未決事項）](#6-open-issues未決事項)
7. [Handoff to Detail Design（詳細設計への引き継ぎ）](#7-handoff-to-detail-design詳細設計への引き継ぎ)

---

## 1. Purpose（目的）

本文書は、Phase 1 でシステムが満たすべき機能要件・非機能要件を定義し、データ / UI / アーキテクチャ設計の基準とする。

---

## 2. Scope（対象範囲）

現行 `DokoTsubu2` が提供する次の機能と、Phase 1 で確定したセキュリティ要求。

- ユーザー登録、ログイン、ログアウト
- つぶやき一覧、投稿、検索、編集、削除
- 投稿成功後の Gemini コメント生成
- User / Mutter の論理削除とデータライフサイクル
- 登録時に取得する `GENDER` / `AGE_FEELING` と、その Gemini コメント生成への利用

---

## 3. Out of Scope（対象外範囲）

- Spring Security による認証基盤
- JPA / Hibernate / Spring Data
- Thymeleaf
- 今回確定した論理削除、データライフサイクル、`GENDER` / `AGE_FEELING` 以外の、現行に無い機能追加
- 公開 REST API の新設

---

## 4. Assumptions（前提条件）

- 画面遷移の正は現行実装事実とする。Legacy API 文書の誤記は引き継がない
- 登録とログインのリクエストパラメーター名は現行どおり異なる
- データ構造の詳細は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) を正とする

---

## 5. Definition Details（定義内容）

### 5.1 Functional Requirements（機能要件一覧）

| ID | Feature Name（機能名） | Priority（優先度） | Details（詳細） |
|---|---|---|---|
| FR-001 | ユーザー登録 | High | `GET /Register` でフォーム表示。`POST /Register` のパラメーターは `username` / `password` / `gender` / `ageFeeling`。`gender` と `ageFeeling` は必須。成功時は登録完了画面。未入力・許可値以外・重複・その他 DB エラーは登録画面へ戻す。重複時の表示は `そのユーザー名は登録済です...`。有効 User と論理削除済み User でエラーメッセージを変えない |
| FR-002 | ログイン | High | `GET /Login` はログイン入口へ戻す。`POST /Login` のパラメーターは `name` / `pass`。ログイン対象は `USERS.DELETED_AT IS NULL` の User のみ。論理削除済み User はログイン不可。成功時はセッションへ `loginUser`（user id / user name のみ）を保存し、ログイン結果画面を表示する |
| FR-003 | ログアウト | High | `GET /Logout` でセッションを破棄し、ログアウト画面を表示する |
| FR-004 | つぶやき一覧 | High | `GET /Main`。要ログイン。表示対象は `MUTTERS.DELETED_AT IS NULL` かつ `USERS.DELETED_AT IS NULL`。論理削除済み User の投稿は表示しない。対象を ID 降順で表示する |
| FR-005 | つぶやき投稿 | High | `POST /Main`。要ログイン。パラメーター `text`。未入力時は一覧画面にエラーを出す。投稿成功後に Gemini を同期呼び出し、`aiMsg` を一覧画面へ渡す |
| FR-006 | つぶやき検索 | High | `GET /SearchMutter`。要ログイン。パラメーター `keyword`。`MUTTERS` の本文を `LIKE` で絞り込む。検索結果は一覧と同じ論理削除条件（`MUTTERS.DELETED_AT IS NULL` かつ `USERS.DELETED_AT IS NULL`）を適用する。論理削除済み投稿、および論理削除済み User の投稿は検索結果へ出さない。ID 降順で一覧表示する |
| FR-007 | つぶやき編集 | High | `GET /UpdateMutter` と `POST /UpdateMutter`。編集できるのは、ログイン済み、投稿者本人、Mutter が論理削除されていない、User が論理削除されていない、をすべて満たす投稿だけ。対象は `id`。`POST` の本文は `text`。成功時は一覧へ戻る。失敗時は編集画面へ戻す |
| FR-008 | つぶやき削除 | High | `POST /DeleteMutter`。要ログインかつ投稿者本人。対象は `id`。削除は論理削除とする。`MUTTERS.DELETED_AT` へ削除日時を設定し、`UPDATED_AT` も更新する。通常の投稿削除処理では物理 DELETE を実行しない。処理後は一覧へ戻る。`GET /DeleteMutter` では削除しない |
| FR-009 | Gemini コメント生成 | High | 投稿成功後に同期呼び出し。model は `gemini-3.5-flash-lite`。`temperature` / `top_p` / `top_k` は明示指定しない。Gemini へ渡す情報は投稿本文、`GENDER`、`AGE_FEELING`。DB 内部コードではなく利用者向け意味が分かる値として渡す。失敗しても投稿は rollback しない。失敗時も `aiMsg` に失敗文を載せて表示する |

#### FR-001 登録項目

`GENDER` は必須。DB 内部値と画面表示は次で固定する。上記 4 値以外は登録不可。画面初期値は `PRIVATE`。`PRIVATE` は未入力ではなく正式な選択値である。

| DB値 | 画面表示 |
|---|---|
| MALE | 男性 |
| FEMALE | 女性 |
| OTHER | その他 |
| PRIVATE | ヒミツ |

`AGE_FEELING` は必須。初期選択は無い。DB 内部値と画面表示は次で固定する。上記 5 値以外は登録不可。

| DB値 | 画面表示 |
|---|---|
| YOUNG | 若い |
| FAIRLY_YOUNG | そこそこ若い |
| MIDDLE | 中年 |
| PRE_SENIOR | 高齢者の少し手前 |
| SENIOR | がっつり高齢者 |

`AGE_FEELING` は実年齢、生年月日、年齢区分ではない。本人が自分自身をどう感じているかを表す自己申告プロフィールである。生年月日・実年齢は保持しない。

`USERS.NAME` の一意性は維持する。論理削除済み User も含めて同一 `NAME` は再利用不可。

#### FR-009 Gemini へ渡す情報

渡す情報は投稿本文、`GENDER`、`AGE_FEELING` のみとする。利用者向けの意味が分かる値として渡す。

例:

- 性別: ヒミツ
- 年齢感覚: そこそこ若い

`GENDER = PRIVATE` の場合、Gemini に性別を推測させない。`GENDER` / `AGE_FEELING` は回答の表現・バリエーション調整の参考情報として扱う。これらから性格、職業、能力、価値観、その他プロフィールに存在しない属性を決めつけさせない。

Gemini へ送らない情報:

- password
- password hash
- user id
- ユーザー名
- Session 情報

連携方式の詳細は [GEMINI_INTEGRATION_SPEC.md](../specs/GEMINI_INTEGRATION_SPEC.md) を参照。

既存仕様として維持するもの:

- 登録パラメーター名: `username` / `password` / `gender` / `ageFeeling`
- ログインパラメーター名: `name` / `pass`
- 一覧は ID 降順。表示条件は `MUTTERS.DELETED_AT IS NULL` かつ `USERS.DELETED_AT IS NULL`
- 検索は `TEXT LIKE`。論理削除条件は一覧と同一

### 5.2 Non-Functional Requirements（非機能要件）

| Type（種別） | Requirement（要件内容） |
|---|---|
| Performance（性能） | Gemini は投稿後の同期呼び出しとする。タイムアウト等の新規性能目標は設けない |
| Availability（可用性） | Gemini 失敗時も投稿を残す。可用性目標値は設けない |
| Security Requirement Level（セキュリティ要求レベル） | password 平文保存禁止。session への password 保持禁止。Update / Delete は認証必須かつ投稿者本人限定。API key / DB password の source・Git 保存禁止。`POST /Main`、`POST /UpdateMutter`、`POST /DeleteMutter` は Session 保存型 CSRF token を照合する。Spring Security は導入しない |
| Maintainability（保守性） | ログイン必須判定は Controller ごとの重複実装を避け、Spring MVC 側で一元化する |
| Other（その他） | context path は `/dokoTsubu`。Controller / JSP に `/dokoTsubu` を固定文字列として書かない |
| Deploy（公開） | `DokoTsubu3` の公開先は Vercel。Project Root は `DokoTsubu3`。Spring Boot / JSP / JDBC / WAR / embedded Tomcat は維持する。Vercel 公開用に `Dockerfile.vercel` を使用する。Cloud Run は採用しない |
| Production Database（公開DB） | 公開 DB は Aiven MySQL。ローカル開発では現行ローカル MySQL を使用してよい |
| Session（セッション） | Controller / JSP から利用する API は現行 `HttpSession` を維持する。Vercel 公開前に Spring Session JDBC で Session 保存先を外部化する。Spring Security 認証基盤は導入しない |
| Secrets（秘密情報） | ローカルは `.local-secrets/`。Vercel は Vercel Environment Variables。DB 設定名は `DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD`。Gemini API key の設定名は `DOKOTSUBU_GEMINI_API_KEY`。実値は source / Git / 文書へ記載しない |

### 5.3 Screen List（画面一覧）

| Screen ID（画面ID） | Screen Name（画面名） | Purpose / Overview（利用目的・概要） |
|---|---|---|
| SCR-001 | ログイン入口 | アプリ入口。ログインフォームと新規登録導線 |
| SCR-002 | ユーザー登録 | 登録フォーム |
| SCR-003 | ユーザー登録完了 | 登録成功の表示 |
| SCR-004 | ログイン結果 | ログイン成功 / 失敗の表示 |
| SCR-005 | メイン | 一覧・投稿・検索・Gemini 一言 |
| SCR-006 | つぶやき編集 | 自分の投稿本文の編集 |
| SCR-007 | ログアウト | ログアウト完了の表示 |

画面項目と遷移の詳細は [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md) を正とする。

### 5.4 API Overview（API一覧の概要）

Phase 1 の外部公開 REST API は無い。次は Spring MVC のアプリケーションエンドポイントである。

| API ID | API Name / Endpoint Overview（API名 / エンドポイント概要） | Purpose（用途） |
|---|---|---|
| API-001 | `GET/POST /Register` | ユーザー登録 |
| API-002 | `GET/POST /Login` | ログイン入口誘導 / ログイン処理 |
| API-003 | `GET /Logout` | ログアウト |
| API-004 | `GET/POST /Main` | 一覧表示 / 投稿 + Gemini |
| API-005 | `GET /SearchMutter` | 検索 |
| API-006 | `GET/POST /UpdateMutter` | 編集画面 / 更新 |
| API-007 | `POST /DeleteMutter` | 削除 |

詳細は [05_ARCHITECTURE_DESIGN.md](./05_ARCHITECTURE_DESIGN.md) および [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md) を正とする。エンドポイント別の詳細は [API_SPEC.md](../specs/API_SPEC.md) を参照。

### 5.5 Data Overview（データ種別・件数規模の概要）

| Data Type（データ種別） | Estimated Volume（想定件数・規模） | Notes（備考） |
|---|---|---|
| USERS | ローカル学習利用。上限未指定 | password は hash のみ保存する。`GENDER` / `AGE_FEELING` を保持する。`NAME` は論理削除済みを含めて一意 |
| MUTTERS | ローカル学習利用。上限未指定 | 投稿者との関連付けを持つ。論理削除は `DELETED_AT` |

詳細は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) を正とする。

### 5.6 Mapping to Request Definition（要求定義との対応マッピング）

| User Story ID（ユーザーストーリー ID） | Functional Requirement ID（対応する機能要件 ID） |
|---|---|
| US-001 | FR-001 |
| US-002 | FR-002 |
| US-003 | FR-003 |
| US-004 | FR-004 |
| US-005 | FR-005 |
| US-006 | FR-006 |
| US-007 | FR-007 |
| US-008 | FR-008 |
| US-009 | FR-009 |

---

## 6. Open Issues（未決事項）

本文書では Open Issue を保持しない。データ構造の詳細は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) を正とする。

---

## 7. Handoff to Detail Design（詳細設計への引き継ぎ）

セキュリティ設計は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md)、画面遷移は [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md)、構成は [05_ARCHITECTURE_DESIGN.md](./05_ARCHITECTURE_DESIGN.md) へ引き継ぐ。
