<!--
Program Name: dokoTsubu-platform CHANGELOG
Language: Markdown
Function: dokoTsubu-platformの変更履歴正本
Created: 2026-09-11
Last Updated: 2026-10-04
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Canonical Templateから新設したRepository固有履歴正本。過去Application履歴は推測して追加しない。Runtime側に CHANGELOG_TEMPLATE.md は作成しない。
-->

# CHANGELOG

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | CHANGELOG-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-11 |
| Last Updated（最終更新日） | 2026-10-04 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | /CONSTITUTION.md / /AGENTS.md / /docs/design/README.md |

---

## Purpose（目的）

本ファイルは、本Repositoryの変更履歴正本である。

本ファイルの初期状態は Canonical Template からの配布物である。
過去Repository履歴は含まない。推測履歴は記載しない。

## What to Record（記録対象）

利用者・開発者・運用担当者・レビュー担当者の判断に影響する重要変更を記録する。
意味・仕様・運用・構成に影響しない軽微な変更（明白な誤字、空白調整など）は原則記録不要。

## Category Definitions（変更分類）

| Category | 日本語 | 用途 |
|---|---|---|
| Added | 追加 | 新しい機能、章、文書、運用項目 |
| Changed | 変更 | 既存仕様、構成、手順の変更 |
| Fixed | 修正 | 誤り、不整合、不具合の修正 |
| Removed | 削除 | 機能、記述、文書の廃止 |
| Security | セキュリティ | セキュリティまたは個人情報保護上の変更 |

Git 履歴は差分確認の補助として利用する。CHANGELOG の代替とはしない。

## Change History（変更履歴）

新しい履歴を上、古い履歴を下に記載する。

Governance導入Baselineは、Governance導入成功後に記録する。
導入前に成功した事実として記録しない。

| Version | Date | Document | Category | Changes | Author |
|---|---|---|---|---|---|
| 0.1 | 2026-10-04 | `/docs/DEPLOYMENT_AND_OPERATION_GUIDE.md` | Added | Vercel 公開までの手順、Production 設定、再デプロイ、利用操作、実測済み範囲と未確認項目を記録 | Takashi Oikawa |
| 0.3 | 2026-10-04 | `/README.md` | Fixed | Vercel 未公開との旧記述を現行公開状態へ修正し、運用手順書へ誘導 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/README.md` | Changed | 運用手順書への案内を追加し、レビュー中の設計書の版・状態を一覧へ反映 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Fixed | Aiven・Vercel の旧未確認記述を本番の確認範囲へ修正。複数インスタンス間 Session 維持は未確認と明記 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Fixed | Session 実装・公開の状態を実測範囲へ修正。設計方針は維持 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Fixed | 公開手順の実施結果と本番未確認項目を記録し、詳細運用手順書を参照 | Takashi Oikawa |
| - | 2026-10-04 | `/DokoTsubu3/Dockerfile.vercel` | Fixed | 起動時の `PORT` がシェルのプロセス ID として展開され、Java の `server.port` に数値で渡らない不具合を修正 | Takashi Oikawa |
| 0.2 | 2026-10-04 | `/README.md` | Changed | 教材の H2 版から Version 3 までの累積変更を追記。Version 3 は Spring Boot 化、本人限定の編集・削除、秘密情報の外部設定、Spring Session JDBC。Vercel 公開は対象であり未実施。AI が設計・実装・レビューを分担 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/README.md` | Changed | 設計書一覧の 03 を 1.4、05 と 06 を 1.5 へ更新 | Takashi Oikawa |
| 1.4 | 2026-10-04 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Fixed | 認証節と引き継ぎの Spring Session JDBC を、ローカル MySQL で実装・確認済み、Aiven への適用と Vercel 公開は未確認へ更新。設計方針は維持 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Fixed | セッション保存欄と引き継ぎの未実装記述を、ローカル MySQL で実装・確認済み、Aiven への適用と Vercel 公開は未確認へ更新。設計方針は維持 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Fixed | HO-006、実装制約、公開手順、引き継ぎの未実装記述を、ローカル MySQL で実装・確認済み、Aiven への適用と Vercel 公開は未確認へ更新。設計方針は維持 | Takashi Oikawa |
| 1.4 | 2026-09-29 | `/docs/design/README.md` | Changed | Gemini modelを `gemini-3.5-flash-lite` へ更新。Gemini API key環境変数を `DOKOTSUBU_GEMINI_API_KEY` と確定。Gemini 3.xに合わせ temperature / top_p / top_k の固定指定を廃止 | Takashi Oikawa |
| 1.3 | 2026-09-29 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | Gemini modelを `gemini-3.5-flash-lite` へ更新。Gemini API key環境変数を `DOKOTSUBU_GEMINI_API_KEY` と確定。Gemini 3.xに合わせ temperature / top_p / top_k の固定指定を廃止 | Takashi Oikawa |
| 1.3 | 2026-09-29 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | Gemini API key環境変数を `DOKOTSUBU_GEMINI_API_KEY` と確定。API key実値を source / Git / 文書 / ログへ記載しない方針を明記 | Takashi Oikawa |
| 1.4 | 2026-09-29 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | Gemini modelを `gemini-3.5-flash-lite` へ更新。Gemini API key環境変数を `DOKOTSUBU_GEMINI_API_KEY` と確定。Gemini 3.xに合わせ temperature / top_p / top_k の固定指定を廃止 | Takashi Oikawa |
| 1.4 | 2026-09-29 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | Gemini modelを `gemini-3.5-flash-lite` へ更新。Gemini API key環境変数を `DOKOTSUBU_GEMINI_API_KEY` と確定。Gemini 3.xに合わせ temperature / top_p / top_k の固定指定を廃止 | Takashi Oikawa |
| 1.3 | 2026-09-29 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.3 | 2026-09-28 | `/docs/design/README.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.3 | 2026-09-28 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.2 | 2026-09-28 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.2 | 2026-09-28 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.3 | 2026-09-28 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | Vercel を DokoTsubu3 公開先として確定。Aiven MySQL を 2026-09-28 実接続スパイク PASS 後に正式採用。現行 JDBC / DDL / FR-001 / FR-002 が Aiven で変更なしに動作することを確認。Vercel 公開時の Session を Spring Session JDBC で外部化する方針を確定。Spring Security 認証基盤は引き続き導入しない | Takashi Oikawa |
| 1.2 | 2026-09-13 | `/docs/design/README.md` | Changed | 現行 `DokoTsubu2` を保持し、Phase 1 Spring Boot版を新規 `DokoTsubu3` として構築する実装配置方針を確定 | Takashi Oikawa |
| 1.2 | 2026-09-13 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | 現行 `DokoTsubu2` を保持し、Phase 1 Spring Boot版を新規 `DokoTsubu3` として構築する実装配置方針を確定 | Takashi Oikawa |
| 1.2 | 2026-09-13 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | 現行 `DokoTsubu2` を保持し、Phase 1 Spring Boot版を新規 `DokoTsubu3` として構築する実装配置方針を確定 | Takashi Oikawa |
| 1.2 | 2026-09-13 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | 現行 `DokoTsubu2` を保持し、Phase 1 Spring Boot版を新規 `DokoTsubu3` として構築する実装配置方針を確定 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | 旧未確認 DB 記述を正本事実へ整合 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | 旧未確認 DB 記述を正本事実へ整合 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/04_UI_AND_FLOW_DESIGN.md` | Changed | 旧未確認 DB 記述を正本事実へ整合 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | ライブ MySQL 実体を確認し TBD-001 を解消。Schema `dokotsubu`、Tables `USERS` / `MUTTERS`、`TEXT VARCHAR(255)`、FK なしを設計へ反映。Phase 1 は現行 DB を維持する。既存平文 password は Spring Boot 切替前に BCrypt へ一度だけ移行し、Application に二重認証ロジックを持たせない | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | MySQL 記載を実 DB 事実へ合わせ、Phase 1 で不要な schema migration を行わない旨を記録。技術スタックを Java 21 / Spring Boot 4.1.1 / Maven / WAR / embedded Tomcat 11.0.x / MySQL 9.6.0 として確定 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | TBD-001 を解消し、実測済み DB 前提を実装担当へ引き継ぐ。password 移行方針を確定 | Takashi Oikawa |
| 1.1 | 2026-09-12 | `/docs/design/README.md` | Changed | TBD-001 解消後の現在状態を反映。01 / 02 / 04 の旧未確認表記を正本事実へ整合 | Takashi Oikawa |
| - | 2026-09-12 | `/.gitignore` | Added | `.local-secrets/` を Git 管理対象外として追加 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/README.md` | Changed | Spring Boot Phase 1 設計7文書を初回 Approved 版 1.0 として確定。一覧 Status / Version / Owner を実ファイルと一致させた | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | 初回 Approved 1.0。重複 TBD-001 を除去し、DB 実体確認は 03 / 06 参照へ委譲 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | 初回 Approved 1.0。重複 TBD-001 を除去し、DB 詳細確認は 03 / 06 へ委譲 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | 初回 Approved 1.0。認証・認可・BCrypt・secret 外部化の設計確定。TBD-001 は未解決のまま維持 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/04_UI_AND_FLOW_DESIGN.md` | Changed | 初回 Approved 1.0 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | 初回 Approved 1.0。Spring MVC / JSP / JDBC / MySQL / WAR の設計確定 | Takashi Oikawa |
| 1.0 | 2026-09-12 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | 初回 Approved 1.0。TBD-001 を実装開始前確認事項として未解決のまま維持 | Takashi Oikawa |
| 0.5 | 2026-09-12 | `/docs/design/README.md` | Changed | Phase 1 Spring Boot 設計の表紙・方針・用語を正本化し、一覧 Version を各実ファイルと一致させた | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | Phase 1 要求定義を正本化。現行機能維持、対象外、成功条件を記録 | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | Phase 1 機能要件とセキュリティ要求を正本化。Gemini を機能として記録 | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Security | users / mutters、BCrypt、session から password 除外、編集削除の本人限定、秘密情報の外部設定を確定 | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/04_UI_AND_FLOW_DESIGN.md` | Fixed | 現行実装の遷移を正とした。GET /Login、Update 失敗時の編集画面復帰、本人以外の操作非表示を記録 | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | Spring MVC / Service / JDBC / JSP / WAR / embedded Tomcat / Gemini 構成を正本化。temperature 1.5 を正とした | Takashi Oikawa |
| 0.3 | 2026-09-12 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | 実装制約を正本化。Legacy 不整合の非実装、secrets 禁止、実装前 DB 確認を記録 | Takashi Oikawa |
| 0.1 | 2026-09-11 | `/CHANGELOG.md` | Added | D-013: Repository固有履歴正本を新設 | Takashi Oikawa |
| 1.3 | 2026-09-11 | `/CONSTITUTION.md` | Changed | D-013: 中央Canonical Source STANDARD_VERSION 1.3 へ同期 | Takashi Oikawa |
| 1.2 | 2026-09-11 | `/AGENTS.md` | Changed | D-013: 中央Canonical Source `AGENTS_SOURCE.md` STANDARD_VERSION 1.2 へ同期 | Takashi Oikawa |
| 0.4 | 2026-09-11 | `/docs/design/README.md` | Fixed | D-013: CHANGELOG相対パスを `../../../CHANGELOG.md` から `../../CHANGELOG.md` へ修正 | Takashi Oikawa |
