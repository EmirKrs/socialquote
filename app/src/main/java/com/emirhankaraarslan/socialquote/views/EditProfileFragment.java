package com.emirhankaraarslan.socialquote.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emirhankaraarslan.socialquote.R;
import com.emirhankaraarslan.socialquote.databinding.FragmentEditProfileBinding;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;

    public EditProfileFragment() {
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
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MeowBottomNavigation bottomNavigation = getActivity().findViewById(R.id.bottomNavigation);
        bottomNavigation.setVisibility(View.INVISIBLE);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ProfileFragment());
                fragmentTransaction.commit();
                bottomNavigation.setVisibility(View.VISIBLE);
                bottomNavigation.show(3,true);
            }
        });

    }
}