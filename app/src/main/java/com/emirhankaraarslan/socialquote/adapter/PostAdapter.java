package com.emirhankaraarslan.socialquote.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emirhankaraarslan.socialquote.databinding.RecyclerRowHomeBinding;
import com.emirhankaraarslan.socialquote.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postArrayList;
    private Context context;

    public PostAdapter(ArrayList<Post> postArrayList, Context context){
        this.postArrayList = postArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowHomeBinding recyclerRowHomeBinding = RecyclerRowHomeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowHomeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowHomeBinding.homeUsername.setText(postArrayList.get(position).username);
        holder.recyclerRowHomeBinding.homeQuoteText.setText(postArrayList.get(position).quote);
        holder.recyclerRowHomeBinding.homeAuthorText.setText(postArrayList.get(position).author);
        holder.recyclerRowHomeBinding.homeBookText.setText(postArrayList.get(position).book);

        Picasso.get().load(postArrayList.get(position).imageUrl).into(holder.recyclerRowHomeBinding.homeRecyclerRowPhoto);

        holder.recyclerRowHomeBinding.homeRecyclerRowPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
    }
    public void showCustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Profil");
        builder.setMessage("Profil Bilgileri");
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowHomeBinding recyclerRowHomeBinding;
        public PostHolder(RecyclerRowHomeBinding recyclerRowHomeBinding) {
            super(recyclerRowHomeBinding.getRoot());
            this.recyclerRowHomeBinding = recyclerRowHomeBinding;
        }
    }


}
