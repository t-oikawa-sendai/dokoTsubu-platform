<!--
Program Name: 2026-09-29-fr005-fr009-mutter-post-gemini-completion.md
Language: Markdown
Function: FR-005つぶやき投稿とFR-009 Geminiコメント生成の完了Evidenceを残す
Created: 2026-09-29
Last Updated: 2026-09-29
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 状態はMILESTONES.mdを正とする。API key実値、password、hashは記録しない。Commit SHAはSELF（このマイルストーン記録を含むcommit）。
-->

# FR-005 / FR-009 Mutter Post and Gemini Completion（つぶやき投稿とGeminiコメント生成の完了記録）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MEET-20260929-005 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-29 |
| Last Updated（最終更新日） | 2026-09-29 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [MILESTONES.md](../MILESTONES.md) / [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) |

---

## Purpose（目的）

FR-005「つぶやき投稿」と FR-009「Geminiコメント生成」の実装内容と実機確認 Evidence を残す。マイルストーンの状態は [MILESTONES.md](../MILESTONES.md) を正とする。

## Implementation（実装内容）

- `POST /Main` を実装した
- `MutterDAO` が `MUTTERS` へ INSERT する
- DB 保存が成功した後だけ、Gemini API を同期呼び出しする
- 呼び出しは `GeminiClient` が行う
- model は `gemini-3.5-flash-lite` である
- API key は `x-goog-api-key` header で送る
- `temperature` / `top_p` / `top_k` は指定しない
- Gemini が失敗しても投稿は残す
- `aiMsg` と `errorMsg` を表示する
- 動的表示値は HTML エスケープする
- API key は環境変数 `DOKOTSUBU_GEMINI_API_KEY` から読む
- `.local-secrets` は Git 管理外である

## Runtime Verification（実機確認結果）

2026-09-29 に確認した。

- 未ログイン `POST /Main`: PASS。HTTP 302。遷移先は `/dokoTsubu/Login`
- 空文字投稿: PASS。HTTP 200。「つぶやきが入力されていません」を表示する。`MUTTERS` 件数は 146 のままである
- 正常投稿: PASS。検証時の `MUTTERS` ID は 166、`USER_ID` は 17。送信した TEXT が保存され、一覧の先頭に表示された。`aiMsg` を表示し、その内容は既定失敗文ではない
- Gemini 失敗: PASS。検証時の `MUTTERS` ID は 168。API key を空にした状態でも投稿は保存された。既定失敗文を表示し、rollback はしていない
- HTML エスケープ: PASS。検証時の `MUTTERS` ID は 167。`<b>notbold</b>` は HTML タグとして解釈されない
- build: `mvn -DskipTests package` は BUILD SUCCESS
- diff check: PASS

## Cleanup Evidence（検証データの削除）

今回作成した検証データだけを削除済みである。

- 削除対象の `MUTTERS` は 166、167、168
- 削除対象の `USERS` は 17、18
- 削除後は `USERS` 8 件、`MUTTERS` 146 件、最大 USER ID 14、最大 MUTTER ID 165
- 削除後は作成前の状態と一致する

## Security Evidence（秘密情報）

- API key の実値は記録しない
- DB password などの秘密値も記録しない
- API key はログへ出していない
- `.local-secrets` は Git 管理外である

## Commit Evidence（Commit SHA）

- Commit SHA は `SELF（この完了記録を含むcommit）` である
- 実際の commit SHA は Git 履歴を Evidence とする
