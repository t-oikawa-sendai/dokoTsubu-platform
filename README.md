# どこつぶ (DokoTsubu)

> シンプルなつぶやき共有Webアプリケーション (Simple Microblogging Web Application)

---

## 概要 (Overview)

「どこつぶ」は、ユーザーが短いテキスト（つぶやき）を投稿・閲覧・検索・編集・削除できるWebアプリケーションです。

---

## 機能一覧 (Features)

| 機能 (Feature) | 説明 (Description) |
|----------------|-------------------|
| ユーザー登録 (Register) | ユーザー名・パスワードで新規登録 |
| ログイン (Login) | 登録済みユーザーのログイン認証 |
| ログアウト (Logout) | セッションを破棄してログアウト |
| つぶやき投稿 (Post) | テキストを投稿してタイムラインに表示 |
| つぶやき一覧 (List) | 全ユーザーのつぶやきを新着順で表示 |
| つぶやき検索 (Search) | キーワードでつぶやきを絞り込み |
| つぶやき編集 (Update) | 投稿済みつぶやきのテキストを編集 |
| つぶやき削除 (Delete) | 投稿済みつぶやきを削除 |

---

## 技術スタック (Tech Stack)

| 分類 (Category) | 技術 (Technology) |
|-----------------|-------------------|
| 言語 (Language) | Java SE 21 |
| サーバー (Server) | Apache Tomcat 11 |
| フレームワーク (Framework) | Jakarta Servlet / JSP |
| データベース (Database) | MySQL 8.x |
| JDBCドライバ (JDBC Driver) | mysql-connector-j 9.5.0 |
| IDE | Eclipse (Dynamic Web Project) |
| バージョン管理 (Version Control) | Git / GitHub |

---

## アーキテクチャ (Architecture)

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
- 最終更新: 2026-03-20
