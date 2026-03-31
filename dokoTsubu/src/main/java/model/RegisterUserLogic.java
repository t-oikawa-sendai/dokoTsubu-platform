package model;

import dao.UserDAO;

public class RegisterUserLogic {
	// ユーザー情報の登録
    public boolean execute(User user) {
        UserDAO dao = new UserDAO();
        // ユーザー登録処理
        return dao.registerUser(user);
    }
}