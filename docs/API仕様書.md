# どこつぶ API仕様書 (DokoTsubu API Specification)

**作成日 (Created):** 2024-06-15  
**最終更新 (Last Updated):** 2026-04-01  
**作成者 (Author):** Takashi Oikawa 
**ベースURL (Base URL):** `http://localhost:8080/dokoTsubu`

> **注意 (Note)**  
> ベースURL (Base URL) はローカル実行環境 (Local Runtime Environment) の設定に依存する。  
> 現行確認時点 (At the Time of Current Verification) では、`http://localhost:8080/dokoTsubu` を基準値 (Default Base URL) とする。  
> Tomcat のコンテキストルート (Context Root) を変更した場合は、この値も合わせて更新すること。

---

## 1. ユーザー登録 (User Registration)

### GET /Register
登録フォーム画面を表示する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/Register` |
| 認証 (Auth) | 不要 |
| フォワード先 (Forward) | `WEB-INF/jsp/registerView.jsp` |

---

### POST /Register
ユーザーを登録する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | POST |
| URL | `/Register` |
| 認証 (Auth) | 不要 |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| username | String | ✅ | ユーザー名 |
| password | String | ✅ | パスワード |

**レスポンス (Response)**

| 条件 | 遷移先 |
|------|--------|
| 登録成功 | `WEB-INF/jsp/registerResult.jsp` |
| ユーザー名重複 | `WEB-INF/jsp/registerView.jsp`（errorMsg: ユーザー名が既に存在します。） |
| その他DBエラー | `WEB-INF/jsp/registerView.jsp`（errorMsg表示） |
| 未入力あり | `WEB-INF/jsp/registerView.jsp`（errorMsg表示） |

---

## 2. ログイン (Login)

> **注意 (Note)**  
> 本システムでは、ユーザー登録 (User Registration) とログイン (Login) で、使用するリクエストパラメーター名 (Request Parameter Names) が異なる。  
> - ユーザー登録 (User Registration): `username` / `password`  
> - ログイン (Login): `name` / `pass`  
> これは現行実装 (Current Implementation) に合わせた仕様 (Specification) である。

### POST /Login

| 項目 | 内容 |
|------|------|
| メソッド (Method) | POST |
| URL | `/Login` |
| 認証 (Auth) | 不要 |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| name | String | ✅ | ユーザー名 |
| pass | String | ✅ | パスワード |

**レスポンス (Response)**

| 条件 | 処理 | 遷移先 |
|------|------|--------|
| 認証成功 | セッションに `loginUser` を保存 | `WEB-INF/jsp/loginResult.jsp` |
| 認証失敗 | errorMsg をセット | `WEB-INF/jsp/loginResult.jsp` |
| 未入力あり | errorMsg をセット | `WEB-INF/jsp/loginResult.jsp` |

**セッション保存値 (Session Attribute)**

| キー | 型 | 内容 |
|------|----|------|
| loginUser | User | ログイン中のユーザー情報（id / name / pass） |

---

## 3. ログアウト (Logout)

### GET /Logout

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/Logout` |
| 認証 (Auth) | 不要 |
| 処理 | セッション破棄（`session.invalidate()`） |
| フォワード先 | `WEB-INF/jsp/logout.jsp` |

---

## 4. メイン画面 / つぶやき投稿 (Main / Post Mutter)

### GET /Main
つぶやき一覧を取得してメイン画面を表示する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/Main` |
| 認証 (Auth) | 要ログイン |

**レスポンス (Response)**

| 条件 | 遷移先 |
|------|--------|
| ログイン済み | `WEB-INF/jsp/main.jsp`（mutterList をセット） |
| 未ログイン | `index.jsp` へリダイレクト |

**リクエストスコープ (Request Attribute)**

| キー | 型 | 内容 |
|------|----|------|
| mutterList | List\<Mutter\> | つぶやき一覧（ID降順） |

---

### POST /Main
つぶやきを投稿する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | POST |
| URL | `/Main` |
| 認証 (Auth) | 要ログイン |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| text | String | ✅ | つぶやき内容 |

**レスポンス (Response)**

| 条件 | 処理 | 遷移先 |
|------|------|--------|
| 投稿成功 | DBにINSERT | `WEB-INF/jsp/main.jsp` |
| 未入力 | errorMsg をセット | `WEB-INF/jsp/main.jsp` |

---

## 5. つぶやき検索 (Search Mutter)

### GET /SearchMutter

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/SearchMutter` |
| 認証 (Auth) | 要ログイン |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| keyword | String | ✅ | 検索キーワード |

**レスポンス (Response)**

| 条件 | 遷移先 |
|------|--------|
| ログイン済み | `WEB-INF/jsp/main.jsp`（絞り込まれた mutterList をセット） |
| 未ログイン | `/dokoTsubu/` へリダイレクト |

---

## 6. つぶやき編集 (Update Mutter)

### GET /UpdateMutter
編集フォーム画面を表示する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/UpdateMutter?id={mutterId}` |
| 認証 (Auth) | 要ログイン (Login Required)（現行実装 (Current Implementation) では未チェック (Not Yet Enforced)。既知制約 (Known Limitation)） |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| id | String | ✅ | 編集対象のつぶやきID |

**レスポンス (Response)**

| 遷移先 |
|--------|
| `WEB-INF/jsp/updateMutter.jsp`（id をリクエストスコープにセット） |

> **補足 (Supplement)**  
> 本機能 (This Function) は仕様上 (By Specification) はログイン必須 (Login Required) とする。  
> ただし、現行実装 (Current Implementation) ではログインチェック (Login Check) は未実装 (Not Implemented) であり、今後の対応対象 (Future Work) である。

---

### POST /UpdateMutter
つぶやきを更新する。

| 項目 | 内容 |
|------|------|
| メソッド (Method) | POST |
| URL | `/UpdateMutter` |
| 認証 (Auth) | 要ログイン (Login Required)（現行実装 (Current Implementation) では未チェック (Not Yet Enforced)。既知制約 (Known Limitation)） |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| id | String | ✅ | 更新対象のつぶやきID |
| text | String | ✅ | 更新後のテキスト |

**レスポンス (Response)**

| 遷移先 |
|--------|
| `/dokoTsubu/Main` へリダイレクト |

> **補足 (Supplement)**  
> 本機能 (This Function) は仕様上 (By Specification) はログイン必須 (Login Required) とする。  
> ただし、現行実装 (Current Implementation) ではログインチェック (Login Check) は未実装 (Not Implemented) であり、今後の対応対象 (Future Work) である。

---

## 7. つぶやき削除 (Delete Mutter)

### GET /DeleteMutter

| 項目 | 内容 |
|------|------|
| メソッド (Method) | GET |
| URL | `/DeleteMutter?id={mutterId}` |
| 認証 (Auth) | 要ログイン (Login Required)（現行実装 (Current Implementation) では未チェック (Not Yet Enforced)。既知制約 (Known Limitation)） |

**リクエストパラメーター (Request Parameters)**

| パラメーター | 型 | 必須 | 説明 |
|-------------|-----|------|------|
| id | String | ✅ | 削除対象のつぶやきID |

**レスポンス (Response)**

| 遷移先 |
|--------|
| `/dokoTsubu/Main` へリダイレクト |

> **補足 (Supplement)**  
> 本機能 (This Function) は仕様上 (By Specification) はログイン必須 (Login Required) とする。  
> ただし、現行実装 (Current Implementation) ではログインチェック (Login Check) は未実装 (Not Implemented) であり、今後の対応対象 (Future Work) である。

---

## 8. Mutterクラス仕様 (Mutter Class Specification)

| フィールド | 型 | 説明 |
|------------|-----|------|
| id | int | つぶやきID |
| userId | int | 投稿者のユーザーID |
| userName | String | 投稿者のユーザー名 |
| text | String | つぶやき内容 |

---

## 9. Userクラス仕様 (User Class Specification)

| フィールド | 型 | 説明 |
|------------|-----|------|
| id | int | ユーザーID |
| name | String | ユーザー名 |
| pass | String | パスワード |