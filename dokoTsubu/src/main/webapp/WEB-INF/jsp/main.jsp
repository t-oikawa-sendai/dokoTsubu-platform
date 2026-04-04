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
// Update:20260404 タイプライター表示用（隠し要素内のテキストノード向け HTML エスケープ）
String aiMsgHtmlSafe = null;
if (aiMsg != null) {
  aiMsgHtmlSafe =
      aiMsg.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<% if (aiMsg != null) { %>
<style>
/* Update:20260404 AI 回答エリア（本 JSP のみ。style.css は未変更） */
.dt-ai-comment {
  margin: 0.75rem 0 1rem;
  padding: 0.85rem 1.1rem;
  border-radius: 12px;
  background: #1c1c1c;
  color: #fff8e7;
  font-size: 1.15rem;
  line-height: 1.55;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
.dt-ai-comment__label {
  font-weight: 700;
  color: #fff3d4;
  margin-right: 0.15em;
}
.dt-ai-comment__out {
  word-break: break-word;
}
.dt-ai-comment__src {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}
</style>
<% } %>
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

      <%-- Update:20260404 aiMsg は投稿 POST 後の画面表示時のみ。見た目・1文字ずつ表示のみ本段で変更 --%>
      <% if(aiMsg != null){ %>
      <div class="dt-ai-comment" role="status" aria-live="polite" aria-atomic="true">
        <span class="dt-ai-comment__label">AI：</span><span id="dt-ai-msg-out" class="dt-ai-comment__out"></span>
      </div>
      <span id="dt-ai-msg-full" class="dt-ai-comment__src" aria-hidden="true"><%= aiMsgHtmlSafe %></span>
      <script>
      /* Update:20260404 1 文字ずつ表示（style.css・Java サーブレットは未変更） */
      (function () {
        var fullEl = document.getElementById("dt-ai-msg-full");
        var outEl = document.getElementById("dt-ai-msg-out");
        if (!fullEl || !outEl) return;
        var full = fullEl.textContent || "";
        var i = 0;
        var delayMs = 42;
        function tick() {
          if (i > full.length) return;
          outEl.textContent = full.substring(0, i);
          i++;
          if (i <= full.length) {
            window.setTimeout(tick, delayMs);
          }
        }
        tick();
      })();
      </script>
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
