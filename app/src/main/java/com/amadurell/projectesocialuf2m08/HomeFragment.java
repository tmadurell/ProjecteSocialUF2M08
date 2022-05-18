package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

    NavController navController;
    public AppViewModel appViewModel;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*searchView = view.findViewById(R.id.search);
        searchView.setIconified(false);*/

        navController = Navigation.findNavController(view);  // <-----------------
        view.findViewById(R.id.gotoNewPostFragmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.newPostFragment);
            }
        });



        final boolean[] hihaquery = {false};
        final Query[] query = {null};


        RecyclerView postsRecyclerView = view.findViewById(R.id.postsRecyclerView);




        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(getQuery(), Post.class)
                .setLifecycleOwner(this)
                .build();

        postsRecyclerView.setAdapter(new PostsAdapter(options));

        appViewModel = new
                ViewModelProvider(requireActivity()).get(AppViewModel.class);

    }

    Query getQuery()
    {
        return FirebaseFirestore.getInstance().collection("posts").limit(50);
    }

    class PostsAdapter extends FirestoreRecyclerAdapter<Post, PostsAdapter.PostViewHolder> {
        public PostsAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
            super(options);
        }

        @NonNull
        @Override
        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_post, parent, false));
        }

        @Override
        protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull final Post post) {

            int photo;
            if (!post.authorPhotoUrl.equals("")) {
/*
                post.authorPhotoUrl="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.emmegi.co.uk%2Fcontact-emmegi-for-air-blast-oil-coolers%2Fuser-icon%2F&psig=AOvVaw2fyyUttZ99AkucX72Qb15O&ust=1645284021696000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPDDo-HGifYCFQAAAAAdAAAAABAc";
*/
/*
                Glide.with(getContext()).load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.emmegi.co.uk%2Fcontact-emmegi-for-air-blast-oil-coolers%2Fuser-icon%2F&psig=AOvVaw2fyyUttZ99AkucX72Qb15O&ust=1645284021696000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCPDDo-HGifYCFQAAAAAdAAAAABAc").circleCrop().into(holder.authorPhotoImageView);
*/
                Glide.with(getContext()).load(post.authorPhotoUrl).circleCrop().into(holder.authorPhotoImageView);

            }


            holder.contentTextView.setText(post.content);

            String currentDateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(post.currentTime);
            holder.dateTextView.setText(currentDateAndTime);
            // Gestion de likes
            final String postKey = getSnapshots().getSnapshot(position).getId();
            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (post.likes.containsKey(uid))
                holder.likeImageView.setImageResource(R.drawable.like_on);
            else
                holder.likeImageView.setImageResource(R.drawable.like_off);
            holder.numLikesTextView.setText(String.valueOf(post.likes.size()));
            holder.likeImageView.setOnClickListener(view -> {
                FirebaseFirestore.getInstance().collection("posts")
                        .document(postKey)
                        .update("likes." + uid, post.likes.containsKey(uid) ?
                                FieldValue.delete() : true);
            });


            holder.authorPhotoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            if (post.mediaUrl != null) {
                holder.mediaImageView.setVisibility(View.VISIBLE);
                if ("audio".equals(post.mediaType)) {
                    Glide.with(requireView()).load(R.drawable.audio).centerCrop().into(holder.mediaImageView);
                } else {
                    Glide.with(requireView()).load(post.mediaUrl).centerCrop().into(holder.mediaImageView);
                }
                holder.mediaImageView.setOnClickListener(view -> {
                    appViewModel.postSeleccionado.setValue(post);
                    navController.navigate(R.id.mediaFragment);
                });
            } else {
                holder.mediaImageView.setVisibility(View.GONE);
            }

        }

        class PostViewHolder extends RecyclerView.ViewHolder {
            ImageView authorPhotoImageView, likeImageView, mediaImageView;
            TextView authorTextView, contentTextView, numLikesTextView, dateTextView;

            PostViewHolder(@NonNull View itemView) {
                super(itemView);
                likeImageView = itemView.findViewById(R.id.likeImageView);
                authorPhotoImageView = itemView.findViewById(R.id.photoImageView);
                mediaImageView = itemView.findViewById(R.id.mediaImage);
                authorTextView = itemView.findViewById(R.id.authorTextView);
                contentTextView = itemView.findViewById(R.id.contentTextView);
                numLikesTextView = itemView.findViewById(R.id.numLikesTextView);
                dateTextView = itemView.findViewById(R.id.date);

            }
        }
    }



}