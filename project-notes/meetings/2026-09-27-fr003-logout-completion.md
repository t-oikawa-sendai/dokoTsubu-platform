<!--
Program Name: 2026-09-27-fr003-logout-completion.md
Language: Markdown
Function: FR-003ログアウト機能の完了Evidenceを残す
Created: 2026-09-27
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 状態はMILESTONES.mdを正とする。passwordとhashは記録しない。Commit SHAはSELF（このマイルストーン記録を含むcommit）。
-->

# FR-003 Logout Completion（ログアウト機能完了記録）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MEET-20260927-003 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-27 |
| Last Updated（最終更新日） | 2026-09-27 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [MILESTONES.md](../MILESTONES.md) / [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) |

---

## Purpose（目的）

FR-003「ログアウト機能」の実装内容、実機確認結果、Evidenceを残す。マイルストーンの状態は [MILESTONES.md](../MILESTONES.md) を正とする。

## Implementation（実装内容）

- `GET /Logout` を `LogoutController` に実装した
- 現在の `HttpSession` がある場合だけ `invalidate()` する。Session が無い場合は新規作成しない
- SCR-007 `logout.jsp` を表示する。表示は DokoTsubu2 の `logout.jsp` を基準にし、見出しは `DokoTsubu Ver.3.0（Springboot Version)` とした
- 「ログアウトしました」を表示する
- TOP リンクは `request.getContextPath()` と `/Login` で組み立て、`/dokoTsubu` はソースに固定していない
- `logout.jsp` は `session="false"` とし、ログアウト画面の表示で新しい Session を作らない
- Service / DAO は追加していない
- CSS は変更していない。既存の SCR-004 用クラスをそのまま使っている

## Runtime Verification（実機確認結果）

2026-09-27 に、ローカルの DokoTsubu3（context path `/dokoTsubu`、port 8080）で確認した。

- FR-002 のログイン成功: PASS。`POST /Login` の応答に「ログインに成功しました」と検証ユーザー名がある
- `GET /dokoTsubu/Logout`: PASS。HTTP 200。応答に「ログアウトしました」がある
- Session 破棄: PASS。ログアウト後、同じ `JSESSIONID` で再度ログインすると新しい `JSESSIONID` が発行される。ログアウトせずに同じ `JSESSIONID` で再度ログインした場合は新しい `JSESSIONID` は発行されない
- ログアウト応答は新しい `JSESSIONID` を発行しない
- TOP リンク: PASS。描画された href は `/dokoTsubu/Login`。その URL はログイン入口（`name` / `pass` と新規登録導線）を返す
- `mvn clean package -DskipTests`: SUCCESS
- `git diff --check`: PASS
- `python3 scripts/validate-docs.py`: PASS

## Evidence（Evidence）

- 検証ユーザー: ID 14 / NAME `fr003verify`。登録は既存の `POST /Register` で作成した
- password と hash は記録しない
- Commit SHA は `SELF（このマイルストーン記録を含むcommit）` である。実際の commit SHA は Git 履歴を Evidence とする
