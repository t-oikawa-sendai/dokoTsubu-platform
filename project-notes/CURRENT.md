<!--
Program Name: dokoTsubu-platform CURRENT
Language: Markdown
Function: 現在状態・確定事項・残作業・現在対象・次作業の記録
Created: 2026-10-08
Last Updated: 2026-10-31
Author: Takashi Oikawa
AI: ChatGPT（原稿）/ Cursor（配置）
Memo: 仕様正本を置き換えず、確認結果と引き継ぎ報告を区別する。
-->

# Current Project State（現在状態）

<details>
<summary>Document Info（文書情報）｜CURRENT-001｜0.8｜Review</summary>

<!-- Document Info（文書情報） -->

| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | CURRENT-001 |
| Version（バージョン） | 0.8 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-10-08 |
| Last Updated（最終更新日） | 2026-10-31 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [AGENTS.md](../AGENTS.md) / [CONSTITUTION.md](../CONSTITUTION.md) / [README.md](../README.md) / [設計書一覧](../docs/design/README.md) / [CHANGELOG.md](../CHANGELOG.md) |

</details>

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

2026-10-08の次の項目は過去の記録である。今回のMac miniの試験結果ではない。

- 2026-10-08に本書を `9ca037c04b4e35ec203c725e8529cb3737f9c5f2` で配置した。そのValidate DocumentsはSUCCESS（Run [37771610011](https://github.com/t-oikawa-sendai/dokoTsubu-platform/actions/runs/37771610011)）。
- 2026-10-08の利用者指示「計画通りの修正作業を開始する」により、アプリ改修の停止を解除した。解除範囲は、論理削除・日時管理、登録プロフィール、Geminiへのプロフィール利用の実装と、独立検証DBでの確認である。この再開指示と、設計担当の実装レビューPASSは過去の記録である。
- 上記3改修と、空の環境専用の `DokoTsubu3/db/create-student-db.sql` は設計担当による実差分レビューPASS。GitHubへのpushと本番反映は、その時点では未実施だった。
- テスト専用ディレクトリの新規MySQL（port 3307、適用前に `dokotsubu` は未作成）へ初期作成SQLと既存Session SQLを適用し、WARをport 18081で起動して確認した。既存のport 3306のMySQLは変更していない。45件の試験は、この独立環境での過去結果であり、すべてPASSと記録されている。
- 独立検証DBで確認した範囲は、登録画面の初期値、4項目登録、内部コード・BCrypt hash・日時、欠落と許可外の拒否、有効Userと論理削除済みUserの同一重複メッセージ、削除済みUserのログイン拒否、削除済みMutter／削除済みUserの投稿が一覧・検索に出ないこと、本人編集が作成日時を保持すること、本人削除が行を残して削除日時と更新日時を設定すること、他人の変更拒否、DBの表示名だけを含む生成リクエスト、実API 1件の成功、Profile取得不能でも投稿が残ること、3つの独立cookieでの通し操作である。3人の同時操作は未確認である。
- 運用手順書に残る確認済み公開デプロイはcommit `1a1c0f1`。2026-10-08にGitHubの `9ca037c04b4e35ec203c725e8529cb3737f9c5f2` のcommit statusでVercelのデプロイ完了（success）を確認した（[deployment](https://vercel.com/t-oikawa-sendai/doko-tsubu-platform/GGaNLe3x9zV5641Xv6UDRh6xjzE8)）。Production指定、現在の公開アプリの適用SHA、次のpushによる自動反映設定は、その時点では未確認だった。

2026-10-12にMac mini（`Mac-mini.local`）で確認した現在状態は次のとおりである。

- 開始HEAD、fetch後の `origin/main`、GitHub `main` は `9ca037c04b4e35ec203c725e8529cb3737f9c5f2` で一致し、ahead/behindは0、作業ツリーはcleanだった。
- レビュー提出時点で、レビュー済み17ファイルを作業ツリーへ復元済み、未commit・未pushだった。これは承認済みファイル内容の復元であり、`cbfa174ea17b60cad5e2114837f49e767f5d7ab6` というGit commitの復元ではない。そのSHAはこのcloneでは取得できていない。
- 今回のMac miniの確認は、`DokoTsubu3` の `mvn -B package` がBUILD SUCCESS（2026-10-12 21:44:35 +0900）であること、`python3 scripts/validate-docs.py` がPASS（終了値 0）であること、`git diff --check` が終了値 0 であることである。提出時点の差分は復元した17ファイルに限られる。既存DBと本番を使う機能試験は実行していない。45件の成功は、今回の試験結果にしない。
- 本番DBのTarget Schema移行、既存データ初期化、Session無効化、手動deploy、配布用ZIP、設定サンプル、Releaseは今回実施していない。
- 修正版の移行・公開実行案はRepository外の `/Users/takashioikawa/BakaUpArea/dokoTsubu-platform/dokotsubu-migration-deployment-plan-20261012.md` にある。Aivenの構造・制約・件数、公開aliasの適用SHA、Production Branch、自動反映、停止・再開の操作は未確認である。
- 配布準備ガイドはDraft。実行用ZIP、配布用WAR、設定サンプルは未作成。配布版の導入・起動は未検証。

2026-10-12の終了時レビューと記録範囲は次のとおりである。

- 設計担当は `dokotsubu-review-20261012.zip` 本体を受領した。SHA-256は `b706a56be9ac29f1e73657bbd7b9899317766762d1c8f333bc48dfefb7e16b72`。基準 `9ca037c` にtracked 15ファイルと新規2ファイルの差分を適用し、17ファイルのSHA-256が提出一覧と一致することを確認した。CURRENT／CHANGELOG以外の15ファイルは2026-10-08のレビュー済み実物とバイト単位で一致し、コードのレビューPASSを維持する。
- 修正版実行案の実物レビューは要修正。12個のbashコードブロックは構文検査PASSだが、§5の禁止文検査を構造用dumpにも適用するため、正常な `CREATE TABLE` が拒否される。架空の構造用dumpで終了値1を確認した。運用DBへ接続した試験ではない。
- 次回は上記のデータ用／構造用検査の分離、Domain復元前のInnoDB確認を実コマンドで行うこと、接続・EVENT一覧の閲覧範囲とdump中の書込み停止の保証を確認する。実行案の修正完了・実行承認として扱わない。
- 本日のGitHub記録は本書とCHANGELOGの2ファイルだけを対象とする。文書2件のcommit後は、残る15ファイル（追跡13・未追跡2）をMac miniの未commit作業ツリーに保全する。アプリコード、README、仕様書、ガイド、初期作成SQLは今回GitHubへ反映しない。文書記録のcommitを新アプリの公開予定SHAとして扱わない。
- Aiven ConsoleとVercel設定画面はCursorの確認ではログイン画面だった。本番DBの構造・制約・件数、公開aliasの適用SHA、Production Branch、自動反映、停止・再開操作、Mac miniの既存ローカルDBは未確認である。既存の利用可能なログインセッション・接続情報で読み取り確認する。秘密値をチャットや文書へ記録しない。

2026-10-13の終了時点は次のとおりである。以下は本番実行の承認ではない。

- 設計担当によるRepository外の修正版実行案の実物レビューはPASS。最終ZIPのSHA-256、実差分、変更bashブロックの構文、提出されたSQL受信結果を確認済み。既存アプリレビューPASSを維持し、レビューを再実行していない。
- 設計担当は保全15ファイルの実SHA-256が既存レビューZIPの保全一覧と一致することを直接確認した。着手時HEADとローカル `origin/main` は `ac11b671ce6bc2247bb45add023dcf6ad9f570ab`、branchは `main`、ahead/behindは0、stageは空。GitHubの最新SHAは今回再取得していない。Cursorの利用者提供報告も追跡13・未追跡2、保全一覧一致であり、同報告は更新時刻確認まででハッシュ再計算は行っていない。既存ハッシュ記録は `/Users/takashioikawa/BakaUpArea/dokoTsubu-platform/stop-confirm-stdin-fix-20261013/dokotsubu-plan-stop-confirm-stdin-20261013/git/preservation-sha256.txt`（2026-10-13 15:12:50 +0900）。
- 既存の利用者画面証拠ではVercelのProduction Currentは `ac11b67`、Production Branchは `main`、mainの自動反映とPause Projectを確認済み。Aiven service `mysql-dokotsubu` とdatabase `dokotsubu` の存在も既存証拠を再利用する。今回の新規実測として扱わない。
- Cursorの利用者提供報告（2026-10-13）では、Projectの環境変数の表示先はProductionのみ、Preview／Development／Pre-productionのフィルタ結果は0件。値は未読取、Sharedタブは未読取。これは現在の割当であり、他の接続元の不存在や移行中の再接続防止を保証しない。
- 同報告では、Vercel公式文書の「Resuming a project」で正式名称 `Resume Project`、Settings → GeneralのPause Project節またはバナーからの導線、Production deploymentの提供再開という適用範囲を確認した。停止中の実ボタン表示と再開動作は未確認。証拠は <https://vercel.com/docs/projects/managing-projects#pausing-a-project>（文書最終更新2026-08-11、確認2026-10-13）。
- 本番4テーブル `USERS`／`MUTTERS`／`SPRING_SESSION`／`SPRING_SESSION_ATTRIBUTES` の列・制約・ENGINEと、全接続・EVENT・transaction閲覧に必要な権限は未読取。`SHOW GRANTS`、`PROCESS`、EVENT一覧、`@@GLOBAL.event_scheduler`、`information_schema.INNODB_TRX`の確認も未了。過去のローカル実測とAiven DDLスパイクPASSで代替しない。件数は移行実行時に測定する。
- 同報告ではAiven Consoleはログイン画面。Mac miniに `.local-secrets/mysql.cnf`／`.local-secrets/aiven-dokotsubu.env`、MySQL login path、`AIVEN_TOKEN`はなく、`mysql`クライアントはある。新しいtokenは発行していない。既存のAivenログインまたは既存接続ファイルが次作業の前提である。秘密値は文書へ記録しない。
- Production以外のdeployment、GUI、スクリプト、Spring Session期限切れ清掃、MySQL EVENTの書込みを移行中に停止維持する方法は不足している。Pause Projectによる配信停止だけではDB書込み停止成立としない。実行案§4.3の停止維持フラグは未成立のまま扱う。
- 本番開始条件は不足。本番DB移行、初期化、Session無効化、停止・再開・公開は未実施。アプリ等15ファイルは未commitのまま保全する。本日のGitHub記録対象はCURRENTとCHANGELOGの2ファイルに限り、その文書commitを新アプリの公開承認commitにしない。

2026-10-15のMac mini実測は次のとおりである。着手時HEADは `4fd7e68de9903f6577925dd184e92b8843b13df6`、branchは `main` である。Gemini生成成功確認とAiven・本番対応は、この記録をもって保留のままとする。

- Cursor実測：127.0.0.1:3306に存在していなかった `dokotsubu` を、空環境用SQLとSession用SQLで新規作成した。Target Schemaは一致した。既存データの削除・上書きはない。
- ローカル8080で起動し、登録プロフィール保存、ログイン、投稿保存、本人投稿の論理削除、ログアウトを確認した。
- 既存のMavenビルドは成功し、`git diff --check` は成功した。設計担当はログイン画面、`.gitignore` 差分、秘密設定の配置・権限・Git除外、保全15ファイルのハッシュ一致を直接確認した。
- アプリコードの追加変更はない。`.gitignore` には `.local-ops/` の除外とコードヘッダを追加済みで、未commitである。
- Geminiはキー未入手のため、利用者指示で生成成功確認を保留する。Aiven・本番対応も保留し、追加調査・問い合わせは進めない。
- Aiven接続設定とCAは作成済みだが1045で拒否された。本番構造・権限は未読取である。Vercelの秘密値は取得できず、Macとの一致は未確認である。
- 保全15ファイルと `.gitignore` 差分を維持する。commit／pushは未実施である。

## 3. Confirmed Decisions（確定事項）

詳細と値定義は[設計書一覧](../docs/design/README.md)および各仕様正本を参照する。

- USERS／MUTTERSは論理削除とし、CREATED_AT／UPDATED_AT／DELETED_ATを持つ。有効条件はDELETED_AT IS NULL。
- USERSにGENDER／AGE_FEELINGを保存し、Geminiコメント生成に利用する。設計は確定済み。コードへ実装し、独立検証DBで確認した。本番DBと公開環境には未適用。
- loginUserにはUser ID／User Nameだけを保存する。プロフィールはGemini生成時にDBから取得する。
- USERS.NAMEのUNIQUEを維持し、論理削除済みの名前も再利用しない。
- Userを論理削除する処理では関連Mutterも同一DBトランザクションで論理削除する。User削除画面・Controller・APIは今回追加しない。
- Schema移行時に既存USERS／MUTTERSのデータを初期化する。既存Userと旧passwordは移行せず、平文／BCryptの互換認証は作らない。
- Spring Security認証基盤、JPA／Hibernate／Spring Data、Thymeleaf、FK追加は採用しない。

## 4. Remaining Work（残作業）

| 対象 | 状態 | 参照正本 |
|---|---|---|
| USERS／MUTTERSの論理削除・日時管理 | コード実装済み。2026-10-08の独立検証DBで確認済み。2026-10-15のMac mini新規ローカル `dokotsubu`（127.0.0.1:3306）とローカル8080で、本人投稿の論理削除を確認した。本番未適用 | [データ設計](../docs/design/03_DATA_AND_SECURITY_DESIGN.md) |
| GENDER／AGE_FEELINGの登録画面・DB保存 | コード実装済み。2026-10-08の独立検証DBで確認済み。2026-10-15のローカル8080で登録プロフィール保存を確認した。本番未適用 | [要件定義](../docs/design/02_REQUIREMENTS_DEFINITION.md)・[画面設計](../docs/design/04_UI_AND_FLOW_DESIGN.md) |
| Geminiコメント生成へのプロフィール利用 | コード実装済み。2026-10-08の独立検証DBで送信構成と実API 1件を確認済み。2026-10-15のローカル8080での生成成功確認は、キー未入手のため利用者指示で保留する。追加調査・再試行は進めない。本番未適用 | [Gemini仕様](../docs/specs/GEMINI_INTEGRATION_SPEC.md) |
| Target Schemaへの移行・既存データ初期化・既存Session無効化 | 本番対応は保留。Aiven接続設定とCAは作成済みだが1045で拒否された。本番構造・権限は未読取。Vercelの秘密値は取得できず、Macとの一致は未確認。追加調査・問い合わせは進めない。本番工程は開始しない | [移行設計](../docs/design/06_OPERATION_AND_HANDOFF.md) §5.7 |
| 配布用WAR・初期作成SQL・設定サンプル・ZIP・Release公開 | 初期作成SQLはMac miniの作業ツリーへ復元済み、未追跡・未commit・未push。空の環境専用。配布用WAR・設定サンプル・ZIP・Releaseは未作成・未公開 | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 配布版の新規導入・起動・対象機能確認 | 未検証 | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 最新環境構築手順どおりのローカル起動・Login入口 | 2026-10-08は独立検証環境のport 18081で確認済み。2026-10-15に既定port 8080で、存在していなかった `dokotsubu` を空環境用SQLとSession用SQLにより新規作成して起動を確認した。Target Schemaは一致。既存データの削除・上書きはない | [環境構築手順書](../docs/ENVIRONMENT_SETUP_GUIDE.md) |
| 登録→ログイン→投稿→ログアウトの通し操作・3人の独立した同時操作 | 2026-10-08の独立検証DBでは通し操作と3つの独立Sessionを確認済み。3人の同時操作は未確認。2026-10-15のローカル8080で、登録プロフィール保存、ログイン、投稿保存、本人投稿の論理削除、ログアウトを確認した。本番は未確認。Gemini生成成功確認は保留 | [運用手順書](../docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) |

公開入口と空欄エラー等の限定確認を、通し操作・同時操作の成功として扱わない。同じブラウザの複数タブはSessionを共有するため、独立した複数人の確認にならない。

## 5. Current Target / Next（現在対象・次作業）

- 2026-10-31の利用者承認により、授業基礎版をVersion 1.5とし、本Repositoryの `DokoTsubu1_5/` へ配置する。`回答.zip` の外側を採用し、内側の重複プロジェクトと `target/` は収録しない。
- ZIPのSHA-256は `5b24d7e8d9c6fb4e6eba9b94c1b1642dd83dceda34f7948e061872cc6bb115eb`。ソース・設定・テスト・Maven Wrapperは原本のまま保持する。Eclipse設定とHELP.mdも展開時は保持するが、原本の.gitignoreに従いGit登録対象から除外する。
- 作業用cloneは `/workspace/scratch/d62bd46d69ba/dokoTsubu-platform`。開始時はmain、HEADとorigin/mainは `a309661e80c0b08db7be488a1e2efa3726589246` で一致、未commit変更なし。Mac側の作業ツリーは未確認であり、過去の保全差分には触れていない。
- 既存V1・V2・V3、DB、デプロイ構成は変更しない。Version 3の設計をVersion 1.5へ転用しない。
- Version 1の表記は「JSP・サーブレットを使用する授業基本版（旧版）」、Version 1.5の表記は「Spring Bootを使用する授業基本版（新版）」とする。
- 配置とREADME整合化を実施した。利用者から今回の変更のcommit・push指示を受領した。GitHub反映結果はGit履歴で確認する。公開アプリのデプロイは今回の対象外である。Java 25が必要だが作業環境はJava 17のため、ビルド・起動・DB・画面試験は未実施。残作業はJava 25環境での動作確認とMac側への同期である。

### 2026-10-15時点の保留事項（継続）

- 現在対象：2026-10-15のローカル動作結果を本書とCHANGELOGへ記録し、Gemini生成成功確認とAiven・本番対応を保留として固定する。
- 文書記録後もcommit／pushは行わない。保全15ファイルと `.gitignore` 差分は維持し、作業ツリーをcleanにするために破棄しない。
- 次作業としてGemini生成成功確認、Aiven調査、本番構造・権限の読取、Vercel秘密値の取得、それらの問い合わせを進めない。再開は利用者の明示指示があるときだけとする。
- 公開承認commitの確定、アプリのcommit／push、DB移行・停止・再開・公開は別途承認後の工程とする。今回の文書記録を新アプリの公開対象として扱わない。
- 配布資材作成は未実施であり、今回の文書記録には含めない。

## 6. Maintenance（運用）

- AIはAGENTS.mdのRequired Reading Orderを守り、変更着手前に本書で現在対象・残作業・次作業を確認する。
- 作業の区切りと新チャット移行前に、実物・実差分・確認結果に基づいて状態を更新する。完了／未完了／未確認を区別する。
- 内容変更時はVersionとLast Updatedを更新し、重要な変更はCHANGELOGへ記録する。過去の会話全文や詳細な作業日誌は本書に蓄積しない。
- Cursor指示には毎回Repository名・URL・作業場所・branch・着手時の期待SHAを明記する。
- MILESTONES.mdは補助資料であり、本書や仕様正本の代替として扱わない。
