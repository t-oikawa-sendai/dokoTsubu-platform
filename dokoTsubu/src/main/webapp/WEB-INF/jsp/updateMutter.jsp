<%--
  つぶやき編集画面（DokoTsubu Platform 用 JSP）
  POST 先・パラメータ名は UpdateMutter サーブレットと整合させること。
  Update:20260405
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id = (String) request.getAttribute("id");
String errorMsg = (String) request.getAttribute("errorMsg");
String textVal = (String) request.getAttribute("text");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>つぶやき編集 | DokoTsubu</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<style>
/* Update:20260405 編集画面のみ：main.jsp と同系の背景（style.css は未変更） */
body {
  background-color: #ebe6dc;
}
</style>
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">DokoTsubu Ver.2.0（Platform Version)</h1>
      <p class="dt-hero__lead">つぶやき編集</p>
    </header>

    <main class="dt-main dt-main--wider">
      <section class="dt-card" aria-label="つぶやき編集フォーム">
        <% if (errorMsg != null) { %>
        <p class="dt-error" role="alert"><%= errorMsg %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/UpdateMutter" method="post"
            enctype="application/x-www-form-urlencoded" accept-charset="UTF-8"
            class="dt-form dt-form--compact">
          <input type="hidden" name="id" value="<%= id != null ? id : "" %>">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-update-text">つぶやき</label>
            <input id="dt-update-text" class="dt-field__input" type="text" name="text"
                value="<% if (textVal != null) { %><%= textVal.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;") %><% } %>">
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
