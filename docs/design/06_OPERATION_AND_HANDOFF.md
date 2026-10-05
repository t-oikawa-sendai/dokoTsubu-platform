# Operation and Handoff Design（運用・詳細設計引き継ぎ）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | OPS-001 |
| Version（バージョン） | 1.9 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../../README.md) / [README.md](./README.md) / [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) / [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) / [05_ARCHITECTURE_DESIGN.md](./05_ARCHITECTURE_DESIGN.md) / [ENVIRONMENT_SETUP_GUIDE.md](../ENVIRONMENT_SETUP_GUIDE.md) / [DEPLOYMENT_AND_OPERATION_GUIDE.md](../DEPLOYMENT_AND_OPERATION_GUIDE.md) |

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

本文書は、Phase 1 実装担当への制約と、ローカル起動および Vercel 公開に必要な運用前提を定義する。

---

## 2. Scope（対象範囲）

- 実装時の遵守事項
- 秘密情報の置き方
- 確認済み DB 実体の引き継ぎ
- ローカル executable WAR の起動前提
- Vercel 公開と Aiven MySQL 接続の運用概要

---

## 3. Out of Scope（対象外範囲）

- Cloud Run
- Staging 運用
- 監視基盤の新設
- Legacy 文書の改訂
- Application 実装そのもの

---

## 4. Assumptions（前提条件）

- 設計正本は `docs/design/` の 7 文書である
- Legacy 文書は参照用であり、不整合をそのまま実装してはならない
- 現行アプリ正本は `DokoTsubu2/`。Phase 1 実装では変更しない。例外は秘密情報除去のみとする。機能確認・挙動比較の参照元とする
- Spring Boot Application は `DokoTsubu3/` に新規作成する

---

## 5. Definition Details（定義内容）

### 5.1 Handoff Items to Detail Design（詳細設計への引き継ぎ事項）

| ID | Handoff Item（引き継ぎ事項） | Details / Background（詳細・背景） |
|---|---|---|
| HO-001 | 機能基準 | `DokoTsubu2` の現行機能を基準とする。`DokoTsubu2/` は Phase 1 実装で変更しない。例外は秘密情報除去のみとする。機能確認・挙動比較の参照元として使用する。Spring Boot Application は `DokoTsubu3/` に新規作成する |
| HO-002 | Legacy 不整合 | Legacy 文書の不整合をそのまま実装しない。正本は `docs/design/` |
| HO-003 | 秘密情報 | secrets を Git へ入れない。実値を source / 文書に書かない。ローカルは `.local-secrets/`。Vercel は Environment Variables。DB 設定名は `DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD`。Gemini API key の設定名は `DOKOTSUBU_GEMINI_API_KEY` |
| HO-004 | 外部設定 | DB 接続情報と Gemini API key（`DOKOTSUBU_GEMINI_API_KEY`）を Spring 外部設定へ移す。絶対パス JSON は使わない |
| HO-005 | DB 実体 | Development は現行ローカル MySQL。Production は Aiven MySQL。Schema `dokotsubu`、Domain tables `USERS` / `MUTTERS`、`MUTTERS.TEXT VARCHAR(255)`、FK なし。移行完了後の構造は同一。2026-09-28 の Aiven 実接続スパイクで現行 DDL・JDBC・FR-001・FR-002 が変更なしで動作することを確認済み。この結果は Target Schema 適用済みを意味しない。Schema 移行時に、既存 `USERS` / `MUTTERS` の Domain データを初期化し、Target Schema へ変更する。実施は Application 停止中とする。table rename / schema rename / `TEXT` 長変更 / FK 追加は行わない |
| HO-006 | 認証基盤 | Spring Security 認証基盤は導入しない。Application API は `HttpSession` を維持する。Vercel 公開用に Session 保存先を Spring Session JDBC + Aiven MySQL へ外部化する。Spring Session 用テーブルは domain table とは分ける |
| HO-007 | 永続化 | JPA / Hibernate / Spring Data へ置換しない。JDBC を維持する |

### 5.2 Implementation Constraints and Notes（実装時の注意点・制約）

- `DokoTsubu2/` は Phase 1 実装で変更しない。例外は秘密情報除去のみとする
- 現行機能確認・挙動比較の参照元として `DokoTsubu2/` を使用する
- Spring Boot Application は `DokoTsubu3/` に新規作成する
- `DokoTsubu2` のソースを一括コピーして開始しない
- 必要な機能を設計正本に従い段階的に `DokoTsubu3` へ実装する
- 現行機能（登録・ログイン・ログアウト・一覧・投稿・検索・編集・削除・Gemini）を維持する
- 登録は `username` / `password`、ログインは `name` / `pass` を維持する
- 一覧は ID 降順、検索は `MUTTERS.TEXT LIKE` を維持する
- `GET /Login` を実装する（ログイン入口へ戻す）
- Update 失敗時は編集画面へ戻す
- 編集・削除はログイン済みかつ `MUTTERS.USER_ID == loginUser.id` のときだけ許可する
- 削除は `POST /DeleteMutter` のみとする。`GET /DeleteMutter` では削除しない
- `POST /Main`、`POST /UpdateMutter`、`POST /DeleteMutter` は Session 保存型 CSRF token を Spring MVC Interceptor で照合する。Spring Security は導入しない。token なしまたは不一致では状態変更しない
- ID だけの UPDATE / DELETE は禁止する
- 投稿者認可は DB FK に依存させない
- `loginUser` に password を入れない
- password は BCrypt hash で保存し、平文保存・平文比較を廃止する
- Schema 移行の条件と手順は §5.7 を正とする
- DB アクセス対象は Schema `dokotsubu`、Tables `USERS` / `MUTTERS`。`MUTTERS.TEXT` は `VARCHAR(255)`。FK はない
- table rename / schema rename / `TEXT` 長変更 / FK 追加は、今回の Spring Boot 移行に含めない
- Gemini は投稿成功後に同期呼び出しする。失敗しても投稿は rollback しない
- model は `gemini-3.5-flash-lite`
- `temperature` / `top_p` / `top_k` は明示指定しない
- context path は `/dokoTsubu`。Controller / JSP に固定文字列として書かない
- Application から `BakaUpArea` を参照しない
- ローカル秘密情報は `.local-secrets/` を使い、Git 管理しない
- Vercel の秘密情報は Vercel Environment Variables に置く。設定名は `DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD` / `DOKOTSUBU_GEMINI_API_KEY`。実値は source / Git / 文書へ記載しない
- 公開先は Vercel。Project Root は `DokoTsubu3`。`Dockerfile.vercel` を使用する。Cloud Run は採用しない
- 公開 DB は Aiven MySQL。ローカル開発では現行ローカル MySQL を使用してよい
- Vercel 公開用に Session 保存先を Spring Session JDBC + Aiven MySQL へ外部化する。Spring Session 用テーブルは domain table と分ける。Spring Session JDBC はローカル MySQL で実装・確認済みである
- Thymeleaf を導入しない
- 不要な機能追加・抽象化をしない

### 5.3 Test Policy and Acceptance Criteria（テスト方針・受け入れ基準）

| Type（種別） | Policy / Criteria（方針・基準） |
|---|---|
| Unit Test（単体テスト） | 新規テスト基盤の導入は Phase 1 必須としない |
| Integration Test（結合テスト） | 新規必須としない |
| System Test（システムテスト） | ローカル executable WAR で現行機能が維持されることを確認する |
| Acceptance Test（受け入れテスト） | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) の SC-001〜SC-009 を満たすこと |

### 5.4 Deployment and Release Overview（デプロイ・リリース手順概要）

| Step（ステップ） | Task（作業内容） | Owner（担当） |
|---|---|---|
| 1 | `DokoTsubu3` を build する | 実装担当 |
| 2 | Spring Session JDBC により Session を外部化する | 実装担当 |
| 3 | Vercel Environment Variables を設定する | 実装担当 |
| 4 | `Dockerfile.vercel` により Vercel へ配置する | 実装担当 |
| 5 | Aiven MySQL への接続を確認する | 実装担当 |
| 6 | 公開 URL で受け入れ確認する | 実装担当 |

外部 Tomcat への必須配備は行わない。Cloud Run は採用しない。Staging 手順は対象外。公開の実施状況と本番の確認範囲は [Deployment and Operation Guide（デプロイ・運用手順書）](../DEPLOYMENT_AND_OPERATION_GUIDE.md) 第1節・6.1節を正とする。詳細な設定・再デプロイ・画面操作も同書を参照。ローカル開発では現行ローカル MySQL を使用してよい。

### 5.5 Monitoring, Alerts, and Incident Response（監視・アラート・障害対応方針）

本文書では対象外。理由: Phase 1 はローカル移行であり、監視基盤は対象外とする。

### 5.6 Operational Constraints and Maintenance（運用上の制約・定期メンテナンス）

- Git に Gemini API key / DB password / その他秘密情報を置かない
- ローカル秘密情報は `.local-secrets/` とし、Git 管理外とする
- Vercel の秘密情報は Vercel Environment Variables（`DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD` / `DOKOTSUBU_GEMINI_API_KEY`）とする。実値は文書へ記載しない
- 定期メンテナンス方針は Phase 1 対象外

### 5.7 Migration（移行）

対象 Schema は `dokotsubu`、対象 Domain tables は `USERS` / `MUTTERS` である。Target Schema の定義は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) を正とする。

#### 5.7.1 Migration Preconditions（移行の前提条件）

- Application 停止中に実施する
- 実際のデータ初期化や Schema 変更は、この文書更新では実行しない

#### 5.7.2 Domain Data Initialization（Domain データ初期化）

- Schema 移行時に、既存 `USERS` / `MUTTERS` の Domain データを初期化する
- `MUTTERS` の既存 Domain データを初期化する
- `USERS` の既存 Domain データを初期化する
- 既存 User は移行後へ引き継がない

#### 5.7.3 Schema Migration（Schema 変更）

- Target Schema へ変更する
- 承認済みの Schema 変更は、論理削除、`CREATED_AT` / `UPDATED_AT` / `DELETED_AT`、`GENDER`、`AGE_FEELING` に限る
- table rename / schema rename / `TEXT` 長変更 / FK 追加は行わない

#### 5.7.4 Password Policy（password 方針）

- 旧 password 移行は行わない
- 移行後の新規 User は、登録時から BCrypt hash を保存する
- 平文 password 認証との互換処理は作らない

#### 5.7.5 Session Invalidation（Session 無効化）

- Spring Session JDBC に残る既存 Session を、サービス再開前に無効化する

#### 5.7.6 Service Resume Conditions（サービス再開条件）

- Domain データ初期化、Target Schema への変更、既存 Session の無効化が完了していること
- その後、新しい Application でサービスを再開する

---

## 6. Open Issues（未決事項）

本文書では Open Issue を保持しない。

---

## 7. Handoff to Detail Design（詳細設計への引き継ぎ）

実装担当が最初に守る要点:

1. `DokoTsubu2` を機能基準とし、Legacy 不整合を実装しない。`DokoTsubu2/` は Phase 1 実装で変更しない。例外は秘密情報除去のみとする。Spring Boot Application は `DokoTsubu3/` に新規作成し、`DokoTsubu2` のソースを一括コピーして開始しない。必要な機能を設計正本に従い段階的に `DokoTsubu3` へ実装する
2. secrets を Git / source に入れず、DB / Gemini 設定を外部化する。ローカルは `.local-secrets/`、Vercel は Environment Variables
3. Development DB は現行ローカル MySQL、Production DB は Aiven MySQL。Schema `dokotsubu`、Domain tables `USERS` / `MUTTERS`、`TEXT VARCHAR(255)`、FK なし。Schema 移行時に、既存 `USERS` / `MUTTERS` の Domain データを初期化し、Target Schema へ変更する。実施は Application 停止中とする。table rename / schema rename / `TEXT` 長変更 / FK 追加は行わない
4. Spring Security 認証基盤と JPA 系を追加しない。`POST /Main`、`POST /UpdateMutter`、`POST /DeleteMutter` の CSRF 照合は Spring MVC Interceptor と Session 保存型 token で行う。Vercel 公開用に Session 保存先を Spring Session JDBC で Aiven MySQL へ外部化し、Application API は `HttpSession` を維持する。公開先は Vercel とし、Cloud Run は採用しない
5. Schema 移行時に、既存 `USERS` / `MUTTERS` の Domain データを初期化する。Application 停止中に、`MUTTERS` の既存 Domain データと `USERS` の既存 Domain データを初期化する。旧 password 移行は行わない。Target Schema へ変更する。Spring Session JDBC に残る既存 Session をサービス再開前に無効化し、その後、新しい Application でサービスを再開する。実際のデータ初期化や Schema 変更は、この文書更新では実行しない
