package sapotero.meetandgo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sapotero.meetandgo.R;
import sapotero.meetandgo.model.Comment;
import timber.log.Timber;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>  {

  private final Context context;
  private ArrayList<Comment> comments;
  private HashMap<String, Comment> hash = new HashMap<>();

  public CommentAdapter(Context context) {
    this.context = context;
    comments = new ArrayList<>();
  }

  public void add(Comment comment){
    if (!hash.containsKey(comment.hash())){
      Timber.e("hash %s", comment.hash());
      comments.add(comment);
      hash.put(comment.hash(), comment);
      notifyDataSetChanged();
    }
  }

  public void clear(){
    comments.clear();
    notifyDataSetChanged();
  }

  @Override
  public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item_layout, parent, false);
    return new CommentViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CommentViewHolder viewHolder, int position) {
    final Comment comment = comments.get(position);

    viewHolder.date.setText( comment.getDate() );
    viewHolder.body.setText( comment.getBody() );
  }

  @Override
  public int getItemCount() {
    return comments.size();
  }

  public class CommentViewHolder extends RecyclerView.ViewHolder {
    private final TextView date;
    private final TextView body;

    public CommentViewHolder(View itemView) {
      super(itemView);
      date = (TextView) itemView.findViewById(R.id.date);
      body = (TextView) itemView.findViewById(R.id.body);
    }
  }
}
