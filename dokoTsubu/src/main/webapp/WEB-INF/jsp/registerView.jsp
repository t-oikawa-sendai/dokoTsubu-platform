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
</head>
<body>
<h1>ユーザー登録</h1>
    <form method="post" action="Register">
    ユーザー名：<input type="text" name="username"><br>
	パスワード：<input type="password" name="password"><br>
    <input type="submit" value="登録">
    </form>
<% if(errorMsg != null){ %>
<p style="color:red"><%= errorMsg %></p>
<% } %>
<a href="index.jsp">TOPへ</a>
</body>
</html>