package app.divyanshu.firebaseauthentication;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ItemViewHolder> {

    Context context;
    List<UserModel> userModelList = new ArrayList<>();

    public UserDetailsAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.user_item,viewGroup,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ItemViewHolder itemViewHolder, int i) {

        itemViewHolder.email.setText("Email: "+userModelList.get(i).getEmail());
        itemViewHolder.name.setText("Name: "+userModelList.get(i).getName());
        itemViewHolder.number.setText("Number: "+userModelList.get(i).getNumber());
        itemViewHolder.gender.setText("Gender: "+userModelList.get(i).getGender());

        Glide.with(context).load(userModelList.get(i).getStoredImageName()).placeholder(R.drawable.select_image_register).error(R.drawable.select_image_register).into(itemViewHolder.imageShow);
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView email,name,number,gender;
        CircleImageView imageShow;
        public ItemViewHolder( View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.userEmail);
            name = itemView.findViewById(R.id.userName);
            number = itemView.findViewById(R.id.userNumber);
            gender = itemView.findViewById(R.id.userGender);
            imageShow = itemView.findViewById(R.id.imageShow);

        }
    }
}
