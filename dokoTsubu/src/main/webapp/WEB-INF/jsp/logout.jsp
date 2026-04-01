<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">DokoTsubu Ver.2.0（Platform Version)</h1>
      <p class="dt-hero__lead">ログアウト</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="ログアウト結果">
        <h2 class="dt-card__title">ログアウト</h2>
        <p class="dt-text">ログアウトしました</p>
        <p class="dt-help-links">
          <a class="dt-link" href="index.jsp">トップへ</a>
        </p>
      </section>
    </main>
  </div>
</body>
</html>