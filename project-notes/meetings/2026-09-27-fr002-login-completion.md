<!--
Program Name: 2026-09-27-fr002-login-completion.md
Language: Markdown
Function: FR-002ログイン認証の完了Evidenceを残す
Created: 2026-09-27
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 状態はMILESTONES.mdを正とする。passwordとhashは記録しない。Commit SHAはSELF（このマイルストーン記録を含むcommit）。
-->

# FR-002 Login Completion（ログイン認証完了記録）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MEET-20260927-002 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-27 |
| Last Updated（最終更新日） | 2026-09-27 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [MILESTONES.md](../MILESTONES.md) / [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) |

---

## Purpose（目的）

FR-002「ログイン認証」の実装完了Evidenceを残す。マイルストーンの状態は [MILESTONES.md](../MILESTONES.md) を正とする。

## Completion Evidence（完了Evidence）

2026-09-27 に確認した完了条件は次のとおりである。

- 正常ログイン PASS
- 同一 Session で未入力時に失敗表示
- 同一 Session で誤 password 時に失敗表示
- build SUCCESS
- `git diff --check` PASS

Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする。

## Review and Runtime Record（レビューと実機確認）

- 当初のコードレビューは PASS
- 成功系確認用の既知 password がなく、成功系は一度 BLOCKED
- FR-001 のユーザー登録機能から検証ユーザーを作成し、成功系を確認した
- 同一 Session で未入力時に成功表示となる不具合を検出した
- 原因: SCR-004 が既存 Session の `loginUser` 存在で成否判定していた
- 修正: request scope の今回認証結果 `loginSuccess` で表示判定する
- 修正後の 3 ケースは PASS
- 検証ユーザー: ID 13 / NAME `fr002verify`
- password と hash は記録しない

認証成功時は Session へ `loginUser` を保存する。認証失敗時の既存 Session 削除と invalidate は行っていない。
