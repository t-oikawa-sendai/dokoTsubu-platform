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
<summary>Document Info（文書情報）｜CURRENT-001｜1.3｜Review</summary>

<!-- Document Info（文書情報） -->

| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | CURRENT-001 |
| Version（バージョン） | 1.3 |
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
- Default Branch：`main`（今回の作業ブランチは §2 を参照）
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

2026-10-31のVersion 3本番作業の現在状態は次のとおりである。上記の過去の保留は、利用者が既存DB移行と本番公開までを明示的に依頼したため、今回の作業範囲には適用しない。

- 着手時の本clone `main`、`origin/main`、GitHub `main` は `a8d2ee0d927a58b57a36221fa1cb5871f2a44ce9` で一致し、作業ツリーはcleanだった。既存のVersion 3実装は保全ブランチ `preserve/pre-sync-main-cbfa174` にあり、現行mainとは分岐している。
- 作業ブランチ `codex/version3-production` に、保全コミット `cbfa174ea17b60cad5e2114837f49e767f5d7ab6` からVersion 3配下の9ファイルだけを取り込んだ。`DokoTsubu1_5/` とVersion 3以外の差分は取り込まない。`mvn -q -DskipTests package` と `git diff --check` は成功。今回の機能試験は未実施であり、2026-10-08の45件を今回の結果とはしない。
- Vercel Productionは `main` の `a8d2ee0` を `Ready` として公開中。Project Rootは `DokoTsubu3`、Framework PresetはContainer。DB・Geminiの環境変数はProductionだけにあり、Shared変数は未連結。Pause Projectの操作は表示されているが、停止は未実行。
- Aiven `mysql-dokotsubu` は稼働中。`dokotsubu` に読み取り接続し、`USERS` 6件、`MUTTERS` 27件、Spring Session 2テーブルは0件を確認。4テーブルはInnoDB。Domain 2テーブルは旧3列構造、追加予定列なし、FK・trigger・eventなし。`PROCESS`／`EVENT`／`ALTER`等の権限を確認した。event schedulerはON。確認時のDB接続表示は自分の照会だけで、将来の書込み停止を保証しない。
- Git管理外の事前バックアップに加え、Vercel ProductionをPause Projectで停止し、公開入口の503 `DEPLOYMENT_PAUSED`、他のDBクライアントなし、Domain eventなし、実行中transactionなし、件数6／27／0／0を確認した。停止後の最終バックアップは `.local-secrets/backups/dokotsubu-final-pre-migration-2026-10-31.sql`（6575 bytes、権限600、SHA-256 `70de30bc9499bc7e8a34c8658f93e2c303546e9b21eafc17f2a78afe1c1819b3`）。4テーブルの定義・データとdump完了印を確認した。復元操作自体は未実施。Aiven自動バックアップの最新Fullは2026-10-30 14:36:12 UTC。Freeプランのfork不可を踏まえ、手元dumpを復旧手段とする。
- 利用者は既存User 6件・Mutter 27件の初期化を明示承認した。停止中に `MUTTERS`、`USERS`、旧Spring Sessionの順でデータを初期化し、`USERS`／`MUTTERS` にTarget Schemaの列を追加した。移行後は4テーブルとも0件。追加列の型・NULL条件とInnoDBを確認した。table rename／schema rename／TEXT長変更／FK追加は行っていない。
- Version 3の対象14ファイルをcommit `27120a39154b18bed7a8f6efa793b157b6881acb` としてGitHub `main`へ反映した。Vercel停止中の自動ビルドはBlockedとなったため、利用者承認を受けて再開し、本人確認後に同commitをProductionへ再デプロイした。新デプロイは2026-10-31 19:53 JSTにReadyとなり、公開ドメインへ割り当てられた。
- 公開入口 `/dokoTsubu/Login` にログイン画面、`/dokoTsubu/Register` に性別・年齢感覚を含む登録画面が表示されることを確認した。登録・ログイン・投稿・論理削除・Gemini生成の本番操作試験は実施していない。今回のAiven接続確認時にパスワードが操作ログへ表示されたため、利用者がAivenのDB利用者 `avnadmin` のパスワードを再設定し、Vercel Productionの `DOKOTSUBU_DB_PASSWORD` とGit管理外のローカル秘密設定を更新した。新しいローカル設定によるAivenへの読み取り接続は成功。Vercelは同じsource commit `0408c49` を新環境変数で再デプロイし、2026-10-31 20:24:15 JSTにReady、公開ドメインへ割当済み。再デプロイ後のログイン画面表示も確認した。秘密値は本書へ記録しない。
- 2026-10-31、利用者から「動作確認OK」と報告があった。個別の確認項目は報告されていない。今回の読み取り確認では、公開ログイン画面と登録画面がHTTP 200で応答し、本番DBに有効User 4件と有効Mutter 14件が存在した。確認時点の有効User 4件すべてに `GENDER` / `AGE_FEELING` が保存されていた。件数は変動する。Gemini生成、本人以外の編集・削除拒否、論理削除の本番操作は今回個別に確認していない。

## 3. Confirmed Decisions（確定事項）

詳細と値定義は[設計書一覧](../docs/design/README.md)および各仕様正本を参照する。

- USERS／MUTTERSは論理削除とし、CREATED_AT／UPDATED_AT／DELETED_ATを持つ。有効条件はDELETED_AT IS NULL。
- USERSにGENDER／AGE_FEELINGを保存し、Geminiコメント生成に利用する。設計は確定済み。コードとDB列を本番へ適用済み。独立検証DBでは確認済み。本番DBでプロフィール列の保存を読み取り確認したが、Geminiへの利用は今回個別に確認していない。
- loginUserにはUser ID／User Nameだけを保存する。プロフィールはGemini生成時にDBから取得する。
- USERS.NAMEのUNIQUEを維持し、論理削除済みの名前も再利用しない。
- Userを論理削除する処理では関連Mutterも同一DBトランザクションで論理削除する。User削除画面・Controller・APIは今回追加しない。
- Schema移行時に既存USERS／MUTTERSのデータを初期化する。既存Userと旧passwordは移行せず、平文／BCryptの互換認証は作らない。
- Spring Security認証基盤、JPA／Hibernate／Spring Data、Thymeleaf、FK追加は採用しない。

## 4. Remaining Work（残作業）

| 対象 | 状態 | 参照正本 |
|---|---|---|
| USERS／MUTTERSの論理削除・日時管理 | コードとTarget Schemaを本番へ適用。2026-10-08の独立検証DBと2026-10-15のローカルで確認済み。本番の論理削除操作は今回個別に確認していない | [データ設計](../docs/design/03_DATA_AND_SECURITY_DESIGN.md) |
| GENDER／AGE_FEELINGの登録画面・DB保存 | コードとTarget Schemaを本番へ適用。公開登録画面の項目表示と、本番DBの有効User 4件すべてに両項目が保存されていることを確認 | [要件定義](../docs/design/02_REQUIREMENTS_DEFINITION.md)・[画面設計](../docs/design/04_UI_AND_FLOW_DESIGN.md) |
| Geminiコメント生成へのプロフィール利用 | コードを本番へ適用。2026-10-08の独立検証DBで送信構成と実API 1件を確認済み。今回の本番生成とプロフィール利用は個別に確認していない | [Gemini仕様](../docs/specs/GEMINI_INTEGRATION_SPEC.md) |
| Target Schemaへの移行・既存データ初期化・既存Session無効化 | 2026-10-31に停止後の最終バックアップを保存し、利用者承認の上で初期化・Schema変更・Session無効化を実施。移行後4テーブル0件、追加列を確認済み | [移行設計](../docs/design/06_OPERATION_AND_HANDOFF.md) §5.7 |
| 配布用WAR・初期作成SQL・設定サンプル・ZIP・Release公開 | 空の環境専用の初期作成SQLはVersion 3のcommit `27120a3` でGitHub mainへ反映済み。配布用WAR・設定サンプル・ZIP・Releaseは未作成・未公開。今回の本番DB移行には初期作成SQLを使用していない | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 配布版の新規導入・起動・対象機能確認 | 未検証 | [配布準備ガイド](../docs/STUDENT_DISTRIBUTION_GUIDE.md) |
| 最新環境構築手順どおりのローカル起動・Login入口 | 2026-10-08は独立検証環境のport 18081で確認済み。2026-10-15に既定port 8080で、存在していなかった `dokotsubu` を空環境用SQLとSession用SQLにより新規作成して起動を確認した。Target Schemaは一致。既存データの削除・上書きはない | [環境構築手順書](../docs/ENVIRONMENT_SETUP_GUIDE.md) |
| 登録→ログイン→投稿→ログアウトの通し操作・3人の独立した同時操作 | 2026-10-08の独立検証DBでは通し操作と3つの独立Sessionを確認済み。2026-10-15のローカル8080で登録・ログイン・投稿・本人論理削除・ログアウトを確認した。利用者から本番の「動作確認OK」報告あり。個別の確認項目、3人の同時操作、Gemini生成成功は今回未確認 | [運用手順書](../docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) |

公開入口と空欄エラー等の限定確認を、通し操作・同時操作の成功として扱わない。同じブラウザの複数タブはSessionを共有するため、独立した複数人の確認にならない。

## 5. Current Target / Next（現在対象・次作業）

### 2026-10-31 Version 3の現在対象

- 保全ブランチのVersion 3機能だけを現行mainへ取り込み、既存Aiven DBを設計済みTarget Schemaへ移行してVercel本番へ公開する。Version 1.5は授業用サンプルソースであり、本作業の対象外。
- Version 3のDB移行・本番公開・接続パスワードの再設定と反映は実施済み。利用者から「動作確認OK」と報告があった。公開画面の応答と本番DBの登録・投稿データ、プロフィール保存を読み取り確認した。個別の操作試験範囲は未特定である。

### 2026-10-31 Version 1.5の配置記録（過去）

- 2026-10-31の利用者承認により、Spring Bootの授業用サンプルソースをVersion 1.5とし、本Repositoryの `DokoTsubu1_5/` へ配置した。`回答.zip` の外側を採用し、内側の重複プロジェクトと `target/` は収録しない。
- ZIPのSHA-256は `5b24d7e8d9c6fb4e6eba9b94c1b1642dd83dceda34f7948e061872cc6bb115eb`。ソース・設定・テスト・Maven Wrapperは原本のまま保持する。Eclipse設定とHELP.mdも展開時は保持するが、原本の.gitignoreに従いGit登録対象から除外する。
- 配置時の作業用cloneは `/workspace/scratch/d62bd46d69ba/dokoTsubu-platform`。開始時はmain、HEADとorigin/mainは `a309661e80c0b08db7be488a1e2efa3726589246` で一致し、未commit変更はなかった。当時未確認だったMac側の本cloneは、2026-10-31に早送り同期し、HEADとorigin/mainが `f7ceff8347e136d025c910953febb300be4f899c` で一致した。同期直後の未commit差分・未追跡ファイルはなかった。
- 既存V1・V2・V3、DB、デプロイ構成は変更しない。Version 3の設計をVersion 1.5へ転用しない。
- Version 1の表記は「JSP・サーブレットを使用する授業基本版（旧版）」、Version 1.5の表記は「Spring Bootを使用する授業用サンプルソース（動作確認対象外）」とする。
- Version 1.5の原本配置はGitHubへ反映済みで、2026-10-31に本cloneへ同期した。Version 1.5のビルド・起動・DB・画面試験は未実施で、利用者指示により動作確認は行わない。当時のVersion 1.5配置作業では、公開アプリのデプロイは対象外だった。

### 2026-10-15時点の保留事項（過去の記録）

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
