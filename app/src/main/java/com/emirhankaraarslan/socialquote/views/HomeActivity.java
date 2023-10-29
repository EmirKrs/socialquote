package com.emirhankaraarslan.socialquote.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.emirhankaraarslan.socialquote.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    public MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        replace(new HomeFragment());
        bottomNavigation.show(1, true);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_35));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_add_35));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_profile_35));

        meowNavigation();
    }



    public void meowNavigation(){
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()){

                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new AddPostFragment());
                        break;

                    case 3:
                        replace(new ProfileFragment());
                        break;
                }



                return null;
            }
        });
    }

    public void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

    }
}