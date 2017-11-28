package sapotero.meetandgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserApi implements Serializable{

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("password")
  @Expose
  private String password;
  @SerializedName("phone")
  @Expose
  private String phone;
  @SerializedName("info")
  @Expose
  private String info;
  @SerializedName("created_at")
  @Expose
  private String createdAt;
  @SerializedName("updated_at")
  @Expose
  private String updatedAt;
  public void setId(Integer id) {
    this.id = id;
  }

  @SerializedName("url")
  @Expose
  private String url;






  @SerializedName("comments")
  @Expose
  private List<Comment> commentList;

  public List<Comment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<Comment> commentList) {
    this.commentList = commentList;
  }

  public UserApi(Integer id, String email, String password, String phone, String info, String createdAt, String updatedAt, String url, List<Comment> commentList) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.info = info;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.url = url;
    this.commentList = commentList;
  }

  public UserApi(String email, String password, String phone, Integer id) {
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.id = id;
  }

  public Integer getId() {
    return id;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}