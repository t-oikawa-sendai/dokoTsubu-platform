<!--
Program Name: dokoTsubu-platform CHANGELOG
Language: Markdown
Function: dokoTsubu-platformの変更履歴正本
Created: 2026-09-11
Last Updated: 2026-10-05
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
| Last Updated（最終更新日） | 2026-10-05 |
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
| 0.4 | 2026-10-05 | `/README.md` | Changed | Root README を再構成した。Overview、Version 1 / 2 / 3 の位置付けと実コード確認に基づく機能比較、Current Version 3、Vercel / Aiven 概要、関連文書一覧を追加した。Version 3 に至る累積変更・機能一覧・Legacy Tech Stack・Legacy Architecture・Legacy Screen Flow の独立章を統合・削除した。Document ID を `LEGACY-001` から `PROJECT-README-001` へ、Status を Review へ変更した。Version 1 の正本を別 Repository `t-oikawa-sendai/dokoTsubu` と明記した | Takashi Oikawa |
| - | 2026-10-05 | `/docs/specs/` | Added | 現行仕様書の配置先として新設した | Takashi Oikawa |
| 0.1 | 2026-10-05 | `/docs/specs/API_SPEC.md` | Added | Version 3 の API Specification（Target Specification）を新設した。設計正本と DokoTsubu3 の Controller を情報源とした。設計確定済み・未実装の仕様を含むことを明記した | Takashi Oikawa |
| 0.1 | 2026-10-05 | `/docs/specs/GEMINI_INTEGRATION_SPEC.md` | Added | Version 3 の Gemini Integration Specification（Target Specification）を新設した。`GENDER` / `AGE_FEELING` の送信は設計確定済み・未実装であることを明記した。送信する / 送信しないデータ、Profile の扱い、エラー処理、API key 管理を定義した | Takashi Oikawa |
| 0.1 | 2026-10-05 | `/docs/ENVIRONMENT_SETUP_GUIDE.md` | Added | Version 3 のローカル開発環境構築手順を新設した。本番運用は運用手順書へ分離した | Takashi Oikawa |
| 0.2 | 2026-10-05 | `/docs/DEPLOYMENT_AND_OPERATION_GUIDE.md` | Changed | 関連文書リンクを更新し、ローカル構築を Environment Setup Guide へ誘導した | Takashi Oikawa |
| - | 2026-10-05 | `/docs/archive/` | Changed | Legacy 文書 6 件を `git mv` で移動した。`AI設定仕様書.md` → `DOKOTSUBU2_AI_CONFIGURATION_SPEC.md`、`API仕様書.md` → `DOKOTSUBU2_API_SPEC.md`、`環境構築手順書.md` → `DOKOTSUBU2_ENVIRONMENT_SETUP_GUIDE.md`、`設計書.md` → `DOKOTSUBU2_DESIGN.md`、`DokoTsubu_修正履歴_20260403.md` → `DOKOTSUBU_CHANGE_HISTORY_20260403.md`、`修正検討事項_20260408.md` → `DOKOTSUBU_REVIEW_NOTES_20260408.md`。本文は変更していない | Takashi Oikawa |
| 1.8 | 2026-10-05 | `/docs/design/README.md` | Changed | 設計書一覧の版・状態を更新し、関連仕様・運用文書への導線を追加した。Legacy 文書の所在を `docs/archive/` へ更新した。Screen Overview にメイン画面の thumbnail を 1 枚掲載した | Takashi Oikawa |
| 1.5 | 2026-10-05 | `/docs/design/01_REQUEST_DEFINITION.md` | Changed | データライフサイクル、`USERS` / `MUTTERS` の論理削除、`GENDER` / `AGE_FEELING`、Profile の Gemini 利用を対象へ追加した。§5.1 を Background / Current Problems / Phase 1 Purpose / Scope of This Change / Out of Scope に分けて箇条書き化した | Takashi Oikawa |
| 1.5 | 2026-10-05 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Changed | `GENDER` / `AGE_FEELING` の値一覧、論理削除と有効データ条件、重複 username と再利用禁止、Gemini への Profile 送信を反映した。API / Gemini 仕様書への参照を追加した | Takashi Oikawa |
| 1.9 | 2026-10-05 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | 現行 DB 実体と Target Schema を分離し、`CREATED_AT` / `UPDATED_AT` / `DELETED_AT`、`GENDER` / `AGE_FEELING`、User 論理削除時の Mutter 同時論理削除、NAME 再利用禁止を定義した。password 移行方針を既存 Domain データ初期化・旧 password 移行なしへ変更した。§5 を Current / Target Data Model、Table Definitions、Data Lifecycle、User Profile、Authentication / Authorization、Gemini Data Transfer、Security Design に分離した | Takashi Oikawa |
| 1.3 | 2026-10-05 | `/docs/design/04_UI_AND_FLOW_DESIGN.md` | Changed | 登録画面へ `gender` / `ageFeeling`、初期値、validation、username 重複メッセージを追加した。UI 上の削除は内部で論理削除であることを明記した。SCR-001 / SCR-005 のスクリーンショット（`screenshots/full/`）を掲載した | Takashi Oikawa |
| 1.8 | 2026-10-05 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | §5 を Application / Domain Data / Session / Gemini Integration / Deployment Architecture に分離した。`loginUser` は id / name のみ、Profile は Session へ保存せず Gemini 生成時に DB から取得することを明記した。Domain table 構造の記述を Target Schema へ整合させた | Takashi Oikawa |
| 1.9 | 2026-10-05 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | password 移行方針を既存 Domain データ初期化へ変更した。Migration を独立節とし、前提条件、Domain データ初期化、Schema 変更、password 方針、Session 無効化、サービス再開条件に分けた | Takashi Oikawa |
| - | 2026-10-05 | 現行文書全体 | Changed | Root README → design README → 01〜06 → specs → guides へ辿れるよう、Related Documents を実リンク化し文書間導線を整備した | Takashi Oikawa |
| 1.7 | 2026-10-05 | `/docs/design/README.md` | Changed | 設計書一覧の版を、SEC-01 / SEC-02 対応後の 01〜06 に合わせた | Takashi Oikawa |
| 1.4 | 2026-10-05 | `/docs/design/01_REQUEST_DEFINITION.md` | Security | SC-005 の対象を Repository 全体の現行 Git 管理ファイルであると明確化した | Takashi Oikawa |
| 1.4 | 2026-10-05 | `/docs/design/02_REQUIREMENTS_DEFINITION.md` | Security | FR-008 と API-007 を `POST /DeleteMutter` にした。対象 POST の Session 保存型 CSRF 照合を非機能要件へ追記した | Takashi Oikawa |
| 1.6 | 2026-10-05 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Security | §5.6 に Session 保存型 CSRF を明記した。秘密値の対象を現行 Git 管理ファイル全体とし、部分マスクを残さない方針を追記した | Takashi Oikawa |
| 1.2 | 2026-10-05 | `/docs/design/04_UI_AND_FLOW_DESIGN.md` | Security | 削除を `POST /DeleteMutter` に変更し、投稿・編集・削除フォームの `csrfToken` を追記した | Takashi Oikawa |
| 1.7 | 2026-10-05 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Security | 対象 POST の CSRF 照合を Spring MVC Interceptor と Session 保存型 token で行うことを追記した | Takashi Oikawa |
| 1.7 | 2026-10-05 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Security | `DokoTsubu2` 変更禁止の例外を秘密情報除去のみと明記した。削除 POST と CSRF 照合を実装制約へ追記した | Takashi Oikawa |
| - | 2026-10-05 | `/DokoTsubu2/` `/DokoTsubu3/` `/docs/修正検討事項_20260408.md` `/docs/環境構築手順書.md` | Security | 現行 Git 管理ファイルから DB パスワード実値を除去した。削除を POST のみにし、対象 3 POST へ Session 保存型 CSRF 照合を追加した | Takashi Oikawa |
| 0.3 | 2026-10-04 | `/README.md` | Changed | 公開ログイン URL と Version 3 の現行構成を先に示し、旧版の技術・構成・画面説明を参考として区別 | Takashi Oikawa |
| 0.1 | 2026-10-04 | `/docs/DEPLOYMENT_AND_OPERATION_GUIDE.md` | Changed | Aiven 準備手順を4項目に分割。利用者の本番登録・ログイン成功を記録し、Aiven 側の直接照合と残る機能の未確認を区別。表示上の記号と古い deployment の表記を修正 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Changed | 重複する本番観測結果を削除し、確認範囲の正本を運用手順書へ集約 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Changed | セッション保存欄と引き継ぎから本番観測結果の重複を削除し、運用手順書を参照 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Changed | 重複する本番観測結果を削除し、公開状態・確認範囲の正本を運用手順書へ集約 | Takashi Oikawa |
| 0.1 | 2026-10-04 | `/docs/DEPLOYMENT_AND_OPERATION_GUIDE.md` | Added | Vercel 公開までの手順、Production 設定、再デプロイ、利用操作、実測済み範囲と未確認項目を記録。DB 接続は未確認と明記 | Takashi Oikawa |
| 0.3 | 2026-10-04 | `/README.md` | Fixed | Vercel 未公開との旧記述を現行公開状態へ修正し、運用手順書へ誘導 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/README.md` | Changed | 運用手順書への案内を追加し、レビュー中の設計書の版・状態を一覧へ反映 | Takashi Oikawa |
| 1.5 | 2026-10-04 | `/docs/design/03_DATA_AND_SECURITY_DESIGN.md` | Fixed | Aiven・Vercel の旧未確認記述を本番の確認範囲へ修正。複数インスタンス間 Session 維持は未確認と明記 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/05_ARCHITECTURE_DESIGN.md` | Fixed | Session 実装・公開の状態を実測範囲へ修正。設計方針は維持 | Takashi Oikawa |
| 1.6 | 2026-10-04 | `/docs/design/06_OPERATION_AND_HANDOFF.md` | Fixed | 公開手順の実施結果と本番未確認項目を記録し、詳細運用手順書を参照。DB 接続は未確認と明記 | Takashi Oikawa |
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
