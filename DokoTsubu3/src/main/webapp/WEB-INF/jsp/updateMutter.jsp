<%--
Program Name: updateMutter.jsp
Language: JSP
Function: Mutter edit screen (SCR-006)
Created: 2026-09-29
Last Updated: 2026-10-05
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3 FR-007. Dynamic text is HTML-escaped. Shows DB text on GET and submitted text on POST error. Update form includes csrfToken.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="dokotsubu.model.Mutter" %>
<%@ page import="org.springframework.web.util.HtmlUtils" %>
<%
Mutter mutter = (Mutter) request.getAttribute("mutter");
String errorMsg = (String) request.getAttribute("errorMsg");
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
      <p class="dt-hero__lead">つぶやき編集</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="つぶやき編集">
        <h2 class="dt-card__title">つぶやき編集</h2>
        <% if (errorMsg != null) { %>
          <p class="dt-error" role="alert"><%= HtmlUtils.htmlEscape(errorMsg) %></p>
        <% } %>
        <form method="post" action="<%= request.getContextPath() %>/UpdateMutter" class="dt-form">
          <input type="hidden" name="csrfToken" value="<%= HtmlUtils.htmlEscape(csrfToken) %>">
          <input type="hidden" name="id" value="<%= mutter.getId() %>">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-text">つぶやき</label>
            <input id="dt-text" class="dt-field__input" type="text" name="text" value="<%= HtmlUtils.htmlEscape(mutter.getText() == null ? "" : mutter.getText()) %>">
          </div>
          <div class="dt-actions">
            <input class="dt-btn dt-btn--primary" type="submit" value="更新">
          </div>
        </form>
        <p class="dt-help-links">
          <a class="dt-link" href="<%= request.getContextPath() %>/Main">一覧へ戻る</a>
        </p>
      </section>
    </main>
  </div>
</body>
</html>
