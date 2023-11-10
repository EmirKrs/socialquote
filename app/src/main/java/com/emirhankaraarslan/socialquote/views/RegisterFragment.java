package com.emirhankaraarslan.socialquote.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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

        binding.registerUserPlain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });

        binding.registerEmailPlain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });

        binding.registerPasswordPlain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });

        binding.registerConfirmPlain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(view);
                }
            }
        });

        binding.registerCreateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    register(v);
                }
            });

    }
    public void register(View view){

        String username = binding.registerUserPlain.getText().toString();
        String email = binding.registerEmailPlain.getText().toString();
        String password = binding.registerPasswordPlain.getText().toString();
        String passwordCon = binding.registerConfirmPlain.getText().toString();

        if (username.equals("") || email.equals("") || password.equals("") || passwordCon.equals("")) {

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

                    binding.registerUserPlain.setText("");
                    binding.registerEmailPlain.setText("");
                    binding.registerPasswordPlain.setText("");
                    binding.registerConfirmPlain.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}