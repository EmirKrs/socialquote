package com.emirhankaraarslan.socialquote.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.emirhankaraarslan.socialquote.R;
import com.emirhankaraarslan.socialquote.databinding.RecyclerRowProfileBinding;
import com.emirhankaraarslan.socialquote.model.ProfilePost;
import com.emirhankaraarslan.socialquote.views.DetailsFragment;
import com.emirhankaraarslan.socialquote.views.EditProfileFragment;

import java.util.ArrayList;

public class ProfilePostAdapter extends RecyclerView.Adapter<ProfilePostAdapter.ProfilePostHolder> {

    private ArrayList<ProfilePost> profilePostArrayList;

    public ProfilePostAdapter(ArrayList<ProfilePost> profilePostArrayList){
        this.profilePostArrayList = profilePostArrayList;
    }
    @NonNull
    @Override
    public ProfilePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowProfileBinding recyclerRowProfileBinding = RecyclerRowProfileBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProfilePostHolder(recyclerRowProfileBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostHolder holder, int position) {
        holder.recyclerRowProfileBinding.profileQuoteText.setText(profilePostArrayList.get(position).quote);
        holder.recyclerRowProfileBinding.profileAuthorText.setText(profilePostArrayList.get(position).author);
        holder.recyclerRowProfileBinding.profileBookText.setText(profilePostArrayList.get(position).book);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new DetailsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profilePostArrayList.size();
    }

    class ProfilePostHolder extends RecyclerView.ViewHolder{
        RecyclerRowProfileBinding recyclerRowProfileBinding;
        public ProfilePostHolder(RecyclerRowProfileBinding recyclerRowProfileBinding) {
            super(recyclerRowProfileBinding.getRoot());
            this.recyclerRowProfileBinding = recyclerRowProfileBinding;
        }

    }
}
