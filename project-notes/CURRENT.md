<!--
Program Name: dokoTsubu-platform CURRENT
Language: Markdown
Function: 現在状態・確定事項・残作業・現在対象・次作業の記録
Created: 2026-10-08
Last Updated: 2026-10-08
Author: Takashi Oikawa
AI: ChatGPT（原稿）/ Cursor（配置）
Memo: 仕様正本を置き換えず、確認結果と引き継ぎ報告を区別する。
-->

# Current Project State（現在状態）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | CURRENT-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-10-08 |
| Last Updated（最終更新日） | 2026-10-08 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [AGENTS.md](../AGENTS.md) / [CONSTITUTION.md](../CONSTITUTION.md) / [README.md](../README.md) / [設計書一覧](../docs/design/README.md) / [CHANGELOG.md](../CHANGELOG.md) |

## 1. Repository（対象）

- Repository：`t-oikawa-sendai/dokoTsubu-platform`
- URL：<https://github.com/t-oikawa-sendai/dokoTsubu-platform>
- Local Workspace：`/Users/takashioikawa/Dev/dokoTsubu-platform`
- Branch：`main`
- 現行アプリ：`DokoTsubu3/`
- 比較基準：`DokoTsubu2/`
- 初版の確認基準コミット：`be40616da5a3332f8e844498d7f8f97c6ba71a4c`。本書追加前の基準であり、以後の最新HEADを表すものではない。

本書は現在状態の記録正本とする。恒久ルールはAGENTS／CONSTITUTION、確定仕様は設計書・機能仕様書を正とする。会話履歴や本書だけで仕様を変更しない。

## 2. Current Status（現在状態）

- 2026-10-07の生徒向け配布準備ガイド新設・README案内追加は上記基準コミットに記録済み。
- 配布準備ガイドはVersion 0.1／Draft。実行用ZIP、配布用WAR、Domain table初期作成SQL、設定サンプルは未作成。配布版の導入・起動は未検証。
- 基準コミットのValidate DocumentsはSUCCESS（Run [37563504119](https://github.com/t-oikawa-sendai/dokoTsubu-platform/actions/runs/37563504119)）。本書追加後の検証結果ではない。
- 前回のLocal HEAD／origin/main一致・作業ツリーcleanはCursorの引き継ぎ報告。現在のローカル状態は着手時にCursorが確認する。
- 2026-10-07に授業用の現行環境を維持するため、アプリ改修を停止した。明示的な再開指示まで停止を維持する。
- 2026-10-08時点の現在対象は本書の導入と再開位置の明確化。アプリコード・DB・公開環境は今回変更しない。
- 公開アプリの適用コミットは未確認。GitHub mainと同一と断定しない。

## 3. Confirmed Decisions（確定事項）

詳細と値定義は[設計書一覧](../docs/design/README.md)および各仕様正本を参照する。

- USERS／MUTTERSは論理削除とし、CREATED_AT／UPDATED_AT／DELETED_ATを持つ。有効条件はDELETED_AT IS NULL。
- USERSにGENDER／AGE_FEELINGを保存し、Geminiコメント生成に利用する。これらは設計確定済み・未実装。
- loginUserにはUser ID／User Nameだけを保存する。プロフィールはGemini生成時にDBから取得する。
- USERS.NAMEのUNIQUEを維持し、論理削除済みの名前も再利用しない。
- Userを論理削除する処理では関連Mutterも同一DBトランザクションで論理削除する。User削除画面・Controller・APIは今回追加しない。
- Schema移行時に既存USERS／MUTTERSのデータを初期化する。既存Userと旧passwordは移行せず、平文／BCryptの互換認証は作らない。
- Spring Security認証基盤、JPA／Hibernate／Spring Data、Thymeleaf、FK追加は採用しない。

## 4. Remaining Work（残作業）

| 対象 | 状態 | 参照正本 |
|---|---|---|
| USERS／MUTTERSの論理削除・日時管理 | 設計確定済み・未実装 | [データ設計](../docs/design/03_DATA_AND_SECURITY_DESIGN.md) |
| GENDER／AGE_FEELINGの登録画面・DB保存 | 設計確定済み・未実装 | [要件定義](../docs/design/02_REQUIREMENTS_DEFINITION.md)・[画面設計](../docs/design/04_UI_AND_FLOW_DESIGN.md) |
| Geminiコメント生成へのプロフィール利用 | 設計確定済み・未実装 | [Gemini仕様](../docs/specs/GEMINI_INTEGRATION_SPEC.md) |
| Target Schemaへの移行・既存データ初期化・既存Session無効化 | 未実施。対象・影響・復旧手段を確認し、実行指示を別途確定する | [移行設計](../docs/design/06_OPERATION_AND_HANDOFF.md) §5.7 |
| 配布用WAR・初期作成SQL・設定サンプル・ZIP・Release公開 | 未作成・未公開。修正完了版が対象 | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 配布版の新規導入・起動・対象機能確認 | 未検証 | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 最新環境構築手順どおりのローカル起動・Login入口 | 未確認。前回8080を他Repositoryのprocessが使用。別Repositoryのprocessは停止しない | [環境構築手順書](../docs/ENVIRONMENT_SETUP_GUIDE.md) |
| 登録→ログイン→投稿→ログアウトの通し操作・3人の独立した同時操作 | 前回引き継ぎに今回の成功確認なし。操作範囲・対象環境を指定して確認する | [運用手順書](../docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) |

公開入口と空欄エラー等の限定確認を、通し操作・同時操作の成功として扱わない。同じブラウザの複数タブはSessionを共有するため、独立した複数人の確認にならない。

## 5. Current Target / Next（現在対象・次作業）

- 現在対象：本書を配置し、READMEから参照できるようにする。実差分レビュー後、明示指示に従ってGitHubへ記録する。
- 次作業：記録整備完了後、確定済みの論理削除・日時管理・登録プロフィール・Gemini利用について、現行実装との差分に基づくCursor指示を作成する。
- アプリ改修の停止解除と実装範囲は利用者の明示指示を記録してから実装する。
- DB移行・初期化・Session無効化・再起動・手動deploy・配布資材作成は、それぞれの作業範囲を確定して進める。

## 6. Maintenance（運用）

- AIはAGENTS.mdのRequired Reading Orderを守り、変更着手前に本書で現在対象・残作業・次作業を確認する。
- 作業の区切りと新チャット移行前に、実物・実差分・確認結果に基づいて状態を更新する。完了／未完了／未確認を区別する。
- 内容変更時はVersionとLast Updatedを更新し、重要な変更はCHANGELOGへ記録する。過去の会話全文や詳細な作業日誌は本書に蓄積しない。
- Cursor指示には毎回Repository名・URL・作業場所・branch・着手時の期待SHAを明記する。
- MILESTONES.mdは補助資料であり、本書や仕様正本の代替として扱わない。
