# Operation and Handoff Design（運用・詳細設計引き継ぎ）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | OPS-001 |
| Version（バージョン） | 1.6 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-10-04 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | README.md / 01_REQUEST_DEFINITION.md / 03_DATA_AND_SECURITY_DESIGN.md / 05_ARCHITECTURE_DESIGN.md |

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
- 現行アプリ正本は `DokoTsubu2/`。Phase 1 実装では変更せず、機能確認・挙動比較の参照元とする
- Spring Boot Application は `DokoTsubu3/` に新規作成する

---

## 5. Definition Details（定義内容）

### 5.1 Handoff Items to Detail Design（詳細設計への引き継ぎ事項）

| ID | Handoff Item（引き継ぎ事項） | Details / Background（詳細・背景） |
|---|---|---|
| HO-001 | 機能基準 | `DokoTsubu2` の現行機能を基準とする。`DokoTsubu2/` は Phase 1 実装で変更せず、機能確認・挙動比較の参照元として使用する。Spring Boot Application は `DokoTsubu3/` に新規作成する |
| HO-002 | Legacy 不整合 | Legacy 文書の不整合をそのまま実装しない。正本は `docs/design/` |
| HO-003 | 秘密情報 | secrets を Git へ入れない。実値を source / 文書に書かない。ローカルは `.local-secrets/`。Vercel は Environment Variables。DB 設定名は `DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD`。Gemini API key の設定名は `DOKOTSUBU_GEMINI_API_KEY` |
| HO-004 | 外部設定 | DB 接続情報と Gemini API key（`DOKOTSUBU_GEMINI_API_KEY`）を Spring 外部設定へ移す。絶対パス JSON は使わない |
| HO-005 | DB 実体 | Development は現行ローカル MySQL。Production は Aiven MySQL。Schema `dokotsubu`、Domain tables `USERS` / `MUTTERS`、`MUTTERS.TEXT VARCHAR(255)`、FK なし。構造は同一。2026-09-28 の Aiven 実接続スパイクで現行 DDL・JDBC・FR-001・FR-002 が変更なしで動作することを確認済み。Domain table `USERS` / `MUTTERS` の構造変更は行わない |
| HO-006 | 認証基盤 | Spring Security 認証基盤は導入しない。Application API は `HttpSession` を維持する。Vercel 公開用に Session 保存先を Spring Session JDBC + Aiven MySQL へ外部化する。Spring Session 用テーブルは domain table とは分ける。Spring Session JDBC はローカル MySQL で実装・確認済みである。Aiven MySQL を接続先とする Vercel 本番デプロイは Ready で、ログイン画面表示と存在しないユーザーのログイン失敗画面を確認済みである。ただし、正しいログインと複数インスタンス間の Session 維持は本番未確認である |
| HO-007 | 永続化 | JPA / Hibernate / Spring Data へ置換しない。JDBC を維持する |

### 5.2 Implementation Constraints and Notes（実装時の注意点・制約）

- `DokoTsubu2/` は Phase 1 実装で変更しない
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
- ID だけの UPDATE / DELETE は禁止する
- 投稿者認可は DB FK に依存させない
- `loginUser` に password を入れない
- password は BCrypt hash で保存し、平文保存・平文比較を廃止する
- Phase 1 では既存ユーザーを維持する。Spring Boot 切替前に既存の平文 password を BCrypt hash へ一度だけ移行する
- Application に平文 / BCrypt の恒久的な二重認証ロジックを持たせない
- 実 DB への password 更新は今回実施しない。実際の移行実行時は、対象・影響・復旧手段を確認してから実施する
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
- Vercel 公開用に Session 保存先を Spring Session JDBC + Aiven MySQL へ外部化する。Spring Session JDBC はローカル MySQL で実装・確認済みである。Aiven MySQL を接続先とする Vercel 本番デプロイは Ready で、ログイン画面表示と存在しないユーザーのログイン失敗画面を確認済みである。ただし、正しいログインと複数インスタンス間の Session 維持は本番未確認である
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

外部 Tomcat への必須配備は行わない。Cloud Run は採用しない。Staging 手順は対象外。上表のうち手順1〜4を実施し、Vercel 本番デプロイは Ready、ログイン画面表示と存在しないユーザーのログイン失敗画面を確認した。手順5（Aiven MySQL への接続）、複数インスタンス間の Session 維持、残りの業務機能は本番未確認である。詳細な設定・再デプロイ・画面操作は [Deployment and Operation Guide（デプロイ・運用手順書）](../DEPLOYMENT_AND_OPERATION_GUIDE.md) を参照。ローカル開発では現行ローカル MySQL を使用してよい。

### 5.5 Monitoring, Alerts, and Incident Response（監視・アラート・障害対応方針）

本文書では対象外。理由: Phase 1 はローカル移行であり、監視基盤は対象外とする。

### 5.6 Operational Constraints and Maintenance（運用上の制約・定期メンテナンス）

- Git に Gemini API key / DB password / その他秘密情報を置かない
- ローカル秘密情報は `.local-secrets/` とし、Git 管理外とする
- Vercel の秘密情報は Vercel Environment Variables（`DOKOTSUBU_DB_URL` / `DOKOTSUBU_DB_USERNAME` / `DOKOTSUBU_DB_PASSWORD` / `DOKOTSUBU_GEMINI_API_KEY`）とする。実値は文書へ記載しない
- 定期メンテナンス方針は Phase 1 対象外

---

## 6. Open Issues（未決事項）

本文書では Open Issue を保持しない。

---

## 7. Handoff to Detail Design（詳細設計への引き継ぎ）

実装担当が最初に守る要点:

1. `DokoTsubu2` を機能基準とし、Legacy 不整合を実装しない。`DokoTsubu2/` は Phase 1 実装で変更しない。Spring Boot Application は `DokoTsubu3/` に新規作成し、`DokoTsubu2` のソースを一括コピーして開始しない。必要な機能を設計正本に従い段階的に `DokoTsubu3` へ実装する
2. secrets を Git / source に入れず、DB / Gemini 設定を外部化する。ローカルは `.local-secrets/`、Vercel は Environment Variables
3. Development DB は現行ローカル MySQL、Production DB は Aiven MySQL。Schema `dokotsubu`、Domain tables `USERS` / `MUTTERS`、`TEXT VARCHAR(255)`、FK なし。Domain table `USERS` / `MUTTERS` の構造変更は行わない
4. Spring Security 認証基盤と JPA 系を追加しない。Vercel 公開用に Session 保存先を Spring Session JDBC で外部化する。ローカル MySQL で実装・確認済みである。Aiven MySQL を接続先とする Vercel 本番デプロイは Ready で、ログイン画面表示と存在しないユーザーのログイン失敗画面を確認済みである。ただし、正しいログインと複数インスタンス間の Session 維持は本番未確認である。公開先は Vercel とし、Cloud Run は採用しない
5. 既存ユーザーは維持する。平文 password は Spring Boot 切替前に BCrypt へ一度だけ移行し、Application に二重認証ロジックを持たせない。実 DB への password 更新は今回実施しない
