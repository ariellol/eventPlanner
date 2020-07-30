package com.LipStudio.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navBar;
    GuestManager guestManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navBar = findViewById(R.id.navBar);
        navBar.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navBar.setCheckedItem(R.id.home);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
         super.onBackPressed();
    }


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (menuItem.getItemId()){
                case R.id.guests:
                    transaction.replace(R.id.fragmentContainer, new GuestManager());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    navBar.setCheckedItem(R.id.guests);
                    break;
                case R.id.events:
                    transaction.replace(R.id.fragmentContainer, new EventManager());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    navBar.setCheckedItem(R.id.events);
                    break;
                case R.id.shopping:
                    transaction.replace(R.id.fragmentContainer, new ProductManager());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    navBar.setCheckedItem(R.id.shopping);
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }


}
