/*
 * Program	: Main Servlet（メイン画面・つぶやき投稿）
 * 機能概要	: ログイン済みユーザーのつぶやき一覧表示、投稿処理。投稿後は Gemini による一言コメントを request 属性 aiMsg に格納する（表示は main.jsp 側で後続対応）。
 * 動作条件	: セッションに loginUser が存在すること。投稿時は text パラメータが非空であること。AI 設定は /Users/takashioikawa/Dev/ai-config.json（AiConfigLoader 経由）。Java 21 / Tomcat 10 想定。
 * その他記載事項	: ai-config 読込・HTTP 通信は AiConfigLoader / GeminiApiClient に委譲し、本クラスには直書きしない。AI 失敗時も DB 投稿は成功のまま failureMessage 相当を aiMsg に載せる。
 * Update:20260403	doPost に loginUser == null の防御（未ログイン時は index.jsp へリダイレクトして終了）。ヘッダ Date/Author 表記を整理。
 * Date		: 2026/04/03
 * Author	: Takashi Oikawa
 */
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.AiConfigLoader;
import model.GeminiApiClient;
import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // つぶやきリストを取得して、リクエストスコープに保存
    GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
    List<Mutter> mutterList = getMutterListLogic.execute();
    request.setAttribute("mutterList", mutterList);

    // ログインしているか確認するため
    // セッションスコープからユーザー情報を取得
    HttpSession session = request.getSession();
    User loginUser = (User) session.getAttribute("loginUser");

    if (loginUser == null) { // ログインしていない
    // リダイレクト
      response.sendRedirect("index.jsp");
    } else { // ログイン済み
    // フォワード
      RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
      dispatcher.forward(request, response);
    }
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // リクエストパラメータの取得
    request.setCharacterEncoding("UTF-8");
    String text = request.getParameter("text");

    // 入力値チェック
    if (text != null && text.length() != 0) {
      // セッションスコープに保存されたユーザー情報を取得
      HttpSession session = request.getSession();
      User loginUser = (User) session.getAttribute("loginUser");

      // Update:20260403 未ログイン（セッション切れ等）の場合は投稿・AI 呼び出しを行わずログイン画面へ
      if (loginUser == null) {
        response.sendRedirect("index.jsp");
        return;
      }

      // ログイン中のユーザーIDとつぶやき情報でmutterインスタンスを生成し、DBに登録
      Mutter mutter = new Mutter(loginUser.getId(), text);
      PostMutterLogic postMutterLogic = new PostMutterLogic();
      postMutterLogic.execute(mutter);

      // 投稿試行後（既存フローと同じ分岐内）のみ Gemini 呼び出し。設定読込・HTTP は委譲クラスへ。
      try {
        AiConfigLoader aiConfigLoader = new AiConfigLoader();
        AiConfigLoader.AiConfig aiConfig =
            aiConfigLoader.loadOrFallback(AiConfigLoader.DEFAULT_CONFIG_PATH);
        GeminiApiClient geminiClient = new GeminiApiClient();
        String aiMsg = geminiClient.generateShortComment(text, aiConfig);
        request.setAttribute("aiMsg", aiMsg);
      } catch (RuntimeException ex) {
        // 想定外の例外でも投稿は完了済みのため、画面用メッセージのみ既定失敗文へ
        ex.printStackTrace();
        request.setAttribute("aiMsg", AiConfigLoader.DEFAULT_FAILURE_MESSAGE);
      }
    } else {
      // エラーメッセージをリクエストスコープに保存
      request.setAttribute("errorMsg", "つぶやきが入力されていません");
    }

    // つぶやきリストを取得して、リクエストスコープに保存
    GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
    List<Mutter> mutterList = getMutterListLogic.execute();
    request.setAttribute("mutterList", mutterList);

    // フォワード
    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
    dispatcher.forward(request, response);
  }
}
