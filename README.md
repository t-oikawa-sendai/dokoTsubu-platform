# DokoTsubu（どこつぶ）

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | PROJECT-README-001 |
| Version（バージョン） | 0.6 |
| Status（ステータス） | Review |
| Created Date（作成日） | 2024-06-15 |
| Last Updated（最終更新日） | 2026-10-08 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | [Design Documents Index（設計書一覧）](./docs/design/README.md) / [API Specification（API仕様書）](./docs/specs/API_SPEC.md) / [Gemini Integration Specification（Gemini連携仕様書）](./docs/specs/GEMINI_INTEGRATION_SPEC.md) / [Environment Setup Guide（環境構築手順書）](./docs/ENVIRONMENT_SETUP_GUIDE.md) / [Deployment and Operation Guide（デプロイ・運用手順書）](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) / [CHANGELOG.md](./CHANGELOG.md) |

> シンプルなつぶやき共有Webアプリケーション (Simple Microblogging Web Application)

公開アプリの入口: [どこつぶのログイン画面](https://doko-tsubu-platform.vercel.app/dokoTsubu/Login)

---

## 1. Overview（概要）

Java Webアプリケーションの基本的なMVCアーキテクチャを起点に、Webアプリケーション開発の基本構成を段階的に学習するためのサンプルアプリケーション。

主な学習対象:

- JDBCによるDBアクセス
- ユーザー登録・ログイン
- HttpSession / Spring Session JDBC
- BCryptによるパスワードハッシュ化
- CSRF対策
- 認可
- Gemini API連携
- MySQL
- Vercel
- Aiven

---

## 2. Version Features（Version別実装機能一覧）

### 2.1 Positioning（位置付け）

| Version | 位置付け | 対応先 |
|---|---|---|
| Version 1 | テキスト教材を基準とする基本版 | 別 Repository [t-oikawa-sendai/dokoTsubu](https://github.com/t-oikawa-sendai/dokoTsubu)（Version 1 の正本） |
| Version 2 | Version 1を基に機能追加した版 | 本 Repository の `DokoTsubu2/` |
| Version 3 | Spring Bootへ移行した現行版 | 本 Repository の `DokoTsubu3/` |

- 教材の出発点は H2Database 版である。Version 1 の実コードは MySQL へ移行済みである
- Version 1 の正本は別 Repository `t-oikawa-sendai/dokoTsubu` である
- 本 Repository 直下の `dokoTsubu/` は Version 1 本体ではない。Version 2 の JSP 1 ファイルだけが残っている

### 2.2 Feature Comparison（機能比較）

○ は実コードで確認済み、― は実装なしを表す。

| 項目 | Version 1 | Version 2 | Version 3 |
|---|---|---|---|
| MVC | ○ Servlet / JSP | ○ Servlet / JSP | ○ Spring MVC / JSP |
| Login | ○ | ○ | ○ |
| Logout | ○ | ○ | ○ |
| Mutter List | ○ | ○ | ○ |
| Mutter Post | ○ | ○ | ○ |
| User Registration | ○ | ○ | ○ |
| Search | ○ | ○ | ○ |
| Update | ○ | ○ | ○ |
| Delete | ○ | ○ | ○ |
| JDBC | ○ | ○ | ○ |
| Database | MySQL | MySQL | MySQL（Development: local MySQL / Production: Aiven MySQL） |
| Gemini API | ― | ○ | ○ |
| Spring Boot | ― | ― | ○ |
| 認可（編集・削除の投稿者本人限定） | ― | ― | ○ |
| BCrypt | ― | ― | ○ |
| CSRF | ― | ― | ○ |
| Spring Session JDBC | ― | ― | ○ |

Version 3 で設計確定済み・未実装の項目:

- `USERS` / `MUTTERS` の論理削除（現行の投稿削除は物理削除）
- User プロフィール `GENDER` / `AGE_FEELING` の登録項目追加
- Gemini コメント生成への User プロフィール利用

確定仕様は [Design Documents Index（設計書一覧）](./docs/design/README.md) を正とする。

---

## 3. Current Version 3（Version 3 の現行構成）

| Item（項目） | Configuration（構成） |
|---|---|
| Application（アプリ） | `DokoTsubu3/` |
| Language（言語） | Java 21 |
| Framework（フレームワーク） | Spring Boot / Spring MVC |
| View（画面） | JSP |
| Persistence（永続化） | JDBC |
| Database（DB） | MySQL |
| Build（ビルド） | Maven |
| Runtime（実行方式） | executable WAR + embedded Tomcat |
| Session（セッション） | HttpSession API + Spring Session JDBC |
| Hosting（公開先） | Vercel |
| Production Database（本番DB） | Aiven MySQL |

---

## 4. Deploy / Public Environment（公開環境）

詳細運用は [Deployment and Operation Guide（デプロイ・運用手順書）](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) を参照。

### 4.1 Vercel

- Version 3 の公開先である
- `DokoTsubu3` を実行する Application hosting である
- 秘密情報は Vercel Environment Variables で管理する
- 公開URL: <https://doko-tsubu-platform.vercel.app/dokoTsubu/Login>

### 4.2 Aiven

- Version 3 の Production MySQL である
- Application の Domain Data 保存先である
- Spring Session JDBC の Session 保存先である
- DB 接続秘密情報の実値は README へ書かない
- Development では local MySQL を使用する

ローカル開発環境は [Environment Setup Guide（環境構築手順書）](./docs/ENVIRONMENT_SETUP_GUIDE.md) を参照。

---

## 5. Documents（関連文書）

| Category | Document |
|---|---|
| Design（設計） | [docs/design/README.md](./docs/design/README.md) |
| API Specification（API仕様） | [docs/specs/API_SPEC.md](./docs/specs/API_SPEC.md) |
| Gemini Integration（Gemini連携） | [docs/specs/GEMINI_INTEGRATION_SPEC.md](./docs/specs/GEMINI_INTEGRATION_SPEC.md) |
| Environment Setup（環境構築） | [docs/ENVIRONMENT_SETUP_GUIDE.md](./docs/ENVIRONMENT_SETUP_GUIDE.md) |
| Deployment / Operation（デプロイ・運用） | [docs/DEPLOYMENT_AND_OPERATION_GUIDE.md](./docs/DEPLOYMENT_AND_OPERATION_GUIDE.md) |
| Student Distribution（生徒向け配布準備） | [docs/STUDENT_DISTRIBUTION_GUIDE.md](./docs/STUDENT_DISTRIBUTION_GUIDE.md)。Draft。配布資材は未作成、導入手順は未検証 |
| Current State（現在状態・残作業） | [project-notes/CURRENT.md](./project-notes/CURRENT.md) |
| Change History（変更履歴） | [CHANGELOG.md](./CHANGELOG.md) |
| Legacy Documents（旧文書） | `docs/archive/`。DokoTsubu2 時点の文書。現行正本ではない |

AIはAGENTS.mdのRequired Reading Orderを守り、変更着手前に[CURRENT.md](./project-notes/CURRENT.md)で現在対象・残作業・次作業を確認する。

---

## 6. Author（作者）

- **及川 孝志 (Takashi Oikawa)**
- 作成開始: 2024-06-15
- 最終更新: 2026-10-08
- 設計、実装、レビューは AI が分担した
