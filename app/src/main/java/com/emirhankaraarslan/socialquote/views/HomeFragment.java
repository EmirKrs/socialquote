package com.emirhankaraarslan.socialquote.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emirhankaraarslan.socialquote.adapter.PostAdapter;
import com.emirhankaraarslan.socialquote.databinding.FragmentHomeBinding;
import com.emirhankaraarslan.socialquote.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth auth;
    private FirebaseStorage firebaseStorage;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;

    public HomeFragment() {
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postArrayList = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        getData();

        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        postAdapter = new PostAdapter(postArrayList,getContext());
        binding.homeRecyclerView.setAdapter(postAdapter);
    }

    public void getData(){

        String userId = auth.getCurrentUser().getUid();

        //Home Fragment Photo
        firebaseFirestore.collection("Profiles").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    String downloadUrl = documentSnapshot.getString("downloadurl");
                    Picasso.get().load(downloadUrl).into(binding.photoHome);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //All Shared Posts
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){

                    for (DocumentSnapshot document : value.getDocuments()){

                        Map<String, Object> data = document.getData();

                        String username = (String) data.get("username");
                        String imageUrl = (String) data.get("downloadurl");
                        String quote = (String) data.get("quote");
                        String author = (String) data.get("author");
                        String book = (String) data.get("book");

                        Post post = new Post(username, imageUrl, quote, author, book);

                        postArrayList.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }
                
            }
        });

    }
}