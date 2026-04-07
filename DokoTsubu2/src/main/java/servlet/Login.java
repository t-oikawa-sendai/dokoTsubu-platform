package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // Update:20260404 GET /Login 直叩き時に 404 相当を避け、ログイン入口へ誘導
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // リクエストパラメータの取得
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String pass = request.getParameter("pass");
    
    // ユーザー名・パスワードがどちらも入力されている場合
    if((name != null && name.length() != 0) && (pass != null && pass.length() != 0)) {
    	// 入力値でUserインスタンスの作成
    	User user = new User(name, pass);
    	LoginLogic loginLogic = new LoginLogic();
    	// DBに存在するかチェック（パスワードチェック込み）
    	User findUser = loginLogic.find(user);
    	
    	// ログイン処理
    	if(findUser != null) {
    		// ユーザー情報をセッションスコープに保存
    		HttpSession session = request.getSession();
    		session.setAttribute("loginUser", findUser);
    	} else {
    		request.setAttribute("errorMsg", "パスワードが間違っているか、ユーザーが未登録です。");
    	}    	
    }else {
    	// どちらかでも入力されてなければ、エラーメッセージ出力
    	request.setAttribute("errorMsg", "必要項目が未入力です。");
    }
    
    // Update:20260404 コンテキストルート基準の絶対パス（相対指定の解釈差で forward 失敗しないようにする）
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/loginResult.jsp");
    dispatcher.forward(request, response);
  }
}