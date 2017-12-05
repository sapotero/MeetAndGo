package sapotero.meetandgo.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("created_at")
    @Expose
    private String date;

    @SerializedName("user_id")
    @Expose
    private String userId;

    public Comment(String body, String date, String userId) {
        this.body = body;
        this.date = date;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public Comment setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
