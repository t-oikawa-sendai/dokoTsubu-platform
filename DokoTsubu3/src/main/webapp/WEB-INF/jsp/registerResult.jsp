<%--
Program Name: registerResult.jsp
Language: JSP
Function: User registration completion screen (SCR-003)
Created: 2026-09-13
Last Updated: 2026-09-13
Author: Takashi Oikawa
AI: Cursor Grok 4.6
Memo: Phase 1 DokoTsubu3. Success view only.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録完了</title>
</head>
<body>
<h1>ユーザー登録完了しました。</h1>
<p style="padding-bottom:10px;"><a href="<%= request.getContextPath() %>/Login">top画面に戻る</a></p>
</body>
</html>
