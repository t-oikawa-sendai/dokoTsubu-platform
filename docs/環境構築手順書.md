# どこつぶ 環境構築手順書 (DokoTsubu Setup Guide)

<!-- Document Info（文書情報） -->
| Item（項目） | Value（値） |
|---|---|
| Document ID（文書ID） | LEGACY-009 |
| Version（バージョン） | 0.1 |
| Status（ステータス） | Draft |
| Created Date（作成日） | 2024-06-15 |
| Last Updated（最終更新日） | 2026-09-12 |
| Owner（管理者） | Takashi Oikawa |
| Related Documents（関連文書） | None |

**作成日 (Created):** 2024-06-15
**最終更新 (Last Updated):** 2026-03-20
**作成者 (Author):** Takashi Oikawa)
**対象OS (Target OS):** macOS (Apple Silicon)

---

## 前提条件 (Prerequisites)

| ツール (Tool) | バージョン (Version) | 確認コマンド (Check Command) |
|--------------|---------------------|------------------------------|
| Java | SE 21 | `java -version` |
| Eclipse | 2025-12以降 | 起動して確認 |
| Tomcat | 11 | Eclipseのサーバー設定で確認 |
| Homebrew | 5.x以降 | `brew --version` |
| Git | 任意 | `git --version` |

---

## STEP 1：MySQLのインストール (Install MySQL)

```bash
# インストール
brew install mysql

# 起動
brew services start mysql

# 起動確認
brew services list | grep mysql
# → mysql started と表示されればOK
```

---

## STEP 2：MySQLの初期設定 (MySQL Initial Setup)

```bash
# パスワードなしでroot接続（初回のみ）
mysql -u root
```

MySQLプロンプト (`mysql>`) に入ったら以下を実行：

```sql
-- rootパスワードの設定
ALTER USER 'root'@'localhost' IDENTIFIED BY '<置換用DBパスワード>';
FLUSH PRIVILEGES;
```

---

## STEP 3：データベース・テーブルの作成 (Create Database & Tables)

```sql
-- データベース作成
CREATE DATABASE dokoTsubu;
USE dokoTsubu;

-- USERSテーブル作成
CREATE TABLE USERS (
  ID   INT          PRIMARY KEY AUTO_INCREMENT,
  NAME VARCHAR(100) NOT NULL UNIQUE,
  PASS VARCHAR(255) NOT NULL
);

-- MUTTERSテーブル作成
CREATE TABLE MUTTERS (
  ID      INT          PRIMARY KEY AUTO_INCREMENT,
  USER_ID INT          NOT NULL,
  TEXT    VARCHAR(140) NOT NULL,
  FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
);

-- 確認
SHOW TABLES;
-- → MUTTERS と USERS が表示されればOK

EXIT;
```

---

## STEP 4：プロジェクトの取得 (Get Project)

### 新規クローンの場合 (New Clone)

```bash
cd ~/Dev
git clone git@github.com:t-oikawa-sendai/dokoTsubu.git
```

### すでにクローン済みの場合 (Already Cloned)

```bash
cd ~/Dev/dokoTsubu
git pull origin main
```

---

## STEP 5：EclipseへのImport (Import to Eclipse)

1. Eclipse起動
2. メニュー → **ファイル (File)** → **インポート (Import)**
3. **既存プロジェクトをワークスペースへ (Existing Projects into Workspace)** を選択
4. ルートディレクトリに `~/Dev/dokoTsubu/dokoTsubu` を指定
5. **完了 (Finish)**

---

## STEP 6：DB接続設定の確認 (Verify DB Connection)

`src/main/java/dao/MuttersDAO.java` と `UserDAO.java` の接続情報は環境変数から取得する。実値は本書に記載しない：

```java
private final String JDBC_URL = System.getenv("DOKOTSUBU_DB_URL");
private final String DB_USER = System.getenv("DOKOTSUBU_DB_USERNAME");
private final String DB_PASS = System.getenv("DOKOTSUBU_DB_PASSWORD");
```

---

## STEP 7：Tomcatで起動 (Start with Tomcat)

1. Eclipseの **Bootダッシュボード (Boot Dashboard)** を開く
2. **dokoTsubu** を選択 → **起動 (Start)**
3. ブラウザで以下にアクセス：

```
http://localhost:8080/dokoTsubu
```

ログイン画面が表示されれば起動成功。

---

## 運用ルール (Operation Rules)

### MySQL 起動・停止

```bash
# 起動
brew services start mysql

# 停止
brew services stop mysql

# 再起動
brew services restart mysql
```

### Git 同期ルール (Git Sync Rules)

```bash
# 作業開始前（必ず実行）
cd ~/Dev/dokoTsubu
git pull origin main

# 作業終了後
git add -A
git commit -m "変更内容のメモ"
git push origin main
```

---

## トラブルシューティング (Troubleshooting)

| 症状 (Symptom) | 原因 (Cause) | 対処 (Solution) |
|----------------|-------------|-----------------|
| `command not found: mysql` | PATHが通っていない | `export PATH="/opt/homebrew/bin:$PATH"` を `.zshrc` に追記 |
| `Access denied for user 'root'` | パスワード誤り | `mysql -u root` でパスワードなし接続し再設定 |
| `No such file or directory: dokoTsubu` | DB未作成 | STEP 3を実行 |
| ブラウザで404 | Tomcat未起動 | BootダッシュボードからTomcatを起動 |
| Eclipseにプロジェクト表示なし | Import未実施 | STEP 5を実施 |
