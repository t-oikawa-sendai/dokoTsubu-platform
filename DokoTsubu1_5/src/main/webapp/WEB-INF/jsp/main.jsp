<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>

<h1>どこつぶメイン</h1>

<p>
<c:out value="${loginUser.name}" />さん、ログイン中
<br>
<a href="Logout">ログアウト</a>
</p>

<p>
<a href="Main">更新</a>
</p>

<!-- 検索フォーム -->
<form action="SearchMutter" method="get">
<input type="text" name="keyword">
<input type="submit" value="検索">
</form>

<!-- つぶやきフォーム -->
<form action="Main" method="post">
<input type="text" name="text">
<input type="submit" value="つぶやく">
</form>

<!-- つぶやき表示 -->
<c:if test="${not empty errorMsg}">
<p><c:out value="${errorMsg}" /></p>
</c:if>

<c:forEach var="mutter" items="${mutterList}">
<p>
<c:out value="${mutter.userName}" /> :
<c:out value="${mutter.text}" />
<br>

<!-- つぶやき編集ボタン -->
<a href="UpdateMutter?id=${mutter.id}">編集</a>

<!-- つぶやき削除ボタン -->
<a href="DeleteMutter?id=${mutter.id}">削除</a>

</p>
</c:forEach>

</body>
</html>