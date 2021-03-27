
public class Users {
private String Username;
private String Userpassword;


public Users(String Username,String Userpassword) {
	this.Username = Username;
	this.Userpassword = Userpassword;
	
}
public String getUsername() {
    return Username;
  }

  // Setter
  public void setUsername(String Username) {
    this.Username = Username;
  }
  public String getPassword() {
	    return Userpassword;
	  }

	  // Setter
	  public void setPassword(String Userpassword) {
	    this.Userpassword = Userpassword;
	  }


	
}
