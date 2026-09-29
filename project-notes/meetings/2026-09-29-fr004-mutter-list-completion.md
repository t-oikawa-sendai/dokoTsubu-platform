<!--
Program Name: 2026-09-29-fr004-mutter-list-completion.md
Language: Markdown
Function: FR-004つぶやき一覧の完了Evidenceを残す
Created: 2026-09-29
Last Updated: 2026-09-29
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 状態はMILESTONES.mdを正とする。passwordとhashは記録しない。Commit SHAはSELF（このマイルストーン記録を含むcommit）。
-->

# FR-004 Mutter List Completion（つぶやき一覧完了記録）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MEET-20260929-004 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-29 |
| Last Updated（最終更新日） | 2026-09-29 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [MILESTONES.md](../MILESTONES.md) / [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) |

---

## Purpose（目的）

FR-004「つぶやき一覧」の実装内容、実機確認結果、Evidenceを残す。マイルストーンの状態は [MILESTONES.md](../MILESTONES.md) を正とする。

## Implementation（実装内容）

- `GET /Main` を `MainController` に実装した
- `MutterService` が `MutterDAO` の一覧を Controller へ返す
- `MutterDAO` は JDBC で `MUTTERS` と `USERS` を結合し、`m.ID DESC` で全件を取得する
- `Mutter` は `id`、`userName`、`text` だけを保持する
- ログイン必須判定は `LoginRequiredInterceptor` と `LoginRequiredWebConfig` に置いた。Interceptor の対象は `/Main` のみである
- 未ログインなら context path 付きの `/Login` へ redirect する。`/dokoTsubu` はソースに固定していない
- `MainController` にはログイン判定を書いていない
- SCR-005 `main.jsp` はログイン中のユーザー名、更新、ログアウト、一覧の `userName` と `text` を表示する
- FR-005 以降の投稿、検索、Gemini、編集、削除は未実装である
- CSS は変更していない。既存クラスの範囲で表示している

## Runtime Verification（実機確認結果）

2026-09-29 に、ローカルの DokoTsubu3（context path `/dokoTsubu`、port 8080）で確認した。

- 未ログインの `GET /Main`: PASS。HTTP 302。Location は `http://127.0.0.1:8080/dokoTsubu/Login`
- ログイン後の `GET /Main`: PASS。HTTP 200。一覧画面を表示する
- DB の 146 件と画面の名前・本文が一致する
- 並びは ID 降順である。先頭 ID は 165、末尾 ID は 1
- ログアウト後の `GET /Main`: PASS。再び `/Login` へ誘導される
- `mvn package`: BUILD SUCCESS
- 今回 commit 対象に対する `git diff --cached --check`: PASS

## Evidence（Evidence）

- 確認用の一時ユーザーは削除済みである
- 既存のつぶやきは削除していない
- password、hash、DB 秘密情報は記録しない
- Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする
