package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.DeleteMutterLogic;

/**
 * Servlet implementation class DeleteMutter
 */
@WebServlet("/DeleteMutter")
public class DeleteMutter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエストパラメーターからIDを取得
		// リクエストパラメータの取得
	    request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		System.out.println(id);

		//DeleteMutterLogicインスタンスの生成
		DeleteMutterLogic delMutterLogic = new DeleteMutterLogic();

		//DeleteMutterLogicの処理を実行
		delMutterLogic.execute(id);

		// リダイレクト
		response.sendRedirect("/dokoTsubu/Main");

	}

}
