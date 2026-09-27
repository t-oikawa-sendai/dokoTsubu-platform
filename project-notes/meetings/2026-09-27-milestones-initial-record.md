<!--
Program Name: 2026-09-27-milestones-initial-record.md
Language: Markdown
Function: MILESTONES.md初回記録の採否判断を残す
Created: 2026-09-27
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: 状態一覧はMILESTONES.mdを正とする。本ファイルは採否理由だけを記録する。
-->

# Initial Milestone Record（マイルストーン初回記録）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | NOTES-MEET-20260927-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-09-27 |
| Last Updated（最終更新日） | 2026-09-27 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [MILESTONES.md](../MILESTONES.md) / [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) |

---

## Purpose（目的）

[MILESTONES.md](../MILESTONES.md) の初回記録で採用した範囲と、採用しなかった項目の理由を残す。各マイルストーンの状態は `MILESTONES.md` を正とする。

## Evidence（判断根拠）

確認した一次資料は次のとおりである。

- branch `main`。HEAD と `origin/main` は `58be0356945593658d9d8c3ee3419d528871ca63`
- commit `7d6b39fbdcef1dff3144e6fa9a23b6973c114d72`（2026-09-10、`chore: apply repository governance`）
- commit `0041cbd445c94d6669df4cf7ec4629b1e2998a44`（2026-09-12、`chore: complete governance migration`）
- commit `3ce5719cd7e395e1689527f704caf7cc8a1d05b1`（2026-09-13、`build: initialize DokoTsubu3 spring boot`）
- commit `9ac59412499216f4a234995da81c76a354fa5c14`（2026-09-13、`docs: define DokoTsubu3 spring boot target`）
- commit `9fdd50f90ba5f27b9f1e5405c26ecb2a2025c192`（2026-09-13、`feat: add DokoTsubu3 registration entry`）
- commit `58be0356945593658d9d8c3ee3419d528871ca63`（2026-09-13、`feat: implement DokoTsubu3 user registration`）
- commit `8e909e52b4ce136b56964289cf00ec6c1b9aa3e3`（2026-09-13、`feat: add DokoTsubu3 login entry`）。この commit の `LoginController` は `GET /Login` のみ
- 2026-09-27 作業開始時の未commit差分。変更は `LoginController.java`、`UserDAO.java`、`style.css`。未追跡は `LoginUser.java`、`UserCredential.java`、`LoginService.java`、`loginResult.jsp`
- [02_REQUIREMENTS_DEFINITION.md](../../docs/design/02_REQUIREMENTS_DEFINITION.md) の FR-001、FR-002、FR-003
- `DokoTsubu3` 配下の Java / JSP 一覧。ログアウト用のファイルは無い
- GitHub Actions `Validate Documents`。`3ce5719cd7e395e1689527f704caf7cc8a1d05b1` は run `34738758957`、`58be0356945593658d9d8c3ee3419d528871ca63` は run `34740368976`。どちらも success

`project-notes/meetings/` は本記録の作成前には無かった。

## Adopted Names（採用した名称）

作業指示は、進行中の作業を Step 6 と呼んでいた。Repository 内の Markdown と `DokoTsubu3` ソースを検索し、`Step 6` という識別子は見つからなかった。

進行中として確認できた実体は、作業ツリー上の未commitログイン認証差分である。マイルストーン名は、ファイルと FR-002 に合わせて「ログイン認証」とした。状態は `IN_PROGRESS` とし、完了 commit SHA は置かない。

その次の主要作業は、承認済み要件で FR-002 の次に定義されている FR-003 とした。別の日程表はリポジトリに無い。`DokoTsubu3` にログアウト実装ファイルが無いため、状態は `NOT_STARTED` とした。

`COMPLETED` は、該当 commit が `main` にあることを指す。ユーザー登録と Spring Boot 初期化について、実行時の受け入れ結果を記した Git commit、設計書、既存 meetings 記録は無い。

## Governance Completion SHA（Governance完了SHA）

初回適用 commit は `7d6b39fbdcef1dff3144e6fa9a23b6973c114d72`（2026-09-10、`chore: apply repository governance`）である。

Migration完了 commit は `0041cbd445c94d6669df4cf7ec4629b1e2998a44`（2026-09-12、`chore: complete governance migration`）である。この commit は `CHANGELOG.md`、`scripts/validate-docs.py`、`.github/workflows/validate-docs.yml` を追加している。Validate Documents は run `34676716855` で success である。

Repository Governance導入の完了マイルストーンの正本 SHA は `0041cbd445c94d6669df4cf7ec4629b1e2998a44` とする。初回適用の後、Governance Migration の完了がこの commit で確認できるためである。

## Excluded Items（今回登録しなかった項目）

次はマイルストーンとして登録しない。

- Spring Boot主要機能完成
- セキュリティ対応完成
- Vercel対応A
- 別RepositoryでのDokoTsubu4 / Cloud再設計B

## Working Tree（作業ツリー）

本記録の作成では、作業開始時からあった未commit差分を変更しない。消去、stash、restore、reset は行わない。

記録作成後、`README.md` に作業開始時の `git status --short` には無かった 1 行の未commit変更が現れた。追加された文は「職業訓練校での実践訓練のアプリです。」である。本記録の作成では `README.md` を編集していない。この差分にも触れない。
