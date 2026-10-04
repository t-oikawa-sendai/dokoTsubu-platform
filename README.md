# どこつぶ (DokoTsubu)




| Item（項目）                | Value（値）       |
| ----------------------- | -------------- |
| Document ID（文書ID）       | LEGACY-001     |
| Version（バージョン）          | 0.2            |
| Status（ステータス）           | Draft          |
| Created Date（作成日）       | 2024-06-15     |
| Last Updated（最終更新日）     | 2026-10-04     |
| Owner（管理者）              | Takashi Oikawa |
| Related Documents（関連文書） | None           |


> シンプルなつぶやき共有Webアプリケーション (Simple Microblogging Web Application)

---

## 概要 (Overview)

「どこつぶ」は、ユーザーが短いテキスト（つぶやき）を投稿・閲覧・検索・編集・削除できるWebアプリケーションです。 職業訓練校での実践ユニット訓練のアプリです。

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

Vercel への公開は対象である。Aiven への適用と Vercel 公開は現時点では未実施であり、未確認である。現時点の状態は未公開である。

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



## 技術スタック (Tech Stack)

以下の表は、Spring Boot 化前の MySQL 構成である。教材の H2 版は出発点として前節に分けて記す。Version 3 の Vercel 公開は未実施である。


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



## アーキテクチャ (Architecture)

以下は Spring Boot 化前の構成である。Version 3 は `DokoTsubu3` の Spring Boot 版であり、公開は未実施である。

```
MVC パターン (MVC Pattern)

[View]      JSP (src/main/webapp/WEB-INF/jsp/)
[Controller] Servlet (servlet/)
[Model]     Logic + DAO (model/ + dao/)
[DB]        MySQL
```

---



## 画面構成 (Screen Structure)

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
