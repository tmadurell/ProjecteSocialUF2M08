package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class FiltreMultimedia extends HomeFragment {

//    Yo me llamo Ralph!!*sonido de boli intensifies*
        private NavController navController;
        //P12 Firebase Storage P7




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_filtremultimedia, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            navController = Navigation.findNavController(view);
            view.findViewById(R.id.gotoNewPostFragmentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.newPostFragment);
                }
            });

            view.findViewById(R.id.filtreimatges).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    navController.navigate(R.id.imatges);

                }
            });
            view.findViewById(R.id.filtrevideos).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    navController.navigate(R.id.videos);

                }
            });
            view.findViewById(R.id.filtreaudios).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    navController.navigate(R.id.audio);

                }
            });



        }

        // class PostsAdapter extends FirestoreRecyclerAdapter ...


        Query getQuery()
        {
            
            //Esto es God Tier ya que filtra muchas cosas
            return FirebaseFirestore.getInstance().collection("posts").whereIn("mediaType", Arrays.asList("video", "audio","image"));

            }





    }
