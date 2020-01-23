package com.sinhro.angles;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
//    private TextView mTextMessage;

//    private FrameLayout frameLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_settings:
                    replaceFragment(SettingsFragment.newInstance());
                    return true;
                case R.id.navigation_rotationToolHolder:
                    replaceFragment(RotationToolHolderFragment.newInstance());
                    return true;
                case R.id.navigation_arctg:
                    replaceFragment(ArctgFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        frameLayout = findViewById(R.id.fragmentContainer);
        BottomNavigationView navigationView = findViewById(R.id.navigation);



        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigationView.setSelectedItemId(R.id.navigation_rotationToolHolder);
//        addFragment(RotationToolHolderFragment.newInstance());
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, newFragment).commit();//R.id.fragment_containerのを代える
    }

    private void addFragment(Fragment newFragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, newFragment).commit();
    }

}
