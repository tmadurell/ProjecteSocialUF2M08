package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amadurell.projectesocialuf2m08.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        FirebaseFirestore.getInstance().setFirestoreSettings(new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build());



//        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.profileFragment,
                R.id.signOutFragment
        )
                .setOpenableLayout(drawer)
                .build();

        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, mAppBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            if(destination.getId() == R.id.benvinguts
                    || destination.getId() == R.id.usuaris
                    || destination.getId()  == R.id.signInFragment
                    || destination.getId()  == R.id.registerFragment
                    || destination.getId()  == R.id.profileFragment
                    //|| destination.getId()  == R.id.felicitats

            ){
                binding.bottomNavView.setVisibility(View.GONE);
                binding.navView.setVisibility(View.GONE);
                binding.toolbar.setVisibility(View.GONE);

            } else {
                binding.bottomNavView.setVisibility(View.VISIBLE);
                binding.navView.setVisibility(View.VISIBLE);
                binding.toolbar.setVisibility(View.VISIBLE);
            }
            if (destination.getId() == R.id.recyclerBusquedaFragment){
                binding.searchView.setVisibility(View.VISIBLE);
                binding.searchView.setIconified(false);
                binding.searchView.requestFocusFromTouch();
            } else {
                binding.searchView.setVisibility(View.GONE);
            }

        });

        View header = navigationView.getHeaderView(0);
        final ImageView photo = header.findViewById(R.id.photoImageView);
        final TextView name = header.findViewById(R.id.displayNameTextView);
        final TextView email = header.findViewById(R.id.emailTextView);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //Importante hacer cambio
                if (user != null) {
                    if (user.getPhotoUrl() != null) {
                        Glide.with(MainActivity.this)
                                .load(user.getPhotoUrl().toString())
                                .circleCrop()
                                .into(photo);
                    }
                    //Importante hacer cambio
                    if (user.getDisplayName() != null && user.getEmail() != null) {

                        name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}