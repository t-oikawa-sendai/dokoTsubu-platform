package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.RegisterUserLogic;
import model.User;

@WebServlet("/Register")
public class Register extends HttpServlet {
// doGetはサーブレットクラスでのmainメソッドにあたるので最初に実行される    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 登録フォームを表示する
    	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registerView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
        // フォームデータを取得し、バリデーションを行う
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // ユーザー名・パスワードがどちらも入力されている場合
        if((username != null && username.length() != 0) && (password != null && password.length() != 0)) {
        	
        	System.out.println("正常処理に入る");
        	// ユーザーを作成し、データベースに保存する
            User user = new User(username, password);
            RegisterUserLogic registerUserLogic = new RegisterUserLogic();
            int result = registerUserLogic.execute(user);
            
            // 登録できた場合
            if(result == UserDAO.REGISTER_OK) {
            	// 登録完了画面にフォワード
            	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registerResult.jsp");
                dispatcher.forward(request, response);
            } else {
            	// 変更理由: 重複（UNIQUE制約違反）とそれ以外のDBエラーを分岐表示する
            	if (result == UserDAO.REGISTER_DUPLICATE) {
            		request.setAttribute("errorMsg", "ユーザー名が既に存在します。");
            	} else {
            		// エラーメッセージをリクエストスコープに保存
            		request.setAttribute("errorMsg", "登録できませんでした。最初からやり直してください。");
            	}
                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registerView.jsp");
                dispatcher.forward(request, response);
            }
        } else {
        	// どちらかでも入力されてなければ、エラーメッセージ出力
        	request.setAttribute("errorMsg", "必要項目が未入力です。");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/registerView.jsp");
            dispatcher.forward(request, response);         
        }
    }
   
}
