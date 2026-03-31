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

import model.Mutter;
import model.SearchMutterLogic;
import model.User;

/**
 * Servlet implementation class Search
 */
@WebServlet("/SearchMutter")
public class SearchMutter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメーター取得
		request.setCharacterEncoding("UTF-8");
		String keyword = request.getParameter("keyword");

		// つぶやきリストを取得して、リクエストスコープに保存
		SearchMutterLogic searchMutterLogic = new SearchMutterLogic();
		List<Mutter> mutterList = searchMutterLogic.execute(keyword);
		request.setAttribute("mutterList", mutterList);

		// ログインしているか確認するため
		// セッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) { // ログインしていない
			// リダイレクト
			response.sendRedirect("/dokoTsubu/");
		} else { // ログイン済み
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
		}
	}

}
