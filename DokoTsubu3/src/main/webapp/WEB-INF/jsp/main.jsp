<%--
Program Name: main.jsp
Language: JSP
Function: Mutter list, post, and search screen (SCR-005)
Created: 2026-09-29
Last Updated: 2026-10-05
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3 FR-004, FR-005, FR-006, FR-007, FR-008, and FR-009. Dynamic text is HTML-escaped. Edit and delete controls only when mutter.userId equals loginUser.id. Post and delete forms include csrfToken. Delete is POST /DeleteMutter.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="dokotsubu.model.LoginUser,dokotsubu.model.Mutter,java.util.List" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%
LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
List<Mutter> mutterList = (List<Mutter>) request.getAttribute("mutterList");
String errorMsg = (String) request.getAttribute("errorMsg");
String aiMsg = (String) request.getAttribute("aiMsg");
String csrfToken = (String) request.getAttribute("csrfToken");
if (csrfToken == null) {
    csrfToken = "";
}
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
        <p class="dt-text"><%= HtmlUtils.htmlEscape(loginUser.getName() == null ? "" : loginUser.getName()) %> さん、ログイン中</p>
        <p class="dt-help-links">
          <a class="dt-link" href="<%= request.getContextPath() %>/Main">更新</a>
          <a class="dt-link" href="<%= request.getContextPath() %>/Logout">ログアウト</a>
        </p>
      </section>

      <section class="dt-card" aria-label="投稿">
        <h2 class="dt-card__title">投稿</h2>
        <% if (errorMsg != null) { %>
          <p class="dt-error" role="alert"><%= HtmlUtils.htmlEscape(errorMsg) %></p>
        <% } %>
        <% if (aiMsg != null) { %>
          <p class="dt-text">AI：<%= HtmlUtils.htmlEscape(aiMsg) %></p>
        <% } %>
        <form method="post" action="<%= request.getContextPath() %>/Main" class="dt-form">
          <input type="hidden" name="csrfToken" value="<%= HtmlUtils.htmlEscape(csrfToken) %>">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-text">つぶやき</label>
            <input id="dt-text" class="dt-field__input" type="text" name="text">
          </div>
          <div class="dt-actions">
            <input class="dt-btn dt-btn--primary" type="submit" value="つぶやく">
          </div>
        </form>
      </section>

      <section class="dt-card" aria-label="検索">
        <h2 class="dt-card__title">検索</h2>
        <form method="get" action="<%= request.getContextPath() %>/SearchMutter" class="dt-form">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-keyword">キーワード</label>
            <input id="dt-keyword" class="dt-field__input" type="text" name="keyword">
          </div>
          <div class="dt-actions">
            <input class="dt-btn dt-btn--primary" type="submit" value="検索">
          </div>
        </form>
      </section>

      <section class="dt-card" aria-label="投稿一覧">
        <h2 class="dt-card__title">投稿一覧</h2>
        <% for (Mutter mutter : mutterList) { %>
          <div class="dt-text"><%= HtmlUtils.htmlEscape(mutter.getUserName() == null ? "" : mutter.getUserName()) %>：<%= HtmlUtils.htmlEscape(mutter.getText() == null ? "" : mutter.getText()) %>
            <% if (mutter.getUserId() == loginUser.getId()) { %>
              <a class="dt-link" href="<%= request.getContextPath() %>/UpdateMutter?id=<%= mutter.getId() %>">編集</a>
              <form method="post" action="<%= request.getContextPath() %>/DeleteMutter" style="display:inline">
                <input type="hidden" name="id" value="<%= mutter.getId() %>">
                <input type="hidden" name="csrfToken" value="<%= HtmlUtils.htmlEscape(csrfToken) %>">
                <button type="submit" class="dt-link" style="background:none;border:0;padding:0;font:inherit;cursor:pointer">削除</button>
              </form>
            <% } %>
          </div>
        <% } %>
      </section>
    </main>
  </div>
</body>
</html>
