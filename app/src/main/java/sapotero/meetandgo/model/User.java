package sapotero.meetandgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

  @SerializedName("id")
  @Expose
  private String id;

  @SerializedName("email")
  @Expose
  private String email;

  @SerializedName("password")
  @Expose
  private String password;

  public User(String id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return String.format( "[%s] %s : %s", id, email, password );
  }
}
