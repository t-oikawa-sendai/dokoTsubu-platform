package model;
import java.io.Serializable;

public class User implements Serializable {
  private int id; // ID
  private String name; // ユーザー名
  private String pass; // パスワード

  public User() { }
  
  // 登録・存在チェック用コンストラクタ  
  public User(String name, String pass) {
	this.name = name;
	this.pass = pass;
  }
  
  //  フルデータ用コンストラクタ
  public User(int id, String name, String pass) {
	this.id = id;
	this.name = name;
	this.pass = pass;
  }
  
  public int getId() { return id; }
  public void setId(int id) { this.id = id;  }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getPass() { return pass; }
  public void setPass(String pass) { this.pass = pass; }
}