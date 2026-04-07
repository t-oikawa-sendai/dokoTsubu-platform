package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.UpdateMutterLogic;

/**
 * Servlet implementation class UpdateMutter
 */
@WebServlet("/UpdateMutter")
public class UpdateMutter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Update:20260404 編集画面 HTML のキャッシュで古い form（GET 既定など）が残り POST にならない事象を防ぐ
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");

		//リクエストパラメーターからIDを取得
		String id = request.getParameter("id");

		//リクエストスコープにidを保存
		request.setAttribute("id", id);

		//編集画面にフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(
				"/WEB-INF/jsp/updateMutter.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメーターからIDとテキストを取得
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String text = request.getParameter("text");

		UpdateMutterLogic updMutter = new UpdateMutterLogic();
		boolean updated = false;
		try {
			updated = updMutter.execute(id, text);
		} catch (RuntimeException ex) {
			// Update:20260404 想定外の実行時例外のみここ（論理層は boolean で失敗を返す）
			ex.printStackTrace();
		}

		if (updated) {
			// Update:20260404 DB 更新成功時のみ一覧へ（URL を /Main の GET にする）
			response.sendRedirect(request.getContextPath() + "/Main");
		} else {
			// Update:20260404 失敗時は編集画面へ戻し、入力とエラーを表示
			request.setAttribute("id", id);
			request.setAttribute("text", text);
			request.setAttribute("errorMsg", "更新できませんでした。ID・本文・DB を確認してください。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/updateMutter.jsp");
			dispatcher.forward(request, response);
		}
	}

}
