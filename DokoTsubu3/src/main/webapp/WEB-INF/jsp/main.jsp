<%--
Program Name: main.jsp
Language: JSP
Function: Mutter list screen (SCR-005) for implemented list features only
Created: 2026-09-29
Last Updated: 2026-09-29
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3 FR-004. No post, search, Gemini, edit, or delete.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="dokotsubu.model.LoginUser,dokotsubu.model.Mutter,java.util.List" %>
<%
LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
List<Mutter> mutterList = (List<Mutter>) request.getAttribute("mutterList");
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
      <p class="dt-hero__lead">つぶやき一覧</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="ユーザー情報と操作">
        <p class="dt-text"><%= loginUser.getName() %> さん、ログイン中</p>
        <p class="dt-help-links">
          <a class="dt-link" href="<%= request.getContextPath() %>/Main">更新</a>
          <a class="dt-link" href="<%= request.getContextPath() %>/Logout">ログアウト</a>
        </p>
      </section>

      <section class="dt-card" aria-label="投稿一覧">
        <h2 class="dt-card__title">投稿一覧</h2>
        <% for (Mutter mutter : mutterList) { %>
          <p class="dt-text"><%= mutter.getUserName() %>：<%= mutter.getText() %></p>
        <% } %>
      </section>
    </main>
  </div>
</body>
</html>
