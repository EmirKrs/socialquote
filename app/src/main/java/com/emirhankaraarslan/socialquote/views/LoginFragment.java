package com.emirhankaraarslan.socialquote.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.emirhankaraarslan.socialquote.R;
import com.emirhankaraarslan.socialquote.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FragmentLoginBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user != null){

            Intent intentToHome = new Intent(getActivity(), HomeActivity.class);
            startActivity(intentToHome);
            getActivity().finish();
        }
            binding.loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    login(view);

                }
            });


        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    private void login(View view){

        String email = binding.emailPlain.getText().toString();
        String password = binding.passwordPlain.getText().toString();

        if (email.equals("")||password.equals("")){

            Toast.makeText(getActivity(), "Lütfen boş alan bırakmayınız", Toast.LENGTH_SHORT).show();
        }
        else {
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intentToHome = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intentToHome);
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

}