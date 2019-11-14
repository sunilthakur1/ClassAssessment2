package com.example.classassessment2;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.classassessment2.model.User;


import java.util.ArrayList;
import java.util.List;
public class userRvAdapter extends RecyclerView.Adapter<userRvAdapter.userHolder> {
    List<User> users = new ArrayList<>();
    private Context context;
    private OnUserClickListener onUserClickListener;

    public userRvAdapter(List<User> users, Context context, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public userHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        userHolder userHolder = new userHolder(view, onUserClickListener);
        return userHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull userHolder holder, int position) {
        final User user = users.get(position);
        holder.tvName.setText(user.getName());
        holder.imageView.setImageResource(user.getImg());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView tvName;
        OnUserClickListener onUserClickListener;

        public userHolder(@NonNull View itemView, OnUserClickListener onUserClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.userImg);
            tvName = itemView.findViewById(R.id.userName);

            this.onUserClickListener = onUserClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserClickListener.onUserClickListener(getAdapterPosition());
        }
    }

    public interface OnUserClickListener{
        void onUserClickListener(int position);
    }
}
