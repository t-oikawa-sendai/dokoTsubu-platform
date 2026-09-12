# Request Definition（要求定義）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | REQ-001 |
| Version（バージョン） | 1.0 |
| Status（ステータス） | Approved |
| Created Date（作成日） | 2026-06-21 |
| Last Updated（最終更新日） | 2026-09-12 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | README.md / 02_REQUIREMENTS_DEFINITION.md / 06_OPERATION_AND_HANDOFF.md |

---

## Table of Contents（目次）

1. [Purpose（目的）](#1-purpose目的)
2. [Scope（対象範囲）](#2-scope対象範囲)
3. [Out of Scope（対象外範囲）](#3-out-of-scope対象外範囲)
4. [Assumptions（前提条件）](#4-assumptions前提条件)
5. [Definition Details（定義内容）](#5-definition-details定義内容)
6. [Open Issues（未決事項）](#6-open-issues未決事項)
7. [Handoff to Detail Design（詳細設計への引き継ぎ）](#7-handoff-to-detail-design詳細設計への引き継ぎ)

---

## 1. Purpose（目的）

本文書は、現行 `DokoTsubu2` を Spring Boot へ移行する Phase 1 の背景・対象・成功条件を定義し、要件定義および設計の前提とする。

---

## 2. Scope（対象範囲）

Phase 1 は次を対象とする。

- 現行アプリ正本 `DokoTsubu2/` の機能維持
- Spring Boot / Spring MVC / JSP / JDBC / MySQL への移行
- Gemini API 連携の維持
- 認証・認可・password・秘密情報配置を本文書群の確定設計へ合わせること
- 後続の外部公開へ接続できる構成（PF としての土台）

---

## 3. Out of Scope（対象外範囲）

Phase 1 では次を対象外とする。

- Spring Security による認証基盤（FilterChain 等）
- JPA / Hibernate / Spring Data
- Thymeleaf
- 不要な新機能および不要な抽象化
- Docker / Cloud Run
- 外部 Tomcat 必須構成
- Legacy 文書（`docs/設計書.md` 等）の改訂
- 現行ライブ MySQL 実体の推測確定

---

## 4. Assumptions（前提条件）

- 機能の基準は現行 `DokoTsubu2` 実装事実とする
- Legacy 文書と実装の不整合は、Phase 1 正本では実装事実と確定設計を優先する
- ローカルで MySQL および Gemini API を利用できる
- 現行ライブ MySQL の実テーブル名は UNVERIFIED である

---

## 5. Definition Details（定義内容）

### 5.1 Background and Purpose（背景・課題・目的）

現行 `DokoTsubu2` は Eclipse Dynamic Web Project と Jakarta Servlet で動作するつぶやき共有アプリである。Servlet / Eclipse 依存、秘密情報のソース直書き、固定絶対パスの AI 設定、編集・削除の認可欠落が、後続の PF 化と外部公開の障害になる。

Phase 1 の目的は、現行機能を維持したまま Spring Boot 構成へ移し、秘密値を Git / source から排除し、他人の投稿を操作できない状態にすることである。

### 5.2 Stakeholders（ステークホルダー一覧と関心事）

| Stakeholder（ステークホルダー） | Interests and Requests（関心事・要求） |
|---|---|
| 利用者（投稿者） | 登録・ログイン・投稿・検索・自分の投稿の編集削除、Gemini 一言の表示 |
| 開発者 / 実装担当 | `DokoTsubu2` を基準に、確定設計だけを実装する |
| 設計担当 | Phase 1 正本の維持。Legacy 不整合を正本へ持ち込まない |
| 運用担当 | 秘密値を Git に置かない。ローカル起動設定を外部化する |

### 5.3 User Stories and Use Cases（ユーザーストーリー・ユースケース概要）

| ID | User Story / Use Case（ユーザーストーリー / ユースケース） |
|---|---|
| US-001 | 利用者としてユーザー名とパスワードで登録したい |
| US-002 | 登録済み利用者としてログインしたい |
| US-003 | ログイン中利用者としてログアウトしたい |
| US-004 | ログイン中利用者として全つぶやきを新着順で見たい |
| US-005 | ログイン中利用者としてつぶやきを投稿したい |
| US-006 | ログイン中利用者としてキーワードでつぶやきを検索したい |
| US-007 | ログイン中利用者として自分のつぶやきを編集したい |
| US-008 | ログイン中利用者として自分のつぶやきを削除したい |
| US-009 | ログイン中利用者として投稿成功後に Gemini 一言を見たい |

### 5.4 Constraints（制約条件）

| Type（種別） | Constraint（制約内容） |
|---|---|
| Budget（予算） | 本文書では対象外。理由: 未指定 |
| Deadline（期限） | 本文書では対象外。理由: 未指定 |
| Technical（技術） | Spring Boot / Spring MVC / JSP / JDBC / MySQL。WAR + embedded Tomcat。Gemini API 維持。Spring Security 認証基盤・JPA・Thymeleaf は導入しない |
| Regulatory（法規） | 秘密値を Git / source に置かない。password を平文保存しない |

### 5.5 Success Criteria and Acceptance Conditions（成功基準・受け入れ条件）

| ID | Success Criteria / Acceptance Condition（成功基準・受け入れ条件） |
|---|---|
| SC-001 | 既存機能（登録・ログイン・ログアウト・一覧・投稿・検索・編集・削除・Gemini コメント）が維持される |
| SC-002 | Spring Boot で起動できる |
| SC-003 | MySQL へ接続できる |
| SC-004 | Gemini 連携が維持される（投稿成功後の同期呼び出し。失敗しても投稿は残る） |
| SC-005 | 秘密値が Git / source に存在しない |
| SC-006 | 他人の投稿を編集・削除できない |

---

## 6. Open Issues（未決事項）

本文書では Open Issue を保持しない。ライブ MySQL の実テーブル名確認は [03_DATA_AND_SECURITY_DESIGN.md](./03_DATA_AND_SECURITY_DESIGN.md) と [06_OPERATION_AND_HANDOFF.md](./06_OPERATION_AND_HANDOFF.md) を参照する。

---

## 7. Handoff to Detail Design（詳細設計への引き継ぎ）

実装制約と確認事項は [06_OPERATION_AND_HANDOFF.md](./06_OPERATION_AND_HANDOFF.md) を正とする。機能要件は [02_REQUIREMENTS_DEFINITION.md](./02_REQUIREMENTS_DEFINITION.md) を正とする。
