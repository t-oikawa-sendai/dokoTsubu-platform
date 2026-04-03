/*
ソース名: taskpane.js
Lang: JavaScript
Function: ダミー投稿配列の描画、デモ用ボタン、Office.onReady での起動
Note: DUMMY_POSTS は固定データ。renderPosts が一覧 DOM を組み立てる。認証・通信・シート操作なし。
Author:Takashi Oikawa
Date:2026/04/03
LastUp:2026/04/04
*/

(function () {
  "use strict";

  /** 固定ダミー（本来は API 等から取得する想定） */
  var DUMMY_POSTS = [
    {
      author: "山田太郎",
      postedAt: "2026-04-01 09:15",
      body: "今日の授業、Office アドインの構成がよく分かった。マニフェストと taskpane の関係がポイント。"
    },
    {
      author: "佐藤花子",
      postedAt: "2026-04-02 14:02",
      body: "HTML / CSS / JS だけで画面を作れるのは小さな Web アプリと同じ感覚で理解しやすい。"
    },
    {
      author: "鈴木一郎",
      postedAt: "2026-04-03 11:40",
      body: "Excel のセルには触らず、タスクペインだけで完結するデモなら説明の切り分けがしやすい。"
    },
    {
      author: "高橋みゆき",
      postedAt: "2026-04-03 16:55",
      body: "認証や DB は後から足すとして、まずは一覧 UI とデータの形を決めるのが良さそう。"
    },
    {
      author: "伊藤健",
      postedAt: "2026-04-04 08:30",
      body: "ボタンは見た目だけ。クリックしたらメッセージを出して「ここに処理を足す」と示せる。"
    }
  ];

  function buildPostCardHtml(post) {
    var author = escapeHtml(post.author);
    var postedAt = escapeHtml(post.postedAt);
    var body = escapeHtml(post.body);
    return (
      '<article class="post-card">' +
        '<div class="post-meta">' +
          '<p class="post-author">' + author + "</p>" +
          '<p class="post-time">' + postedAt + "</p>" +
        "</div>" +
        '<p class="post-body">' + body + "</p>" +
      "</article>"
    );
  }

  function escapeHtml(text) {
    var div = document.createElement("div");
    div.textContent = text;
    return div.innerHTML;
  }

  function renderPosts(posts) {
    var feed = document.getElementById("feed");
    var placeholder = document.getElementById("feed-placeholder");
    if (placeholder) {
      placeholder.remove();
    }
    var html = "";
    for (var i = 0; i < posts.length; i++) {
      html += buildPostCardHtml(posts[i]);
    }
    feed.innerHTML = html;
  }

  function onPostClick() {
    window.alert("デモ用: ここに投稿フォームや API 呼び出しを足す（今回は未実装）");
  }

  function onRefreshClick() {
    renderPosts(DUMMY_POSTS);
    window.alert("デモ用: ダミーデータを描画し直しました（通信・DB はしていません）");
  }

  function wireButtons() {
    var btnPost = document.getElementById("btn-post");
    var btnRefresh = document.getElementById("btn-refresh");
    if (btnPost) {
      btnPost.addEventListener("click", onPostClick);
    }
    if (btnRefresh) {
      btnRefresh.addEventListener("click", onRefreshClick);
    }
  }

  function startApp() {
    wireButtons();
    renderPosts(DUMMY_POSTS);
  }

  if (typeof Office !== "undefined" && Office.onReady) {
    Office.onReady(function () {
      startApp();
    });
  } else {
    document.addEventListener("DOMContentLoaded", startApp);
  }
})();
