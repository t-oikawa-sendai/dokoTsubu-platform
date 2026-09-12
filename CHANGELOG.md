<!--
Program Name: dokoTsubu-platform CHANGELOG
Language: Markdown
Function: dokoTsubu-platformの変更履歴正本
Created: 2026-09-11
Last Updated: 2026-09-12
Author: Takashi Oikawa
AI: Cursor Grok 4.6
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
| Last Updated（最終更新日） | 2026-09-12 |
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
| 0.1 | 2026-09-11 | `/CHANGELOG.md` | Added | D-013: Repository固有履歴正本を新設 | Takashi Oikawa |
| 1.3 | 2026-09-11 | `/CONSTITUTION.md` | Changed | D-013: 中央Canonical Source STANDARD_VERSION 1.3 へ同期 | Takashi Oikawa |
| 1.2 | 2026-09-11 | `/AGENTS.md` | Changed | D-013: 中央Canonical Source `AGENTS_SOURCE.md` STANDARD_VERSION 1.2 へ同期 | Takashi Oikawa |
| 0.4 | 2026-09-11 | `/docs/design/README.md` | Fixed | D-013: CHANGELOG相対パスを `../../../CHANGELOG.md` から `../../CHANGELOG.md` へ修正 | Takashi Oikawa |
