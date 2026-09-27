<%--
Program Name: loginResult.jsp
Language: JSP
Function: Login result screen (SCR-004)
Created: 2026-09-13
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3. Success and failure follow request attribute loginSuccess.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="dokotsubu.model.LoginUser" %>
<%
Boolean loginSuccess = (Boolean) request.getAttribute("loginSuccess");
LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DokoTsubu Ver.3.0（Springboot Version)</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">DokoTsubu Ver.3.0（Springboot Version)</h1>
      <p class="dt-hero__lead">つぶやき投稿・閲覧</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="ログイン結果">
        <h2 class="dt-card__title">ログイン</h2>

        <% if(Boolean.TRUE.equals(loginSuccess)) { %>
          <p class="dt-message dt-message--success">ログインに成功しました</p>
          <p class="dt-text">ようこそ<%= loginUser.getName() %>さん</p>
          <p class="dt-actions">
            <a class="dt-btn dt-btn--primary dt-btn--link" href="<%= request.getContextPath() %>/Main">つぶやき投稿・閲覧へ</a>
          </p>
        <% } else { %>
          <p class="dt-message dt-message--error">ログインに失敗しました</p>
          <p class="dt-error dt-error--inline"><%= errorMsg %></p>
          <p class="dt-help-links">
            <a class="dt-link" href="<%= request.getContextPath() %>/Login">TOPへ</a>
          </p>
        <% } %>
      </section>
    </main>
  </div>
</body>
</html>
