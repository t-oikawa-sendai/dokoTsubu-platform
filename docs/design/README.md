# Design Documents Index（設計書一覧）

<!--
README Writing Policy（README作成方針）
- README は設計書群の表紙・入口とする
- 文章は最短にする。詳細は個別 Doc へ誘導する
- スクリーンショットは最小サイズの代表画像のみ掲載する
- `screenshots/thumbnail/` は README 用の小さい代表スクリーンショットを配置する
- `screenshots/full/` は個別 Doc 用の大きいスクリーンショットを配置する
- README では thumbnail の代表画像のみを使用し、詳細画像は 04_UI_AND_FLOW_DESIGN.md から full を参照する
- 大きいスクリーンショット・画面項目説明・操作フローは 04_UI_AND_FLOW_DESIGN.md へ分離する
- 個人情報・機密情報・APIキー・トークンが写る画像は使用禁止
- 存在しないスクリーンショットは掲載しない
-->

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | README-001 |
| Version（バージョン） | 1.3 |
| Status（ステータス） | Approved |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-09-28 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | docs/standards/DESIGN_DOCUMENT_STANDARD.md / [CHANGELOG.md](../../CHANGELOG.md)（リポジトリルート） |

> 詳細な変更履歴はリポジトリルートの [CHANGELOG.md](../../CHANGELOG.md) を参照。

---

## Table of Contents（目次）

1. [Project Overview（プロジェクト・機能の概要）](#1-project-overviewプロジェクト機能の概要)
2. [Problem / Solution / Benefit Summary（問題・解決・効果の概要）](#2-problem-solution-benefit-summary問題解決効果の概要)
3. [Screen Overview（画面概要）](#3-screen-overview画面概要)
4. [Design Documents Index（設計書一覧）](#4-design-documents-index設計書一覧)
5. [Overall Design Policy（設計上の全体方針・前提）](#5-overall-design-policy設計上の全体方針前提)
6. [Glossary（用語集・略語定義）](#6-glossary用語集略語定義)
7. [Document Owners and Reviewers（文書管理者・レビュアー一覧）](#7-document-owners-and-reviewers文書管理者レビュアー一覧)

---

## 1. Project Overview（プロジェクト・機能の概要）

`dokoTsubu-platform` は、つぶやき共有アプリ「どこつぶ」のリポジトリである。`DokoTsubu2/` は現行 Application の正本であり、Phase 1 の機能・挙動比較基準として保持する。

Phase 1 では `DokoTsubu2/` を直接 Spring Boot 化しない。新規 `DokoTsubu3/` を Spring Boot 版 Application の実装先とし、`DokoTsubu2` の現行機能・挙動を基準として移行する。登録・ログイン・ログアウト・一覧・投稿・検索・編集・削除・Gemini コメント生成は維持する。

---

## 2. Problem / Solution / Benefit Summary（問題・解決・効果の概要）

| Item（項目） | Summary（概要） | Detail Document（詳細文書） |
|---|---|---|
| Current Problems（現在の問題点） | Servlet / Eclipse 依存、秘密情報のソース配置、編集削除の認可欠落 | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) |
| Development Purpose（開発目的） | `DokoTsubu2` の現行機能を維持し、新規 `DokoTsubu3` として Spring Boot 化し、Vercel で公開できる構成にする | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) |
| Solution Approach（解決方針） | Spring MVC + JSP + JDBC。秘密情報は外部設定。編集削除は投稿者本人限定 | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) |
| System Functions（システム機能） | 登録、ログイン、ログアウト、一覧、投稿、検索、編集、削除、Gemini コメント | [02_REQUIREMENTS_DEFINITION.md](./02_REQUIREMENTS_DEFINITION.md) |
| Expected Benefits（期待効果） | 現行機能を保ちつつ、秘密値排除と本人以外の更新削除防止ができる | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) |
| Completion Criteria（完成判定基準） | Spring Boot 起動、MySQL 接続、Gemini 維持、秘密値非所持、他人投稿の操作不可 | [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) |

---

## 3. Screen Overview（画面概要）

代表画面はメイン画面（一覧・投稿・検索・Gemini 一言）である。

承認済みスクリーンショットは存在しないため、画像は掲載しない。

詳細（画面一覧・項目定義・操作フロー）: [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md)

---

## 4. Design Documents Index（設計書一覧）

| File（ファイル名） | Document Name（文書名） | Status（ステータス） | Version（バージョン） | Owner（担当者） |
|---|---|---|---|---|
| [01_REQUEST_DEFINITION.md](./01_REQUEST_DEFINITION.md) | Request Definition（要求定義） | Approved | 1.3 | Takashi Oikawa |
| [02_REQUIREMENTS_DEFINITION.md](./02_REQUIREMENTS_DEFINITION.md) | Requirements Definition（要件定義） | Approved | 1.2 | Takashi Oikawa |
| [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) | Data and Security Design（データ・セキュリティ設計） | Approved | 1.2 | Takashi Oikawa |
| [04_UI_AND_FLOW_DESIGN.md](./04_UI_AND_FLOW_DESIGN.md) | UI and Flow Design（UI・フロー設計） | Approved | 1.1 | Takashi Oikawa |
| [05_ARCHITECTURE_DESIGN.md](./05_ARCHITECTURE_DESIGN.md) | Architecture Design（アーキテクチャ設計） | Approved | 1.3 | Takashi Oikawa |
| [06_OPERATION_AND_HANDOFF.md](./06_OPERATION_AND_HANDOFF.md) | Operation and Handoff Design（運用・詳細設計引き継ぎ） | Approved | 1.3 | Takashi Oikawa |

---

## 5. Overall Design Policy（設計上の全体方針・前提）

- `DokoTsubu2/` は現行 Application の正本・機能比較基準として保持する。Phase 1 では直接 Spring Boot 化しない
- Phase 1 の Spring Boot 版実装先は新規 `DokoTsubu3/`。`DokoTsubu2` の現行機能・挙動を基準として移行する
- Spring Boot / Spring MVC / JSP / JDBC / MySQL / Gemini API
- WAR + embedded Tomcat。外部 Tomcat 必須にはしない
- Spring Security 認証基盤、JPA / Hibernate、Spring Data、Thymeleaf は導入しない
- セッションキーは `loginUser`。保存は user id と user name のみ
- password は BCrypt hash。平文保存と比較は廃止
- 編集・削除はログイン済みかつ投稿者本人のみ
- 秘密値は source / Git に置かない。絶対パス JSON 設定は廃止する
- context path は `/dokoTsubu`。コードへ固定文字列として書かない
- Gemini 初期 model は `gemini-2.5-flash-lite`。temperature の正は `1.5`
- Legacy 文書の不整合は Phase 1 正本へ持ち込まない
- 現行ライブ MySQL 実体は 2026-09-12 実測で確認済み。詳細は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) を正とする
- Deploy: Vercel
- Production DB: Aiven MySQL
- Session: HttpSession API + Spring Session JDBC
- Local DB: 現行 MySQL

---

## 6. Glossary（用語集・略語定義）

| Term / Abbreviation（用語・略語） | Definition（定義） |
|---|---|
| dokoTsubu-platform | 本リポジトリ名 |
| DokoTsubu2 | 現行 Servlet版。Phase 1 の機能・挙動比較基準 |
| DokoTsubu3 | Phase 1 で新規作成する Spring Boot 版 |
| どこつぶ / DokoTsubu | アプリ名。外部 URL の context path は `/dokoTsubu` |
| Phase 1 | Spring Boot 移行フェーズ |
| Legacy 文書 | `docs/設計書.md` / `docs/API仕様書.md` / `docs/AI設定仕様書.md` / `docs/環境構築手順書.md` 等。参照のみ |
| loginUser | セッションキー。user id と user name のみを保持する |
| aiMsg | 投稿直後にメイン画面へ渡す Gemini 一言 |

---

## 7. Document Owners and Reviewers（文書管理者・レビュアー一覧）

| Role（役割） | Name（氏名） | Assigned Documents（担当文書） |
|---|---|---|
| Document Owner（文書管理者） | Takashi Oikawa | All Documents（全文書） |
| Reviewer（レビュアー） | Takashi Oikawa | All Documents（全文書） |
