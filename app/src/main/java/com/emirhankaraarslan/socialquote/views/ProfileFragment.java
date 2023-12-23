package com.emirhankaraarslan.socialquote.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emirhankaraarslan.socialquote.R;
import com.emirhankaraarslan.socialquote.adapter.PostAdapter;
import com.emirhankaraarslan.socialquote.adapter.ProfilePostAdapter;
import com.emirhankaraarslan.socialquote.databinding.FragmentProfileBinding;
import com.emirhankaraarslan.socialquote.model.Post;
import com.emirhankaraarslan.socialquote.model.ProfilePost;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<ProfilePost> profilePostArrayList;
    private ProfilePostAdapter profilePostAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        profilePostArrayList = new ArrayList<>();

        MeowBottomNavigation bottomNavigation = requireActivity().findViewById(R.id.bottomNavigation);
        bottomNavigation.setVisibility(View.VISIBLE);
        bottomNavigation.show(3,true);


        getData();

        binding.profileRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        profilePostAdapter = new ProfilePostAdapter(profilePostArrayList);
        binding.profileRecyclerView.setAdapter(profilePostAdapter);

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new EditProfileFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false).commit();
                auth.signOut();

                Intent intentToMain = new Intent(requireActivity(), MainActivity.class);
                startActivity(intentToMain);
                requireActivity().finish();
            }
        });
    }

    public void getData(){

        // Profile Info Process
        String userId = auth.getCurrentUser().getUid();
        firebaseFirestore.collection("Profiles").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String username = documentSnapshot.getString("username");
                    String biography = documentSnapshot.getString("biography");
                    String downloadUrl = documentSnapshot.getString("downloadurl");

                    binding.nameTextProfile.setText(username);
                    binding.bioText.setText(biography);
                    Picasso.get().load(downloadUrl).into(binding.photoProfile);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


        //Profile Post Process
        firebaseFirestore.collection("Posts").whereEqualTo("userId", userId).orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){

                    for (DocumentSnapshot document : value.getDocuments()){

                        Map<String, Object> data = document.getData();

                        String quote = (String) data.get("quote");
                        String author = (String) data.get("author");
                        String book = (String) data.get("book");

                        ProfilePost profilePost = new ProfilePost(quote, author, book);

                        profilePostArrayList.add(profilePost);
                    }
                    profilePostAdapter.notifyDataSetChanged();
                }

            }
        });



    }
    
    
}