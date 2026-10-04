# どこつぶ (DokoTsubu)




| Item（項目）                | Value（値）       |
| ----------------------- | -------------- |
| Document ID（文書ID）       | LEGACY-001     |
| Version（バージョン）          | 0.3            |
| Status（ステータス）           | Draft          |
| Created Date（作成日）       | 2024-06-15     |
| Last Updated（最終更新日）     | 2026-10-04     |
| Owner（管理者）              | Takashi Oikawa |
| Related Documents（関連文書） | [デプロイ・運用手順書](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) / [設計書一覧](./docs/design/README.md) |


> シンプルなつぶやき共有Webアプリケーション (Simple Microblogging Web Application)

---

## 概要 (Overview)

「どこつぶ」は、ユーザーが短いテキスト（つぶやき）を投稿・閲覧・検索・編集・削除できるWebアプリケーションです。職業訓練校での実践ユニット訓練のアプリです。

公開アプリの入口: [どこつぶのログイン画面](https://doko-tsubu-platform.vercel.app/dokoTsubu/Login)

---

## Current Version 3（Version 3 の現行構成）

| Item（項目） | Configuration（構成） |
|---|---|
| Application（アプリ） | `DokoTsubu3/`。Java 21 / Spring Boot / Spring MVC / JSP / JDBC |
| Runtime（実行方式） | 実行可能 WAR + embedded Tomcat |
| Hosting（公開先） | Vercel Container。入口 URL は上記 |
| Production Database（本番DB） | Aiven MySQL を接続先に設定。実接続の確認状況は [運用手順書](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) を参照 |
| Session（セッション） | `HttpSession` API + Spring Session JDBC。本番での維持動作の確認状況は運用手順書を参照 |

---

## Version 3 に至る累積変更 (Cumulative Changes through Version 3)

出発点は教材の H2 版である。現行のデータベースは、その後に移行した MySQL である。

Version 3 に至るまでの累積変更は次のとおり。

- MySQL への移行
- ユーザー登録、つぶやきの検索、編集、削除
- DokoTsubu2 の Gemini 連携

Version 3（`DokoTsubu3`）で扱う内容は次のとおり。

- Spring Boot 化
- 編集・削除の本人限定
- 秘密情報の外部設定
- Spring Session JDBC。ローカル MySQL で実装・確認済み

Version 3 は Vercel に公開済みである。確認済みの公開 URL、設定、動作範囲、未確認項目は [Deployment and Operation Guide（デプロイ・運用手順書）](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) を参照。

設計、実装、レビューは AI が分担した。

---

## 機能一覧 (Features)


| 機能 (Feature)      | 説明 (Description)   |
| ----------------- | ------------------ |
| ユーザー登録 (Register) | ユーザー名・パスワードで新規登録   |
| ログイン (Login)      | 登録済みユーザーのログイン認証    |
| ログアウト (Logout)    | セッションを破棄してログアウト    |
| つぶやき投稿 (Post)     | テキストを投稿してタイムラインに表示 |
| つぶやき一覧 (List)     | 全ユーザーのつぶやきを新着順で表示  |
| つぶやき検索 (Search)   | キーワードでつぶやきを絞り込み    |
| つぶやき編集 (Update)   | 投稿済みつぶやきのテキストを編集   |
| つぶやき削除 (Delete)   | 投稿済みつぶやきを削除        |


---



## Legacy Tech Stack（Spring Boot 化前の技術スタック・参考）

以下は Spring Boot 化前の MySQL 構成である。現行の Version 3 は上の構成表を参照。


| 分類 (Category)             | 技術 (Technology)               |
| ------------------------- | ----------------------------- |
| 言語 (Language)             | Java SE 21                    |
| サーバー (Server)             | Apache Tomcat 11              |
| フレームワーク (Framework)       | Jakarta Servlet / JSP         |
| データベース (Database)         | MySQL 8.x                     |
| JDBCドライバ (JDBC Driver)    | mysql-connector-j 9.5.0       |
| IDE                       | Eclipse (Dynamic Web Project) |
| バージョン管理 (Version Control) | Git / GitHub                  |


---



## Legacy Architecture（Spring Boot 化前の構成・参考）

以下は Spring Boot 化前の構成である。Version 3 は `DokoTsubu3` の Spring Boot 版であり、Vercel で公開済みである。

```
MVC パターン (MVC Pattern)

[View]      JSP (src/main/webapp/WEB-INF/jsp/)
[Controller] Servlet (servlet/)
[Model]     Logic + DAO (model/ + dao/)
[DB]        MySQL
```

---



## Legacy Screen Flow（Spring Boot 化前の画面構成・参考）

```
index.jsp（TOP / ログイン画面）
├── /Register → ユーザー登録画面
└── /Login    → ログイン処理
                └── /Main → メイン画面（つぶやき一覧・投稿・検索）
                            ├── /UpdateMutter → 編集画面
                            ├── /DeleteMutter → 削除処理
                            └── /Logout       → ログアウト
```

---



## 作者 (Author)

- **及川 孝志 (Takashi Oikawa)**
- 作成開始: 2024-06-15
- 最終更新: 2026-10-04
