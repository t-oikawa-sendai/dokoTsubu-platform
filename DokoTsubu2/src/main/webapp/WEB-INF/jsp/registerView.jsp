<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">ユーザー登録</h1>
      <p class="dt-hero__lead">必要事項を入力して登録してください。</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="登録フォーム">
        <h2 class="dt-card__title">登録情報</h2>

        <% if(errorMsg != null){ %>
          <p class="dt-error" role="alert"><%= errorMsg %></p>
        <% } %>

        <form method="post" action="Register" class="dt-form">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-username">ユーザー名</label>
            <input id="dt-username" class="dt-field__input" type="text" name="username" autocomplete="username">
          </div>

          <div class="dt-field">
            <label class="dt-field__label" for="dt-password">パスワード</label>
            <input id="dt-password" class="dt-field__input" type="password" name="password" autocomplete="new-password">
          </div>

          <div class="dt-actions">
            <input class="dt-btn dt-btn--primary" type="submit" value="登録">
          </div>
        </form>

        <p class="dt-help-links">
          <a class="dt-link" href="index.jsp">TOPへ</a>
        </p>
      </section>
    </main>
  </div>
</body>
</html>
