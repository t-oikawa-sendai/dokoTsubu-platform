<%--JSPのコメント
DATE		: 2025-11-09
LASTUPDATE:2025-11-09
Author:Takashi Oikawa
Function:vaaaaa
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶへようこそ</h1>
<p style="padding-bottom:10px;"><a href="Register">ユーザー未登録の方はこちら</a></p>    <%--Register.java --%>
<form action="Login" method="post">
ユーザー名：<input type="text" name="name"><br>
パスワード：<input type="password" name="pass"><br>      <%--Login.java --%>
<input type="submit" value="ログイン">
</form>
</body>
</html>