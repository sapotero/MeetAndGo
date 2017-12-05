package sapotero.meetandgo.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f2prateek.rx.preferences.RxSharedPreferences;

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

    String user = "";

    String[] strings = comment.getBody().split(":");

    if ( strings.length >= 2 ){



      if ( strings[0].contains( getCurrentUser() ) ){
        viewHolder.date.setTextColor( ContextCompat.getColor(context, R.color.colorAccent) );

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        lp.setMargins(16,16,16,16);
        lp.gravity = Gravity.END;
        viewHolder.card.setLayoutParams(lp);

      } else {


        viewHolder.date.setTextColor( ContextCompat.getColor(context, R.color.colorPrimaryDark) );


        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        lp.setMargins(16,16,16,16);
        lp.gravity = Gravity.START;
        viewHolder.card.setLayoutParams(lp);
      }
    }



  }

  @Override
  public int getItemCount() {
    return comments.size();
  }

  public class CommentViewHolder extends RecyclerView.ViewHolder {
    private final TextView date;
    private final TextView body;
    private final LinearLayout card;

    public CommentViewHolder(View itemView) {
      super(itemView);
      date = (TextView) itemView.findViewById(R.id.date);
      body = (TextView) itemView.findViewById(R.id.body);
      card = (LinearLayout) itemView.findViewById(R.id.cardItem);
    }
  }

  private String getCurrentUser(){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
    return rxPreferences.getString("current_user", "аноним").get();
  }
}
