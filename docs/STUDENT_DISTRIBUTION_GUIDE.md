# Student Distribution and Setup Guide（生徒向け配布・導入手順書）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | STUDENT-DISTRIBUTION-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-10-07 |
| Last Updated（最終更新日） | 2026-10-07 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../README.md) / [Design Documents Index（設計書一覧）](./design/README.md) / [02_REQUIREMENTS_DEFINITION.md](./design/02_REQUIREMENTS_DEFINITION.md) / [03_DATA_AND_SECURITY_DESIGN.md](./design/03_DATA_AND_SECURITY_DESIGN.md) / [Environment Setup Guide（環境構築手順書）](./ENVIRONMENT_SETUP_GUIDE.md) / [Deployment and Operation Guide（デプロイ・運用手順書）](./DEPLOYMENT_AND_OPERATION_GUIDE.md) |

> 修正完了版のDokoTsubu3を生徒が各自のPCで起動するための準備文書です。実行用ZIP、Domain table初期作成SQL、設定サンプルは未作成であり、本書の導入手順は配布版で未検証です。現行アプリには設計確定済み・未実装の仕様が残っています。本書の整備ではアプリ改修・DB移行・ZIP作成・公開を行いません。

生徒側で必要なのはJava 21、MySQL、ブラウザです。各自のローカルDBを使います。Geminiコメントを使う場合は、各自のAPIキーとインターネット接続を用意します。実行用WAR（起動できるアプリ本体）を配布するため、生徒側のビルドは不要です。

---

## 1. 講師：修正完了後の配布物を準備する

### 1.1 配布ファイルと配置案

以下の配布用ファイル名・配置は本書の案です。Cursorによる配布資材作成時に確定します。`spring-session-mysql.sql` と `.gitignore` は既存ファイルです。

| ZIP内のパス | 内容 | 準備状況 |
|---|---|---|
| `DokoTsubu3/dokotsubu.war` | 修正完了版をビルドした実行用WAR | 配布版は未作成 |
| `DokoTsubu3/db/create-student-db.sql` | 空の環境に `dokotsubu` とTarget Schemaの `USERS`／`MUTTERS` を作成 | 新規作成が必要 |
| `DokoTsubu3/db/spring-session-mysql.sql` | ログイン状態を保存する2テーブルを作成 | 既存。配布版との整合確認が必要 |
| `DokoTsubu3/student.properties.example` | 接続設定の空欄サンプル | 新規作成が必要 |
| `STUDENT_GUIDE.md` | 配布時に確定した本手順書 | 現在はDraft |
| `.gitignore` | `.local-secrets/` をGit管理から除外 | 既存 |

生徒の設定ファイル `.local-secrets/student.properties` は、生徒が展開後に作成します。講師の設定ファイル、APIキー、DBパスワード、既存ユーザー・投稿データをZIPに含めません。

### 1.2 資産の区分

| 区分 | 対象・配置 |
|---|---|
| Git管理する正本 | `DokoTsubu3/` のソース、初期作成SQL、設定サンプル、確定した手順書 |
| 講師側の生成物 | WARと配布用ZIP。`DokoTsubu3/target/` 配下で組み立て、Gitへ追加しない |
| 生徒側の起動必須資産 | 展開先の `DokoTsubu3/dokotsubu.war` と、各自の設定ファイル。削除可能なバックアップとして扱わない |
| 秘密情報を含む運用資産 | 展開先の `.local-secrets/student.properties`。Git管理外 |
| テスト専用資産 | 配布確認用のWAR・DB。現行運用環境から分離する |

macOSの配置は `/Users/＜ユーザー名＞/Dev/dokoTsubu-platform/` とします。確認用のテスト環境は `/Users/＜ユーザー名＞/local_test_env/dokoTsubu-platform/`、削除可能なバックアップコピーは `/Users/＜ユーザー名＞/BakaUpArea/dokoTsubu-platform/` に区分し、アプリの起動・運用からバックアップを参照しません。

### 1.3 配布までの手順

1. アプリ修正と対象検証を完了し、配布するコミット、ZIPの版、対応OS、MySQLの版を確定します。
2. 確定設計に沿って、生徒向けのDB初期作成SQLと秘密値を含まない設定サンプルをCursorが作成します。初期作成SQLは空の環境専用とし、既存データの削除・初期化を含めません。講師環境の移行用SQLを、生徒の新規導入に使わせません。
3. `DokoTsubu3` で既存ビルドを実行します。

   ```bash
   mvn -B package
   ```

4. 生成された `target/dokotsubu-0.0.1-SNAPSHOT.war` を、配布用フォルダの `DokoTsubu3/dokotsubu.war` としてコピーし、表のファイルだけを組み立てます。
5. 対象OSのテスト専用環境で、第2節の導入と第3節の動作を確認します。現行ローカルDB・本番DBを確認用に初期化しません。同名DBとの衝突を避けるため、初期導入確認は独立したMySQL環境で行います。
6. 確認結果と未確認事項を手順書へ反映し、配布用ZIPを作成します。ファイル名の例は `dokotsubu-student-vX.Y.Z.zip` です。`X.Y.Z` は確定した配布版に置き換えます。
7. レビュー・明示承認後に、GitHub Releasesへ配布版とZIPを掲載します。生徒にはその版のAssets欄にある実行用ZIPのURLを案内します。

GitHubが自動表示する「Source code (zip)」はソースのアーカイブです。生徒が起動する配布物として、講師が添付した実行用ZIPを指定します。

---

## 2. 生徒：ダウンロードして自分のPCに導入する

> この節は修正完了版の配布後に使う手順案です。現在は必要ファイルが揃っていないため、まだ実行しません。Windows 11のPowerShellとmacOSのターミナルを想定しています。OS別の配布確認は未実施です。

### 2.1 必要なソフトを確認する

Java 21と、講師が配布時に指定した版のMySQLを用意し、MySQLを起動しておきます。アプリはローカルDBを使用します。Geminiコメントを使う場合はインターネット接続も必要です。

PowerShellまたはターミナルで、それぞれ実行します。

```text
java -version
```

```text
mysql --version
```

Javaは21であること、MySQLのコマンドが実行できることを確認します。ソフトの未導入・コマンドが見つからない場合は、講師の指定するインストール手順を先に行います。MySQLの実際の接続は第2.3節で確認します。

### 2.2 ZIPを展開する

講師が案内したGitHub ReleasesのAssets欄から実行用ZIPをダウンロードし、次の場所に展開します。

| OS | 展開先 |
|---|---|
| Windows 11 | `C:\Users\＜ユーザー名＞\Dev\dokoTsubu-platform\` |
| macOS | `/Users/＜ユーザー名＞/Dev/dokoTsubu-platform/` |

展開先の直下に `DokoTsubu3` があり、その中に `dokotsubu.war` があることを確認します。既存の同名フォルダがある場合は上書きせず、講師に確認します。

### 2.3 自分のMySQLに初期テーブルを作る

1. PowerShellまたはターミナルで、展開先の `DokoTsubu3` を作業フォルダにします。

   ```text
   cd ~/Dev/dokoTsubu-platform/DokoTsubu3
   ```

2. DBを作成する権限を持つ、自分のローカルMySQLユーザーで接続します。以下はユーザー名が `root` の場合です。別の名前を指定された場合は `root` の部分を置き換えます。パスワードは接続時に入力します。

   ```text
   mysql -h localhost -P 3306 -u root -p
   ```

3. `mysql>` と表示されたら、次のSQLを実行します。

   ```sql
   SHOW DATABASES LIKE 'dokotsubu';
   ```

   `dokotsubu` が表示された場合は、ここで止めて講師に確認します。既存DBを削除したり、初期作成SQLを再適用したりしません。

4. `dokotsubu` が存在しない場合だけ、配布されたSQLを順に読み込みます。以下はMySQLの画面で実行します。

   ```sql
   SOURCE db/create-student-db.sql;
   USE dokotsubu;
   SOURCE db/spring-session-mysql.sql;
   SHOW TABLES;
   ```

5. `USERS`、`MUTTERS`、`SPRING_SESSION`、`SPRING_SESSION_ATTRIBUTES` の4テーブルが表示されることを確認し、MySQLの画面を終了します。

   ```sql
   EXIT;
   ```

SQL実行が失敗した場合は、続けて再実行せず講師にエラーを伝えます。接続先は各自のローカルDBです。講師のAiven本番DBの接続情報は使用しません。

### 2.4 自分の接続設定を保存する

1. 展開先の `dokoTsubu-platform` 直下に `.local-secrets` フォルダを作成します。
2. `DokoTsubu3/student.properties.example` をコピーし、`.local-secrets/student.properties` として保存します。
3. テキストエディターで、自分のDBユーザー名・DBパスワードを設定します。以下は設定ファイルの書式案です。空欄に自分の値を入力します。

   ```properties
   DOKOTSUBU_DB_URL=jdbc:mysql://localhost:3306/dokotsubu
   DOKOTSUBU_DB_USERNAME=
   DOKOTSUBU_DB_PASSWORD=
   DOKOTSUBU_GEMINI_API_KEY=
   ```

- DBのポートが3306以外の場合は、接続時のポートとURLのポートを合わせます。
- これは `.properties` 形式の設定ファイルです。値を引用符で囲みません。値に逆斜線 `\` がある場合は `\\` と記載します。
- Geminiコメントを利用する場合は、自分のAPIキーを設定します。キーの入手方法は講師の案内に従います。
- Gemini APIキーが空欄でも、起動・登録・ログイン・投稿はできる仕様です。AIコメント欄は失敗文になります。
- 設定ファイルには秘密情報があるため、Git・提出ZIP・チャット・画面共有に含めません。

### 2.5 アプリを起動する

作業フォルダが `DokoTsubu3` であり、`dokotsubu.war` がある状態で実行します。

```text
java -jar dokotsubu.war --spring.config.additional-location=file:../.local-secrets/student.properties
```

追加の設定ファイルを読み込む、Spring Bootの標準機能を使う起動案です。配布版での実動確認は未実施です。設定ファイルがない場合は起動を続行しません。

起動したPowerShell・ターミナルは開いたままにします。ブラウザで次のURLを開きます。

[ローカルのログイン画面](http://localhost:8080/dokoTsubu/Login)

`localhost` は、自分のPCを表す名前です。ほかの生徒のローカルアプリとは、DBも投稿一覧も別になります。

---

## 3. 生徒：修正完了版の動作を確認する

| 操作 | 確認する結果 |
|---|---|
| 新規登録 | ユーザー名・パスワード・性別・年齢感覚を入力し、登録完了画面へ進む |
| ログイン | 登録したアカウントでメイン画面へ進む |
| 投稿 | 入力した文章が一覧へ表示される。AIコメントの成功・失敗は投稿保存と別に確認する |
| 検索 | キーワードに合う投稿が表示される |
| 自分の投稿を編集 | 更新した文章が一覧へ表示される |
| 自分の投稿を削除 | 一覧・検索から表示されなくなる。修正完了版ではDBに削除日時を記録する論理削除 |
| ログアウト | ログイン状態が解除される |

性別の初期値は「ヒミツ」です。年齢感覚には初期選択がなく、自分で選択します。年齢感覚は実年齢や生年月日ではなく、自分自身をどう感じるかを選ぶ項目です。

アプリの終了は、起動したPowerShell・ターミナルで Ctrl + C を押します。次回はMySQLを起動し、第2.5節から再開します。初期作成SQLは再実行しません。

---

## 4. 起動できないとき

| 状況 | 最初に確認すること |
|---|---|
| `java`／`mysql` が見つからない | 指定ソフトの導入と、コマンドを実行できる設定 |
| WARまたは設定ファイルが見つからない | 作業フォルダが `DokoTsubu3` か、ファイル名・配置が手順と一致するか |
| DB接続で失敗する | MySQLの起動、ローカル接続先・ユーザー名・パスワード、4テーブルの作成状況 |
| 8080が使用中 | ほかのアプリを止めず、どこつぶを別ポートで起動する |
| AIコメントだけ失敗する | 自分のAPIキー、インターネット接続、Gemini側の利用可能状態。保存済み投稿は取り消されない仕様 |

8080が使用中の場合の起動案です。8081も使用中なら、講師に空きポートを確認します。

```text
java -jar dokotsubu.war --spring.config.additional-location=file:../.local-secrets/student.properties --server.port=8081
```

この場合は [8081のログイン画面](http://localhost:8081/dokoTsubu/Login) を開きます。エラーを伝える際は、設定ファイル・パスワード・APIキーを貼り付けません。

---

## 5. 講師：配布前に確定する事項

- 修正完了版のコミット、Releaseの版、実行用ZIPの実在URL。
- 対応OSとMySQLの版、必要ソフトの導入手順。
- 初期作成SQL、設定サンプル、本書のファイル名・配置。
- 上記手順による新規導入・設定ファイル読み込み・起動・対象機能の実機確認結果。

現在のRepositoryにはDomain table用の初期作成SQLがありません。本書の作成を、配布資材の完成や実機検証の完了として扱いません。

---

## 6. 参照正本

### プロジェクト

- [Environment Setup Guide（環境構築手順書）](./ENVIRONMENT_SETUP_GUIDE.md)：現行ローカル環境・設定名・起動方法。
- [03_DATA_AND_SECURITY_DESIGN.md](./design/03_DATA_AND_SECURITY_DESIGN.md)：Target Schema、論理削除、秘密情報。
- [02_REQUIREMENTS_DEFINITION.md](./design/02_REQUIREMENTS_DEFINITION.md)：登録項目と機能要件。
- [Deployment and Operation Guide（デプロイ・運用手順書）](./DEPLOYMENT_AND_OPERATION_GUIDE.md)：公開環境と確認範囲。

### 公式仕様

- [Spring Boot: Packaging Executable Archives](https://docs.spring.io/spring-boot/maven-plugin/packaging.html)：実行用WARと `java -jar`。
- [Spring Boot: Externalized Configuration](https://docs.spring.io/spring-boot/reference/features/external-config.html)：追加の設定ファイルと起動時のポート指定。
- [MySQL: mysql Client Commands](https://dev.mysql.com/doc/refman/8.4/en/mysql-commands.html)：SQLファイルを読む `SOURCE`。この参照版を生徒向けMySQLの採用版とするものではありません。
- [GitHub: About releases](https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases)：配布ファイルとソースアーカイブ。
