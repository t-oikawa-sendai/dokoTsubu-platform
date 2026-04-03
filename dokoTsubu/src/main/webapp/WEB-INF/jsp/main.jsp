<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" %>
<%@ page import="model.User,model.Mutter,java.util.List" %>
<%
// セッションスコープに保存されたユーザー情報を取得
User loginUser = (User) session.getAttribute("loginUser");
// アプリケーションスコープに保存されたつぶやきリストを取得
List<Mutter> mutterList = (List<Mutter>) request.getAttribute("mutterList");
// リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String) request.getAttribute("errorMsg");
// Update:20260403 Main から渡された AI 一言（投稿直後の forward のみ存在）
String aiMsg = (String) request.getAttribute("aiMsg");
%>
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
      <p class="dt-hero__lead">つぶやき投稿・閲覧</p>
    </header>

    <main class="dt-main dt-main--wider">
      <section class="dt-card dt-card--pad-sm" aria-label="ユーザー情報と操作">
        <div class="dt-row dt-row--between dt-row--wrap">
          <p class="dt-text dt-text--muted">
            <span class="dt-strong"><%= loginUser.getName() %></span> さん、ログイン中
          </p>
          <nav class="dt-navlinks" aria-label="操作リンク">
            <a class="dt-link" href="Main">更新</a>
            <a class="dt-link" href="Logout">ログアウト</a>
          </nav>
        </div>
      </section>

      <section class="dt-split">
        <section class="dt-card" aria-label="検索">
          <h2 class="dt-card__title">検索</h2>
          <form action="<%= request.getContextPath() %>/SearchMutter" method="get" class="dt-form dt-form--compact">
            <div class="dt-field">
              <label class="dt-field__label" for="dt-keyword">キーワード</label>
              <input id="dt-keyword" class="dt-field__input" type="text" name="keyword">
            </div>
            <div class="dt-actions">
              <input class="dt-btn dt-btn--secondary" type="submit" value="検索">
            </div>
          </form>
        </section>

        <section class="dt-card" aria-label="投稿">
          <h2 class="dt-card__title">投稿</h2>
          <form action="<%= request.getContextPath() %>/Main" method="post" class="dt-form dt-form--compact">
            <div class="dt-field">
              <label class="dt-field__label" for="dt-text">つぶやき</label>
              <input id="dt-text" class="dt-field__input" type="text" name="text">
            </div>
            <div class="dt-actions">
              <input class="dt-btn dt-btn--primary" type="submit" value="つぶやく">
            </div>
          </form>
        </section>
      </section>

      <% if(errorMsg != null){ %>
        <p class="dt-error" role="alert"><%= errorMsg %></p>
      <% } %>

      <%-- Update:20260403 aiMsg は投稿 POST 後の画面表示時のみセットされる --%>
      <% if(aiMsg != null){ %>
        <p class="dt-text dt-text--muted" role="status" aria-live="polite">AI：<%= aiMsg %></p>
      <% } %>

      <section class="dt-card" aria-label="投稿一覧">
        <h2 class="dt-card__title">投稿一覧</h2>

        <div class="dt-list">
          <% for(Mutter mutter : mutterList){%>
            <article class="dt-post" aria-label="投稿">
              <p class="dt-post__text">
                <span class="dt-post__user"><%=mutter.getUserName()%></span>
                <span class="dt-post__sep">：</span>
                <span class="dt-post__body"><%=mutter.getText()%></span>
              </p>
              <p class="dt-post__actions">
                <a class="dt-link" href="<%= request.getContextPath() %>/UpdateMutter?id=<%=mutter.getId()%>">編集</a>
                <a class="dt-link dt-link--danger" href="<%= request.getContextPath() %>/DeleteMutter?id=<%=mutter.getId()%>">削除</a>
              </p>
            </article>
          <% } %>
        </div>
      </section>
    </main>
  </div>
</body>
</html>
