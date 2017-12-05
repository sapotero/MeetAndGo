package sapotero.meetandgo.model.wrapper;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sapotero.meetandgo.model.Comment;

public class CommentWrapper {
    @SerializedName("comment")
    @Expose
    private Comment comment;

    public CommentWrapper(Comment comment) {
        this.comment = comment;
    }
}
