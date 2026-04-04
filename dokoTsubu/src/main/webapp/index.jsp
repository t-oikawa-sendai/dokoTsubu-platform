<%--JSPのコメント
DATE		: 2025-11-09
LASTUPDATE:2026-04-01
Author:Takashi Oikawa
Function:ランディング（入口）画面
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>DokoTsubu Ver.2.0（Platform Version)</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="dt-page">
  <header class="dt-hero">
    <h1 class="dt-hero__title">DokoTsubu Ver.2.0（Platform Version)</h1>
    <p class="dt-hero__lead">学習用DokoTsubu機能拡張版</p>
  </header>

  <main class="dt-main">
    <section class="dt-login-card" aria-labelledby="login-heading">
      <h2 id="login-heading" class="dt-login-card__title">ログイン</h2>

      <%-- Update:20260404 コンテキスト外へ解決されないよう絶対パス化（例: /dokoTsubu 直下 URL からの相対 Login は /Login になり 404） --%>
      <form class="dt-login-form" action="<%= request.getContextPath() %>/Login" method="post">
        <div class="dt-field">
          <label class="dt-field__label" for="login-name">ユーザー名</label>
          <input class="dt-field__input" id="login-name" type="text" name="name" autocomplete="username">
        </div>
        <div class="dt-field">
          <label class="dt-field__label" for="login-pass">パスワード</label>
          <input class="dt-field__input" id="login-pass" type="password" name="pass" autocomplete="current-password">
        </div>
        <div class="dt-actions">
          <input class="dt-btn dt-btn--primary" type="submit" value="ログイン">
        </div>
      </form>

      <div class="dt-register">
        <p class="dt-register__hint">アカウントをお持ちでない方はこちら</p>
        <p class="dt-register__link"><a href="Register">新規登録へ</a></p>
      </div>
    </section>
  </main>
</div>
</body>
</html>
