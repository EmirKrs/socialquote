package com.emirhankaraarslan.socialquote.views;

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
import com.emirhankaraarslan.socialquote.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth auth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

            binding.registerCreateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    register(v);
                }
            });

    }
    public void register(View view){

        String userName = binding.registerUserPlain.getText().toString();
        String email = binding.registerEmailPlain.getText().toString();
        String password = binding.registerPasswordPlain.getText().toString();
        String passwordCon = binding.registerConfirmPlain.getText().toString();

        if (userName.equals("") || email.equals("") || password.equals("") || passwordCon.equals("")) {

            Toast.makeText(getActivity(), "Lütfen boş alan bırakmayınız", Toast.LENGTH_SHORT).show();

        }
        else if (!password.equals(passwordCon)) {

            Toast.makeText(getActivity(), "Parolayı doğru girdiğinizden emin olunuz", Toast.LENGTH_SHORT).show();

        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Toast.makeText(getActivity(), "Kayıt işlemi başarılı", Toast.LENGTH_SHORT).show();

                    NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment();
                    Navigation.findNavController(view).navigate(action);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}