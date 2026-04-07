package model;

import dao.UserDAO;

public class RegisterUserLogic {
	// 変更理由: DAOが「成功/重複/DBエラー」を返すため、上位へ結果コードを伝播する
	// ユーザー情報の登録
    public int execute(User user) {
        UserDAO dao = new UserDAO();
        // ユーザー登録処理
        return dao.registerUser(user);
    }
}