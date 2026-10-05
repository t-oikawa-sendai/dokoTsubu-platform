# Deployment and Operation Guide（デプロイ・運用手順書）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | DEPLOY-001 |
| Version（バージョン） | 0.2 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2026-10-04 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../README.md) / [Design Documents Index（設計書一覧）](./design/README.md) / [Architecture Design（アーキテクチャ設計）](./design/05_ARCHITECTURE_DESIGN.md) / [Operation and Handoff Design（運用・詳細設計引き継ぎ）](./design/06_OPERATION_AND_HANDOFF.md) / [Environment Setup Guide（環境構築手順書）](./ENVIRONMENT_SETUP_GUIDE.md) / [Gemini Integration Specification（Gemini連携仕様書）](./specs/GEMINI_INTEGRATION_SPEC.md) / [CHANGELOG.md](../CHANGELOG.md) |

---

## 1. Purpose and Scope（目的と対象）

本書は Version 3（`DokoTsubu3`）を GitHub `main` から Vercel に公開し、Aiven MySQL と Gemini API を設定・運用するための手順である。設計上の要件と画面遷移の正本は [設計書一覧](./design/README.md) 以下の7文書とし、本書は実際の設定・操作・確認記録を扱う。ローカル開発環境の構築は [Environment Setup Guide（環境構築手順書）](./ENVIRONMENT_SETUP_GUIDE.md) を正とする。

**本番公開は完了している。** 2026-10-04 に Vercel のデプロイが `Ready` となり、ログイン画面と存在しないユーザーのログイン失敗画面を確認した。同日、利用者は本番 URL で新規登録し、同じアカウントでログインしてメイン画面まで表示されたと報告した。登録・認証で使用する DB 書き込み・読み出しの経路は、この操作結果とコードから動作したと判断できる。Aiven 側での接続先・登録行の直接照合、投稿、Gemini 応答、検索、編集、削除、ログアウト、複数インスタンス間のセッション維持は未確認である。

## 2. Current Production Configuration（現在の本番構成）

| Item（項目） | Setting / Evidence（設定・根拠） |
|---|---|
| Source Repository（ソース） | `t-oikawa-sendai/dokoTsubu-platform`、Production Branch `main` |
| Vercel Team / Project（チーム・プロジェクト） | `t-oikawa-sendai` / `doko-tsubu-platform` |
| Root Directory（ルートディレクトリ） | `DokoTsubu3` |
| Build / Runtime（ビルド・実行） | Container、`DokoTsubu3/Dockerfile.vercel`、Java 21、実行可能 WAR、`PORT=8080` |
| Context Path（アプリのパス） | `/dokoTsubu`。`application.properties` で設定 |
| Production Database（本番DB） | Aiven MySQL を接続先に設定。schema `dokotsubu`。登録・ログイン成功の利用者報告あり。Aiven 側での直接照合は未実施 |
| Session Store（セッション保存先） | Spring Session JDBC。同じ Aiven MySQL を使う設定。起動時のテーブル自動作成は無効。複数インスタンス間の維持は未確認 |
| Gemini（生成AI） | `DOKOTSUBU_GEMINI_API_KEY` を外部設定。model は `gemini-3.5-flash-lite` |
| Last Checked Deployment（確認済みデプロイ） | [Vercel deployment](https://vercel.com/t-oikawa-sendai/doko-tsubu-platform/6K8tg4cz1H7GDiMCrEzdmX5iDN61)、`Ready`、commit `1a1c0f1` |
| Login URL（ログイン画面） | <https://doko-tsubu-platform.vercel.app/dokoTsubu/Login> |

設計上の構成は、GitHub `main` → Vercel Container → Spring Boot WAR → Aiven MySQL である。投稿保存後の Gemini 呼び出しはアプリ内から行う設計である。Vercel のドメイン直下 `/` はアプリの入口ではなく、確認時は 404 だった。利用者には上記の `/dokoTsubu/Login` まで含む URL を案内する。

## 3. Before Deployment（デプロイ前の確認）

1. 対象リポジトリ、`main` の commit、変更有無を確認し、公開対象を確定する。未コミットのローカル変更は GitHub からの Vercel デプロイに含まれない。
2. `DokoTsubu3/Dockerfile.vercel` と `DokoTsubu3/src/main/resources/application.properties` が対象 commit に含まれることを確認する。Dockerfile は Maven で WAR を作り、Vercel の `PORT` で起動する。
3. Aiven MySQL の接続先と必要なテーブルを確認する。

   1. **接続情報:** Aiven Console で対象の MySQL service を開き、Service Overview の host、port、database、username を照合する。秘密値は文書や画面共有に残さない。
   2. **初回の database:** service と `dokotsubu` database がない場合だけ作成する。既存の本番環境では作り直さない。
   3. **テーブル:** 対象の `dokotsubu` に `USERS`、`MUTTERS`、`SPRING_SESSION`、`SPRING_SESSION_ATTRIBUTES` があるか確認する。
   4. **不足時の DDL:** 対象 DB、既存データ、影響、復旧手段を確認してから、不足するテーブルにだけ承認済み DDL を一度適用する。Domain table 用 DDL は本リポジトリにないため構造を推測して作らない。Session 用 DDL は `DokoTsubu3/db/spring-session-mysql.sql` を参照する。既存テーブルには再適用しない。アプリ起動時の自動作成は無効である。
4. DB の JDBC URL、ユーザー名、パスワード、Gemini API キーの入手元を確認する。値を本書、ソース、Git、チャット、スクリーンショット、ログへ記載しない。ローカルの秘密情報はリポジトリ内 `.local-secrets/` に置き、Git 管理外にする。
5. ローカルでビルドを確認する場合は `DokoTsubu3` で `mvn -B package` を実行する。DB 接続・Gemini 呼び出しの成功はビルド成功だけでは証明できない。Vercel の Dockerfile 自体は `mvn -B -DskipTests package` を実行する。
6. ローカル起動が必要なら、対象のローカル MySQL に同じ schema と Session テーブルがあることを確認し、秘密値を表示せずに4つの `DOKOTSUBU_*` 変数を実行環境へ渡す。`DokoTsubu3` で `mvn spring-boot:run` を実行し、`http://localhost:8080/dokoTsubu/Login` を開く。ローカル用の接続値は Git 管理外の `.local-secrets/` に保管する。詳細は [Environment Setup Guide（環境構築手順書）](./ENVIRONMENT_SETUP_GUIDE.md) を参照。

## 4. First Deployment on Vercel（Vercel への初回公開）

この節は同一構成で再作成する際の手順である。すでに本番プロジェクトがある場合は重複プロジェクトを作らず、第5節を使う。

1. Vercel で `Add New` → `Project` を開き、GitHub の `t-oikawa-sendai/dokoTsubu-platform` を選択する。接続先 Team は `t-oikawa-sendai`、Project Name は `doko-tsubu-platform` とする。
2. Production Branch が `main` であることを確認し、Root Directory に `DokoTsubu3` を指定する。Framework / Application Preset は `Container` を選ぶ。ビルド対象は `DokoTsubu3/Dockerfile.vercel` であり、リポジトリ直下や `DokoTsubu2` ではない。
3. Production の Environment Variables に次の5項目を登録する。DB 接続値は Aiven の対象 service、Gemini キーは有効な発行元の値を使用する。秘密値の表示を抑える設定（Sensitive）が選べる欄では有効にする。

   | Name（名前） | Value（入力する内容） | Meaning（用途） |
   |---|---|---|
   | `PORT` | `8080` | Container の待ち受けポート |
   | `DOKOTSUBU_DB_URL` | Aiven MySQL の JDBC URL | DB と Spring Session JDBC の接続先 |
   | `DOKOTSUBU_DB_USERNAME` | 対象 DB ユーザー名 | DB 認証 |
   | `DOKOTSUBU_DB_PASSWORD` | 対象 DB パスワード | DB 認証 |
   | `DOKOTSUBU_GEMINI_API_KEY` | 有効な Gemini API キー | 投稿後の Gemini 呼び出し |

   `.env` やシェルの `export DOKOTSUBU_DB_URL='...'` を参照する場合、Vercel の **Name は左辺の変数名、Value は引用符内の実値だけ**を入力する。`export`、`=`、外側の `'` は Value に含めない。DB URL は `jdbc:mysql://...` から始まる JDBC 形式を用い、接続方式と SSL 設定は Aiven の接続情報・使用ドライバに合わせて確認する。ここに実値の例は掲載しない。
4. 設定名、Production 適用先、Root Directory を見直して `Deploy` を実行する。ビルド失敗時は Vercel の Build Logs の該当箇所を確認し、設定や実装を修正して新たにデプロイする。ログに秘密値を貼り付けない。
5. Deployment の status が `Ready` となり、対象 commit と production domain が正しいことを確認する。`Ready` は起動・公開の状態であり、業務機能すべての合格判定ではない。

## 5. Update, Redeploy, and Rollback（更新・再デプロイ・戻し方）

### 5.1 Code Update（コード更新）

承認した変更を `main` に反映した後、Vercel の Deployments で新しい Production deployment の commit、Build Logs、status を確認する。`Ready` 後、第6節の公開 URL を開き、変更箇所に直接対応する最小の実機確認を行う。設定を触らずに古い deployment の状態だけで合格としない。

### 5.2 Environment Variable Update（環境変数の変更）

Vercel Project → `Settings` → `Environment Variables` で対象名と `Production` を選び、値を更新する。値の変更は既存の deployment に自動反映されない。設定保存後に **新しい Production deployment** を作成し、`Ready` と公開 URL を確認する。キーのローテーションでは新キーでの動作確認と旧キーの利用範囲を確認してから旧キーを失効させる。旧キーの削除申告があっても、キー実値を画面で照合できない場合は「一致は未確認」と記録する。

### 5.3 Failure and Recovery（障害時と復旧）

失敗した deployment の Build Logs / Runtime Logs を確認し、直前の commit または変更した設定に原因を絞る。Vercel 側で直前の正常な deployment に戻す場合は、戻す対象の commit、適用される環境変数、DB schema との互換性を確認してから実施する。DB の削除や再作成、Session テーブルの再作成、秘密値のログ出力を復旧手順に含めない。

## 6. Application Operation and Acceptance（画面操作と受け入れ）

### 6.1 Confirmed Production Results（本番で確認済み）

| Action（操作） | Observed Result（観測結果） | Judgment（判定範囲） |
|---|---|---|
| 2026-10-04 に確認した Production deployment を開く | commit `1a1c0f1` が `Ready` | 当該デプロイの公開を確認 |
| <https://doko-tsubu-platform.vercel.app/dokoTsubu/Login> を開く | ログイン画面が表示 | HTTP 配信とログイン画面の表示 |
| 存在しないユーザーでログインする | 「ログインに失敗しました」と表示 | ログイン失敗画面の表示まで。DB 接続失敗時も同じ画面になる実装のため、DB 接続の確認にはならない |
| 本番 URL で新規登録し、同じアカウントでログインする | メイン画面まで表示（2026-10-04、利用者の実機確認） | `USERS` への登録とログイン照合が動作。Aiven 側の直接照合は未実施 |
| ドメイン直下 `/` を開く | 404 | context path のため想定される入口外の結果 |

### 6.2 Designed User Flow（設計上の利用手順・未確認項目を含む）

次は [UI and Flow Design（UI・フロー設計）](./design/04_UI_AND_FLOW_DESIGN.md) に基づく操作方法である。登録・ログイン・メイン画面表示は第6.1節の利用者確認の範囲で実施済みである。ほかの機能は本番実測の記録がない。実運用前の確認では、実データと権限に配慮し、成功画面と失敗時の画面をそれぞれ確認する。

1. **登録:** ログイン画面から「登録」を開き、ユーザー名とパスワードを入力して登録する。成功時は登録結果、重複などの失敗時は入力画面のメッセージを確認する。
2. **ログイン:** 登録済みの認証情報をログイン画面へ入力する。成功後に `Main` の一覧へ進めること、誤った認証情報では失敗メッセージが出ることを確認する。
3. **一覧・投稿・Gemini:** `Main` でつぶやきを投稿する。保存後に一覧へ表示され、Gemini の一言が出る設計である。Gemini 呼び出しが失敗しても保存済み投稿は取り消さず、画面に失敗文を出す設計である。投稿の保存と Gemini 応答は別々に判定する。
4. **検索:** `Main` の検索欄にキーワードを入れ、対象つぶやきが絞り込まれることを確認する。
5. **編集・削除:** 自分の投稿だけに編集・削除の操作が表示される。編集後の内容、削除後の一覧を確認する。他人の投稿は画面から操作できず、サーバー側でも拒否される設計である。
6. **ログアウト:** ログアウト後はログイン状態が解除され、`Main` などの保護された画面へ直接進めないことを確認する。

本番の受け入れ判定は [Request Definition（要求定義）](./design/01_REQUEST_DEFINITION.md) の SC-001〜SC-009 に照らして行う。上記の未確認項目を確認せず、全体を `PASS` と記録しない。

## 7. Troubleshooting（症状別の確認先）

| Symptom（症状） | Check（最初に見る場所） | Next Action（対処） |
|---|---|---|
| Build が失敗 | Vercel Build Logs、Root Directory、Container preset、Dockerfile | 対象 commit と失敗行を照合し、原因箇所のみ修正する |
| ドメイン直下が 404 | URL のパス | `/dokoTsubu/Login` を開く。ここも失敗なら Runtime Logs を確認する |
| 起動できない・DB 接続エラー | Production 変数の名前と適用先、Aiven service 状態、JDBC URL、Runtime Logs | 秘密値を公開せず設定を修正し、新しい deployment で確認する |
| Session 関連エラー | `SPRING_SESSION` / `SPRING_SESSION_ATTRIBUTES` の存在、DB 接続 | 自動作成は無効。対象DBを確定して DDL 適用状況を調べる |
| 投稿は残るが Gemini の一言が失敗 | Gemini キーの有効性、Production 変数、Runtime Logs | 設定を修正して再デプロイし、投稿保存と応答を分けて確認する |
| 変更した環境変数が効かない | Deployment 作成時刻と設定変更時刻 | 設定変更後に新しい Production deployment を作成する |

## 8. Security and Record Keeping（秘密情報と記録）

- API キー、DB パスワード、接続 URL の資格情報、個人情報の実値を文書・Git・画面共有・ログに残さない。スクリーンショットは機密欄を含めない。
- 本番デプロイの記録には日時、commit、deployment URL、status、確認した画面・結果、未確認項目を残す。DB や Gemini の成功を別の確認結果から推定しない。
- Git 管理外の `.local-secrets/` はローカル運用資産であり、削除可能なバックアップ領域とは区別する。`BakaUpArea` をアプリ起動・運用の参照先にしない。

## 9. References（参照）

- [Vercel Environment Variables](https://vercel.com/docs/environment-variables)：変数の適用先と、新しいデプロイへの反映。
- [Vercel Environments](https://vercel.com/docs/deployments/environments)：Production deployment と公開ドメイン。
- [Aiven: Connect to MySQL with Java](https://aiven.io/docs/products/mysql/howto/connect-with-java)：Aiven の Java/JDBC 接続。
- [Aiven: Create a database](https://aiven.io/docs/products/mysql/howto/create-database)：Aiven 側の database 管理。

本書の設定名、Dockerfile、context path、テーブル名、画面フローはこのリポジトリの現行ファイルで確認した。Vercel 管理画面の表示名・導線はサービス側の変更により変わり得るため、操作時の画面で照合する。
