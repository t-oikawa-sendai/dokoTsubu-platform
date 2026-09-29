<!--
Program Name: MILESTONES.md
Language: Markdown
Function: DokoTsubu3開発の主要マイルストーン状態を記録する
Created: 2026-09-27
Last Updated: 2026-09-29
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: FR-005つぶやき投稿とFR-009 Geminiコメント生成の完了Evidenceを追記。Commit SHAはSELF（このマイルストーン記録を含むcommit）。
-->

# Milestones（マイルストーン）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MILESTONES-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-27 |
| Last Updated（最終更新日） | 2026-09-29 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [02_REQUIREMENTS_DEFINITION.md](../docs/design/02_REQUIREMENTS_DEFINITION.md) / [2026-09-27-milestones-initial-record.md](./meetings/2026-09-27-milestones-initial-record.md) / [2026-09-27-fr002-login-completion.md](./meetings/2026-09-27-fr002-login-completion.md) / [2026-09-27-fr003-logout-completion.md](./meetings/2026-09-27-fr003-logout-completion.md) / [2026-09-29-fr004-mutter-list-completion.md](./meetings/2026-09-29-fr004-mutter-list-completion.md) / [2026-09-29-fr005-fr009-mutter-post-gemini-completion.md](./meetings/2026-09-29-fr005-fr009-mutter-post-gemini-completion.md) |

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
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-27 |
| Commit SHA（関連commit SHA） | `SELF（このマイルストーン記録を含むcommit）` |
| Completion Criteria（完了条件） | 正常ログインPASS。同一Sessionで未入力時に失敗表示。同一Sessionで誤password時に失敗表示。build SUCCESS。`git diff --check` PASS |
| Detail Record（詳細記録） | [2026-09-27-fr002-login-completion.md](./meetings/2026-09-27-fr002-login-completion.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

完了Evidenceは [2026-09-27-fr002-login-completion.md](./meetings/2026-09-27-fr002-login-completion.md) に残す。Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。

### ログアウト機能

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | ログアウト機能 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-27 |
| Commit SHA（関連commit SHA） | `SELF（このマイルストーン記録を含むcommit）` |
| Completion Criteria（完了条件） | `GET /Logout` で現在の Session を破棄し、ログアウト画面に「ログアウトしました」を表示する。TOP リンクからログイン入口へ戻れる。build SUCCESS。`git diff --check` PASS |
| Detail Record（詳細記録） | [2026-09-27-fr003-logout-completion.md](./meetings/2026-09-27-fr003-logout-completion.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

完了Evidenceは [2026-09-27-fr003-logout-completion.md](./meetings/2026-09-27-fr003-logout-completion.md) に残す。Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。

### つぶやき一覧

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | つぶやき一覧 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-29 |
| Commit SHA（関連commit SHA） | `SELF（このマイルストーン記録を含むcommit）` |
| Completion Criteria（完了条件） | `GET /Main` を実装した。未ログイン時は `/Login` へ誘導する。ログイン済みで一覧画面を表示する。`MUTTERS` 全件を ID 降順で表示する。構成は Controller → Service → DAO(JDBC)。build SUCCESS。実機確認で 146 件を表示し、先頭 ID は 165、末尾 ID は 1。staged 対象の `git diff --cached --check` は PASS |
| Detail Record（詳細記録） | [2026-09-29-fr004-mutter-list-completion.md](./meetings/2026-09-29-fr004-mutter-list-completion.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

完了Evidenceは [2026-09-29-fr004-mutter-list-completion.md](./meetings/2026-09-29-fr004-mutter-list-completion.md) に残す。Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。

### つぶやき投稿

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | つぶやき投稿 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-29 |
| Commit SHA（関連commit SHA） | `SELF（このマイルストーン記録を含むcommit）` |
| Completion Criteria（完了条件） | `POST /Main` を実装した。空文字は投稿しない。ログインユーザー ID と text を `MUTTERS` へ INSERT する。INSERT 成功後に一覧を再取得する。未ログイン POST は `/Login` へ誘導する。build SUCCESS。実機確認 PASS |
| Detail Record（詳細記録） | [2026-09-29-fr005-fr009-mutter-post-gemini-completion.md](./meetings/2026-09-29-fr005-fr009-mutter-post-gemini-completion.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

完了Evidenceは [2026-09-29-fr005-fr009-mutter-post-gemini-completion.md](./meetings/2026-09-29-fr005-fr009-mutter-post-gemini-completion.md) に残す。Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。

### Geminiコメント生成

| Item（項目） | Value（値） |
|---|---|
| Milestone（マイルストーン名） | Geminiコメント生成 |
| Status（状態） | `COMPLETED` |
| Completed Date（完了日） | 2026-09-29 |
| Commit SHA（関連commit SHA） | `SELF（このマイルストーン記録を含むcommit）` |
| Completion Criteria（完了条件） | 投稿成功後だけ Gemini API を同期呼び出しする。model は `gemini-3.5-flash-lite`。`x-goog-api-key` header を使う。`temperature` / `top_p` / `top_k` は送信しない。成功時は `aiMsg` を表示する。Gemini 失敗時も投稿を rollback しない。失敗文を `aiMsg` へ表示する。HTML エスケープを確認した。build SUCCESS。実機確認 PASS |
| Detail Record（詳細記録） | [2026-09-29-fr005-fr009-mutter-post-gemini-completion.md](./meetings/2026-09-29-fr005-fr009-mutter-post-gemini-completion.md) |
| GitHub Actions（GitHub Actions結果） | 未commitのため、この作業を対象にした Workflow 実行は無い |

完了Evidenceは [2026-09-29-fr005-fr009-mutter-post-gemini-completion.md](./meetings/2026-09-29-fr005-fr009-mutter-post-gemini-completion.md) に残す。Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。
