package model;

import dao.UserDAO;

public class LoginLogic {
  // ユーザー情報の存在チェック
  public User find(User user) {
      UserDAO dao = new UserDAO();
      
      return dao.findUser(user);     
  }
}