# F/O/R Classification Ledger（仕様分類台帳）

<details>
<summary>Document Info（文書情報）｜FOR-LEDGER-001｜0.3｜Review</summary>

| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | FOR-LEDGER-001 |
| Version（バージョン） | 0.3 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-10-31 |
| Last Updated（最終更新日） | 2026-10-31 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [設計書一覧](./README.md) / [AGENTS.md](../../AGENTS.md) / [CONSTITUTION.md](../../CONSTITUTION.md) |

</details>

## 1. Purpose and Scope（目的・対象範囲）

本台帳は、UML-01〜05 の置換候補に対応する既存設計の分類と所在を示す。対象は [03](./03_DATA_AND_SECURITY_DESIGN.md) §5.4 の状態図・User 削除範囲・NAME UNIQUE、[04](./04_UI_AND_FLOW_DESIGN.md) §5.2、[05](./05_ARCHITECTURE_DESIGN.md) §5.1・§5.5、[06](./06_OPERATION_AND_HANDOFF.md) §5.7 に限る。設計本文の正本は各文書であり、本台帳は仕様本文を複製しない。UMLへの置換や仕様内容の変更を承認する文書ではない。

`F-xxx` は確定仕様、`O-xxx` は未決事項、`R-xxx` は却下仕様の分類番号である。既存の `FR-xxx`、`SC-xxx`、`HO-xxx` などの番号は変更しない。設計書の「対象外」や検証未了を、未決事項または却下仕様へ読み替えない。

## 2. Confirmed Specification Ledger（確定仕様台帳）

各行の仕様内容は「正本箇所」にある本文を正とする。「対象」は検索・照合用の見出しであり、本文の言い換えではない。

| ID | 対象 | 正本箇所 | 状態 |
|---|---|---|---|
| F-001 | 作成・更新・論理削除の日時管理 | [03 §5.4 状態図](./03_DATA_AND_SECURITY_DESIGN.md) | FROZEN |
| F-002 | USERS・MUTTERS の論理削除と有効判定 | [03 §5.4 状態図](./03_DATA_AND_SECURITY_DESIGN.md) | FROZEN |
| F-003 | User 論理削除時の関連 Mutter の整合性 | [03 §5.4 状態図](./03_DATA_AND_SECURITY_DESIGN.md) | FROZEN |
| F-004 | User 削除の画面・Controller・API・操作導線を作らない範囲 | [03 §5.4](./03_DATA_AND_SECURITY_DESIGN.md) | FROZEN |
| F-005 | 論理削除済み User を含む NAME の一意性 | [03 §5.4 NAME UNIQUE](./03_DATA_AND_SECURITY_DESIGN.md) | FROZEN |
| F-006 | 登録・ログイン結果・ログアウトの画面遷移 | [04 §5.2 画面遷移図](./04_UI_AND_FLOW_DESIGN.md) | FROZEN |
| F-007 | GET /Login の入口遷移 | [04 §5.2 画面遷移図](./04_UI_AND_FLOW_DESIGN.md) | FROZEN |
| F-008 | 更新失敗時の編集画面復帰 | [04 §5.2 画面遷移図](./04_UI_AND_FLOW_DESIGN.md) | FROZEN |
| F-009 | 投稿成功時の aiMsg 表示 | [04 §5.2 画面遷移図](./04_UI_AND_FLOW_DESIGN.md) | FROZEN |
| F-010 | ログイン必須の入口と未ログイン時の誘導 | [04 §5.2 画面遷移図の注記](./04_UI_AND_FLOW_DESIGN.md) | FROZEN |
| F-011 | Browser・Vercel・Application・Aiven MySQL の構成 | [05 §5.1 システム構成図](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-012 | Controller・Service・DAO の構成 | [05 §5.1 システム構成図](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-013 | 投稿処理から Gemini API までの構成 | [05 §5.1 システム構成図](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-014 | 投稿成功後の Gemini 同期呼び出し | [05 §5.5 Gemini連携](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-015 | Gemini model と明示しない sampling parameter | [05 §5.5 Gemini連携](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-016 | 有効 User のプロフィールを DB から取得すること | [05 §5.5 Gemini連携](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-017 | Gemini 失敗時に投稿を rollback しないこと | [05 §5.5 Gemini連携](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-018 | Gemini API key の外部設定 | [05 §5.5 Gemini連携](./05_ARCHITECTURE_DESIGN.md) | FROZEN |
| F-019 | Application 停止中の移行 | [06 §5.7 活動図](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-020 | 既存 Domain データの初期化と既存 User の非移行 | [06 §5.7 活動図](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-021 | Target Schema と許可された変更範囲 | [06 §5.7.1 Schema 変更](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-022 | 旧 password の非移行と新規 User の BCrypt 方針 | [06 §5.7.2 password 方針](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-023 | サービス再開前の既存 Session 無効化 | [06 §5.7 活動図](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-024 | サービス再開条件 | [06 §5.7 活動図](./06_OPERATION_AND_HANDOFF.md) | FROZEN |
| F-025 | Application 停止後・Domain 初期化前の最終バックアップ取得、取得結果と復元手順の確認 | [06 §5.7 活動図](./06_OPERATION_AND_HANDOFF.md) | FROZEN |

## 3. Open Issue Ledger（未決事項台帳）

今回の対象範囲に、`O-xxx` として分類された事項はない。レビュー指摘や検証未了を、新たな未決仕様として登録しない。

## 4. Rejected Specification Ledger（却下仕様台帳）

今回の対象範囲に、`R-xxx` として分類された事項はない。対象外事項を却下仕様として登録しない。

## 5. Change Control（変更時の照合）

UML置換前に、対象の F-ID と正本箇所を対応付ける。置換後は各 F-ID の意味が本文に残り、未決事項や却下仕様へ移動していないことを照合する。本台帳は並行する DB 操作の安全性や本番での実行結果を証明しない。
