package sapotero.meetandgo.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sapotero.meetandgo.R;
import sapotero.meetandgo.activities.UserInfoActivity;
import sapotero.meetandgo.model.UserApi;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>  {

  private final Context context;
  private ArrayList<UserApi> users;

  public UserAdapter(Context context) {
    this.context = context;
    users = new ArrayList<>();
  }

  public void add(UserApi user){
    users.add(user);
    notifyDataSetChanged();
  }

  public void clear(){
    users.clear();
    notifyDataSetChanged();
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item_layout, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(UserViewHolder viewHolder, int position) {
    final UserApi user = users.get(position);

    viewHolder.email.setText( user.getEmail() );
    viewHolder.password.setText( user.getPassword() );

    viewHolder.wrapper.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", user.getId() );
        intent.putExtras(bundle);

        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView email;
    private final TextView password;
    private final CardView wrapper;

    public UserViewHolder(View itemView) {
      super(itemView);
      email    = (TextView) itemView.findViewById(R.id.email);
      password = (TextView) itemView.findViewById(R.id.password);
      wrapper  = (CardView) itemView.findViewById(R.id.wrapper);
    }
  }
}
