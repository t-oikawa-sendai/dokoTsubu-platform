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
<title>つぶやき編集画面</title>
</head>
<body>
<h1>つぶやき編集画面</h1>
<% if (errorMsg != null) { %>
<p role="alert"><%= errorMsg.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") %></p>
<% } %>
<!-- Update:20260404 本コメントがソースに無い場合は、ブラウザが古い JSP を表示しているかデプロイ未反映の可能性 -->
<%-- Update:20260404 コンテキスト /dokoTsubu の実行用に Eclipse が Dev/dokoTsubu/... を配信している場合、当ファイルとその実体を同一に保つ --%>
<%-- Update:20260404 action コンテキスト対応／method=post＋enctype／accept-charset 明示（配信 HTML とソース確認用） --%>
<form action="<%= request.getContextPath() %>/UpdateMutter" method="post" enctype="application/x-www-form-urlencoded" accept-charset="UTF-8">
<%-- Update:20260404 hidden の id を属性値としてクォート（パラメータ欠落・解釈ずれ防止） --%>
<input type="hidden" name="id" value="<%= id != null ? id : "" %>">
<%-- Update:20260404 更新失敗時の再編集用に直前の本文を保持（HTML 属性用エスケープ） --%>
<input type="text" name="text" value="<% if (textVal != null) { %><%= textVal.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;") %><% } %>">
<input type="submit" value="編集">
<%-- Update:20260404 form を明示終了（未閉鎖によるブラウザ依存の挙動不良防止） --%>
</form>
</body>
</html>