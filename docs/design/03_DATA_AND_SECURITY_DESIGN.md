# Data and Security Design（データ・セキュリティ設計）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | DATA-001 |
| Version（バージョン） | 1.0 |
| Status（ステータス） | Approved |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-09-12 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | README.md / 02_REQUIREMENTS_DEFINITION.md / 05_ARCHITECTURE_DESIGN.md |

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

本文書は、Phase 1 のエンティティ、テーブル、認証・認可、秘密情報管理を定義し、実装の基準とする。

---

## 2. Scope（対象範囲）

- エンティティ: User / Mutter
- テーブル: `users` / `mutters`
- 認証（`HttpSession` / `loginUser`）
- 認可（編集・削除の投稿者本人限定）
- password の hash 保存
- Gemini API key および DB 接続情報の外部設定

---

## 3. Out of Scope（対象外範囲）

- Spring Security による認証基盤
- JPA / Hibernate / Spring Data
- 現行ライブ MySQL 実体の推測確定
- 秘密値の文書記載

---

## 4. Assumptions（前提条件）

- Phase 1 設計上のテーブル名は lower-case に統一する
- 現行ライブ MySQL の実テーブル名および実カラムは UNVERIFIED である
- 現行 `DokoTsubu2` の DAO は `users` / `MUTTERS` / `USERS` の表記が混在する。Phase 1 正本は `users` / `mutters` とする
- Application は `BakaUpArea` を参照しない

---

## 5. Definition Details（定義内容）

### 5.1 Entity Definition and ER Diagram（エンティティ定義・ER図）

#### Entity List（エンティティ一覧）

| Entity Name（エンティティ名） | Description（説明） |
|---|---|
| User | 登録利用者。永続化時の password は hash のみ。セッションへは id と name だけを載せる |
| Mutter | つぶやき。投稿者は `user_id` で User に関連する |

#### ER Diagram（ER図）

```mermaid
erDiagram
  users ||--o{ mutters : "id = user_id"
  users {
    int id PK
    varchar name
    varchar pass
  }
  mutters {
    int id PK
    int user_id FK
    varchar text
  }
```

関係: `mutters.user_id -> users.id`

### 5.2 Table Definitions（テーブル定義）

Phase 1 設計値である。ライブ DB の現物確認結果ではない。

#### users

| Column（カラム名） | Type（型） | NOT NULL | PK / FK | Description（説明） |
|---|---|---|---|---|
| id | INT | YES | PK, AUTO_INCREMENT | ユーザー ID |
| name | VARCHAR(100) | YES | UNIQUE | ユーザー名 |
| pass | VARCHAR(255) | YES | - | BCrypt hash のみを保存する。平文は保存しない |

#### mutters

| Column（カラム名） | Type（型） | NOT NULL | PK / FK | Description（説明） |
|---|---|---|---|---|
| id | INT | YES | PK, AUTO_INCREMENT | つぶやき ID |
| user_id | INT | YES | FK → users.id | 投稿者 |
| text | VARCHAR(140) | YES | - | つぶやき本文 |

UNVERIFIED: 現行ライブ MySQL に存在する実テーブル名、実カラム、実 PK / FK。既存 DB の移行要否は実装前の最小確認とする。

### 5.3 Data Access and Role Design（データアクセス権限・ロール設計）

ロールモデルは設けない。ログイン済み利用者のみを扱う。

| Role Name（ロール名） | Accessible Data / Permitted Operations（アクセス可能なデータ / 操作範囲） |
|---|---|
| 未ログイン | 登録、ログイン入口、ログアウト処理 |
| ログイン済み利用者 | 一覧、投稿、検索。編集・削除は `mutters.user_id == loginUser.id` の自分の投稿のみ |

### 5.4 Personal and Confidential Data Policy（個人情報・機密データの取り扱い方針）

- 利用者名と password hash を扱う
- password 平文、Gemini API key、DB password、その他秘密情報を source および Git 管理ファイルへ実値記載しない
- ローカル起動で秘密情報ファイルが必要な場合だけ `/Users/takashioikawa/Dev/dokoTsubu-platform/.local-secrets/` を使用し、Git 管理しない
- Application から `BakaUpArea` を参照してはならない

### 5.5 Encryption, Masking, and Logging Policy（暗号化・マスキング・ログ取得方針）

| Type（種別） | Target（対象） | Policy / Method（方式・方針） |
|---|---|---|
| Encryption（暗号化） | users.pass | 登録時に BCrypt で一方向ハッシュ化する。可逆暗号化は用いない |
| Masking（マスキング） | API key / DB password | 文書・source・Git に実値を書かない |
| Logging（ログ取得） | 秘密値 | ログへ API key / password / DB password を出さない。新規ログ基盤は Phase 1 対象外 |

### 5.6 Security Design Specifications（セキュリティ設計仕様）

| Type（種別） | Design Specification（設計仕様） |
|---|---|
| Authentication（認証） | `HttpSession` を継続する。キーは `loginUser`。保存内容は user id と user name のみ。password はセッションへ保存しない。ログイン必須判定は Spring MVC の共通機構で一元化する。Spring Security の FilterChain 等は導入しない |
| Authorization（認可） | 編集・削除は「ログイン済み」かつ `mutters.user_id == loginUser.id` の両方を満たす場合だけ許可する。ID だけを条件とする UPDATE / DELETE は禁止する。最終判定はサーバー側で行う |
| Access Control（権限管理） | 画面は自分の投稿以外に編集・削除操作を表示しない。画面非表示は補助であり、認可の正ではない |
| Communication（通信） | Gemini API は HTTPS REST。新規の通信暗号化要件は設けない |
| Data Storage（データ保存） | password は BCrypt hash のみ。Gemini API key と DB 接続情報は Spring 外部設定から取得する。`/Users/takashioikawa/Dev/ai-config.json` への絶対パス依存は廃止する |
| Data Disposal（データ廃棄） | ログアウト時にセッションを破棄する。追加の廃棄プロセスは Phase 1 対象外 |
| Personal Data Protection（個人情報保護） | セッションと永続化に password 平文を残さない。秘密値を Git / source に置かない |

ログイン照合は、入力 password と保存済み BCrypt hash を比較する。Spring Security 認証機能全体は導入しない。ハッシュ照合に必要な最小利用は許可する。

---

## 6. Open Issues（未決事項）

| ID | Open Issue（未決事項） | Owner（担当者） | Due Date（期限） | Status（ステータス） |
|---|---|---|---|---|
| TBD-001 | 現行ライブ MySQL の実テーブル名は UNVERIFIED。既存 DB 移行要否は実装前に最小確認する | Takashi Oikawa | 実装前 | Open |

---

## 7. Handoff to Detail Design（詳細設計への引き継ぎ）

- DAO は JDBC を維持する。JPA / Spring Data へ置換しない
- 更新・削除 SQL は必ず `id` と `user_id` の両方を条件にする
- 秘密値の実値を設計書・実装・Git に書かない
- ライブ DB 未確認事項は UNVERIFIED のまま実装前確認へ残す
