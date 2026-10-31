<%--
Program Name: registerView.jsp
Language: JSP
Function: User registration screen (SCR-002)
Created: 2026-09-13
Last Updated: 2026-10-08
Author: Takashi Oikawa
AI: Cursor Grok 4.7
Memo: Phase 1 DokoTsubu3 SCR-002. Gender defaults to PRIVATE. Age feeling starts unselected.
--%>
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
  <div class="dt-page">
    <header class="dt-hero" aria-label="ページ見出し">
      <h1 class="dt-hero__title">ユーザー登録</h1>
      <p class="dt-hero__lead">必要事項を入力して登録してください。</p>
    </header>

    <main class="dt-main dt-main--wide">
      <section class="dt-card" aria-label="登録フォーム">
        <h2 class="dt-card__title">登録情報</h2>

        <% if(errorMsg != null){ %>
          <p class="dt-error" role="alert"><%= errorMsg %></p>
        <% } %>

        <form method="post" action="<%= request.getContextPath() %>/Register" class="dt-form">
          <div class="dt-field">
            <label class="dt-field__label" for="dt-username">ユーザー名</label>
            <input id="dt-username" class="dt-field__input" type="text" name="username" autocomplete="username">
          </div>

          <div class="dt-field">
            <label class="dt-field__label" for="dt-password">パスワード</label>
            <input id="dt-password" class="dt-field__input" type="password" name="password" autocomplete="new-password">
          </div>

          <div class="dt-field">
            <label class="dt-field__label" for="dt-gender">性別</label>
            <select id="dt-gender" class="dt-field__input" name="gender">
              <option value="MALE">男性</option>
              <option value="FEMALE">女性</option>
              <option value="OTHER">その他</option>
              <option value="PRIVATE" selected>ヒミツ</option>
            </select>
          </div>

          <div class="dt-field">
            <label class="dt-field__label" for="dt-age-feeling">年齢感覚</label>
            <select id="dt-age-feeling" class="dt-field__input" name="ageFeeling">
              <option value="" selected></option>
              <option value="YOUNG">若い</option>
              <option value="FAIRLY_YOUNG">そこそこ若い</option>
              <option value="MIDDLE">中年</option>
              <option value="PRE_SENIOR">高齢者の少し手前</option>
              <option value="SENIOR">がっつり高齢者</option>
            </select>
          </div>

          <div class="dt-actions">
            <input class="dt-btn dt-btn--primary" type="submit" value="登録">
          </div>
        </form>

        <p class="dt-help-links">
          <a class="dt-link" href="<%= request.getContextPath() %>/Login">TOPへ</a>
        </p>
      </section>
    </main>
  </div>
</body>
</html>
