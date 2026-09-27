<%--
Program Name: logout.jsp
Language: JSP
Function: Logout completion screen (SCR-007)
Created: 2026-09-27
Last Updated: 2026-09-27
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3. session=false so this view does not open a new session.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false" %>
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
      <p class="dt-hero__lead">ログアウト</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="ログアウト結果">
        <h2 class="dt-card__title">ログアウト</h2>
        <p class="dt-text">ログアウトしました</p>
        <p class="dt-help-links">
          <a class="dt-link" href="<%= request.getContextPath() %>/Login">トップへ</a>
        </p>
      </section>
    </main>
  </div>
</body>
</html>
