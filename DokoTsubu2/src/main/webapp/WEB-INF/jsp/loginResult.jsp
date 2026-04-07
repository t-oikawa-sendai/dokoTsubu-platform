<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
// セッションスコープからユーザー情報を取得
User loginUser = (User) session.getAttribute("loginUser");
//リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">DokoTsubu Ver.2.0（Platform Version)</h1>
      <p class="dt-hero__lead">つぶやき投稿・閲覧</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="ログイン結果">
        <h2 class="dt-card__title">ログイン</h2>

        <% if(loginUser != null) { %>
          <p class="dt-message dt-message--success">ログインに成功しました</p>
          <p class="dt-text">ようこそ<%= loginUser.getName() %>さん</p>
          <p class="dt-actions">
            <a class="dt-btn dt-btn--primary dt-btn--link" href="Main">つぶやき投稿・閲覧へ</a>
          </p>
        <% } else { %>
          <p class="dt-message dt-message--error">ログインに失敗しました</p>
          <p class="dt-error dt-error--inline"><%= errorMsg %></p>
          <p class="dt-help-links">
            <a class="dt-link" href="index.jsp">TOPへ</a>
          </p>
        <% } %>
      </section>
    </main>
  </div>
</body>
</html>
