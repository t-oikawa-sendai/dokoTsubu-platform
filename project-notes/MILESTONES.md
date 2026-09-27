<!--
Program Name: MILESTONES.md
Language: Markdown
Function: DokoTsubu3開発の主要マイルストーン状態を記録する
Created: 2026-09-27
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 初回記録。Git commit、実ファイル、既存設計書、GitHub Actionsの確認結果だけを記載する。
-->

# Milestones（マイルストーン）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MILESTONES-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-27 |
| Last Updated（最終更新日） | 2026-09-27 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [02_REQUIREMENTS_DEFINITION.md](../docs/design/02_REQUIREMENTS_DEFINITION.md) / [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) |

---

## Purpose（目的）

`dokoTsubu-platform` における DokoTsubu3 開発の主要マイルストーンの状態を記録する。

採否の判断経緯は [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) を正とする。

## Status Values（状態）

使用する状態は次の 5 つだけである。

- `NOT_STARTED`
- `IN_PROGRESS`
- `BLOCKED`
- `COMPLETED`
- `CANCELLED`

## Recorded Milestones（記録済みマイルストーン）

### Repository Governance導入

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | Repository Governance導入 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-12 |
| Commit SHA（関連commit SHA） | `0041cbd445c94d6669df4cf7ec4629b1e2998a44` |
| Completion Criteria（完了条件） | この commit が `CHANGELOG.md`、`scripts/validate-docs.py`、`.github/workflows/validate-docs.yml` を追加している |
| Detail Record（詳細記録） | [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) |
| GitHub Actions（GitHub Actions結果） | Validate Documents success。run `34676716855`。検査対象は文書ヘッダーである |

### DokoTsubu3 Spring Boot化開始

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | DokoTsubu3 Spring Boot化開始 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-13 |
| Commit SHA（関連commit SHA） | `3ce5719cd7e395e1689527f704caf7cc8a1d05b1` |
| Completion Criteria（完了条件） | `DokoTsubu3/pom.xml` が Spring Boot 4.1.1、Java 21、`war` を定義し、`DokoTsubuApplication` と `application.properties` が追加されている |
| Detail Record（詳細記録） | なし |
| GitHub Actions（GitHub Actions結果） | Validate Documents success。run `34738758957`。検査対象は文書ヘッダーである |

先行する設計 commit は `9ac59412499216f4a234995da81c76a354fa5c14`（2026-09-13、`docs: define DokoTsubu3 spring boot target`）である。Spring Boot アプリケーションの初期ファイルを追加した commit は `3ce5719cd7e395e1689527f704caf7cc8a1d05b1` である。

### ユーザー登録機能

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | ユーザー登録機能 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-13 |
| Commit SHA（関連commit SHA） | `58be0356945593658d9d8c3ee3419d528871ca63` |
| Completion Criteria（完了条件） | `GET /Register` が登録画面を返し、`POST /Register` が `username` / `password` を受ける。未入力・重複・その他 DB エラーは登録画面へ戻り、成功時は登録完了画面を返す。password は BCrypt hash として `USERS` へ INSERT する |
| Detail Record（詳細記録） | [02_REQUIREMENTS_DEFINITION.md](../docs/design/02_REQUIREMENTS_DEFINITION.md) の FR-001 |
| GitHub Actions（GitHub Actions結果） | Validate Documents success。run `34740368976`。検査対象は文書ヘッダーである |

登録画面の追加 commit は `9fdd50f90ba5f27b9f1e5405c26ecb2a2025c192`（2026-09-13）である。登録処理の実装 commit は `58be0356945593658d9d8c3ee3419d528871ca63` である。

### ログイン認証

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | ログイン認証 |
| Status（状態） | `IN_PROGRESS` |
| Current State（現在状態） | 2026-09-27 の作業ツリーに未commitのログイン認証差分がある。commit SHA は未確定 |
| Commit SHA（関連commit SHA） | 未確定。ログイン入口の既存 commit は `8e909e52b4ce136b56964289cf00ec6c1b9aa3e3` |
| Completion Criteria（完了条件） | `GET /Login` はログイン入口へ戻す。`POST /Login` のパラメーターは `name` / `pass`。成功時はセッションへ `loginUser`（user id と user name のみ）を保存し、ログイン結果画面を表示する |
| Detail Record（詳細記録） | [02_REQUIREMENTS_DEFINITION.md](../docs/design/02_REQUIREMENTS_DEFINITION.md) の FR-002 / [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

`8e909e52b4ce136b56964289cf00ec6c1b9aa3e3` の `LoginController` は `GET /Login` のみである。2026-09-27 時点の未commit差分は `LoginController.java`、`UserDAO.java`、`style.css` の変更と、`LoginUser.java`、`UserCredential.java`、`LoginService.java`、`loginResult.jsp` の追加である。

### ログアウト機能

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | ログアウト機能 |
| Status（状態） | `NOT_STARTED` |
| Current State（現在状態） | `DokoTsubu3` にログアウト実装ファイルは無い |
| Commit SHA（関連commit SHA） | なし |
| Completion Criteria（完了条件） | `GET /Logout` でセッションを破棄し、ログアウト画面を表示する |
| Detail Record（詳細記録） | [02_REQUIREMENTS_DEFINITION.md](../docs/design/02_REQUIREMENTS_DEFINITION.md) の FR-003 / [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) |
| GitHub Actions（GitHub Actions結果） | 実装 commit が無いため、対象 Workflow 実行は無い |
