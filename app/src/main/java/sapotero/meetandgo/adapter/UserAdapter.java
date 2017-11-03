package sapotero.meetandgo.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sapotero.meetandgo.R;
import sapotero.meetandgo.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>  {

  private ArrayList<User> users;

  public UserAdapter() {
    users = new ArrayList<>();
  }

  public void add(User user){
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
    final User user = users.get(position);

    viewHolder.email.setText( user.getEmail() );
    viewHolder.password.setText( user.getPassword() );
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView email;
    private final TextView password;

    public UserViewHolder(View itemView) {
      super(itemView);
      email    = (TextView) itemView.findViewById(R.id.email);
      password = (TextView) itemView.findViewById(R.id.password);
    }
  }
}
