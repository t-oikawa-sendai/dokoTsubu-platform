# Environment Setup Guide（環境構築手順書）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | ENV-001 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2026-10-05 |
| Last Updated（最終更新日） | 2026-10-05 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Project README](../README.md) / [Design Documents Index（設計書一覧）](./design/README.md) / [03_DATA_AND_SECURITY_DESIGN.md](./design/03_DATA_AND_SECURITY_DESIGN.md) / [06_OPERATION_AND_HANDOFF.md](./design/06_OPERATION_AND_HANDOFF.md) / [Deployment and Operation Guide（デプロイ・運用手順書）](./DEPLOYMENT_AND_OPERATION_GUIDE.md) |

---

## 1. Purpose and Scope（目的と対象）

Version 3（`DokoTsubu3/`）のローカル開発環境を構築し、localhost で起動するための手順である。

- 対象: ローカル開発環境
- 対象外: Vercel / Aiven / 本番運用。[Deployment and Operation Guide（デプロイ・運用手順書）](./DEPLOYMENT_AND_OPERATION_GUIDE.md) を参照
- DokoTsubu2 の旧手順（`docs/archive/DOKOTSUBU2_ENVIRONMENT_SETUP_GUIDE.md`）は Eclipse / 外部 Tomcat 前提であり、現行正本ではない

---

## 2. Prerequisites（前提ソフトウェア）

| Software | Requirement（要件） |
|---|---|
| Java | Java 21（`DokoTsubu3/pom.xml` の `java.version`） |
| Maven | Maven 3 系 |
| Git | Repository の clone に使用 |
| MySQL | ローカル MySQL。Development DB として使用する |

外部 Tomcat と Eclipse は不要である。

---

## 3. Repository Clone（Repository の取得）

```bash
git clone git@github.com:t-oikawa-sendai/dokoTsubu-platform.git
cd dokoTsubu-platform
```

Application は `DokoTsubu3/` にある。

---

## 4. Local MySQL（ローカル MySQL）

| Item（項目） | Value（値） |
|---|---|
| Schema | `dokotsubu` |
| Domain tables | `USERS` / `MUTTERS` |
| Session tables | `SPRING_SESSION` / `SPRING_SESSION_ATTRIBUTES` |

- Domain table の構造は [03_DATA_AND_SECURITY_DESIGN.md](./design/03_DATA_AND_SECURITY_DESIGN.md) を正とする。Domain table 用 DDL は本 Repository に無い。構造を推測して作らない
- Session table は `DokoTsubu3/db/spring-session-mysql.sql` で作成する。Application 起動時の自動作成は無効である。既存 table には再適用しない
- Target Schema への移行手順は [06_OPERATION_AND_HANDOFF.md](./design/06_OPERATION_AND_HANDOFF.md) を正とする。本書の手順では Schema 変更・データ初期化を行わない

---

## 5. Secrets and Environment Variables（秘密情報と環境変数）

### 5.1 `.local-secrets/`

- ローカルの接続値・API key は Repository 直下の `.local-secrets/` に置く
- `.local-secrets/` は `.gitignore` 対象である。Git に追加しない
- 実値を文書・チャット・スクリーンショット・ログへ記載しない

### 5.2 Required Environment Variables（必要な環境変数）

| Name | Meaning（用途） |
|---|---|
| `DOKOTSUBU_DB_URL` | ローカル MySQL の JDBC URL（`jdbc:mysql://...` 形式） |
| `DOKOTSUBU_DB_USERNAME` | DB ユーザー名 |
| `DOKOTSUBU_DB_PASSWORD` | DB パスワード |
| `DOKOTSUBU_GEMINI_API_KEY` | Gemini API key。未設定でも起動はでき、投稿後の Gemini 一言は失敗文になる |

値は表示せずに、起動するシェルの環境変数として渡す。`.local-secrets/` 内の env ファイルを読み込む例:

```bash
set -a
source .local-secrets/<env-file>
set +a
```

`<env-file>` は各自の Git 管理外ファイル名に置き換える。

---

## 6. Build（ビルド）

```bash
cd DokoTsubu3
mvn -B package
```

成果物は `DokoTsubu3/target/dokotsubu-0.0.1-SNAPSHOT.war`（executable WAR）である。ビルド成功だけでは DB 接続・Gemini 呼び出しの成功を証明できない。

---

## 7. Local Run（ローカル起動）

`DokoTsubu3` で次のどちらかを実行する。

```bash
mvn spring-boot:run
```

```bash
java -jar target/dokotsubu-0.0.1-SNAPSHOT.war
```

embedded Tomcat が既定ポート 8080 で起動する。

---

## 8. Localhost Check（localhost 確認）

1. <http://localhost:8080/dokoTsubu/Login> を開き、ログイン画面が表示されることを確認する
2. 新規登録 → ログイン → メイン画面表示を確認する
3. 投稿し、一覧への表示と Gemini 一言（または失敗文）の表示を確認する

context path は `/dokoTsubu` である。`http://localhost:8080/` 直下はアプリの入口ではない。

画面遷移は [04_UI_AND_FLOW_DESIGN.md](./design/04_UI_AND_FLOW_DESIGN.md)、エンドポイントは [API_SPEC.md](./specs/API_SPEC.md) を参照。
