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

public class FiltreMultimedia extends Fragment {

//    Yo me llamo Ralph!!*sonido de boli intensifies*
        private NavController navController;
        //P12 Firebase Storage P7
        public AppViewModel appViewModel;

        public FiltreMultimedia() {
        }

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

            final boolean[] hihaquery = {false};
            final Query[] query = {null};

            //Establecer el Adaptador en el RecyclerView
            RecyclerView postsRecyclerView = view.findViewById(R.id.postsRecyclerView);



            FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                    .setQuery(getQuery(), Post.class)
                    .setLifecycleOwner(this)
                    .build();

            postsRecyclerView.setAdapter(new com.amadurell.projectesocialuf2m08.FiltreMultimedia.PostsAdapter(options));

            //P12 Firebase Storage P7
            appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        }

        // class PostsAdapter extends FirestoreRecyclerAdapter ...


        Query getQuery()
        {
            return FirebaseFirestore.getInstance().collection("posts").whereEqualTo("mediaType","image")
                    .whereEqualTo("mediaType","video")
                    .limit(50);


            }



        //Clase PostsAdapter

        class PostsAdapter extends FirestoreRecyclerAdapter<Post, com.amadurell.projectesocialuf2m08.FiltreMultimedia.PostsAdapter.PostViewHolder> {
            public PostsAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
                super(options);
            }

            @NonNull
            @Override
            public com.amadurell.projectesocialuf2m08.FiltreMultimedia.PostsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new com.amadurell.projectesocialuf2m08.FiltreMultimedia.PostsAdapter.PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_post, parent, false));
            }


            @Override
            protected void onBindViewHolder(@NonNull com.amadurell.projectesocialuf2m08.FiltreMultimedia.PostsAdapter.PostViewHolder holder, int position, @NonNull final Post post) {
                Glide.with(getContext()).load(post.authorPhotoUrl).circleCrop().into(holder.authorPhotoImageView);
                holder.authorTextView.setText(post.author);
                holder.contentTextView.setText(post.content);

                // Gestion de likes

                //3. Gestion de likes
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
                //P12 Firebase Storage P7
                // Miniatura de media
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


            //1.Gestió de Likes
            class PostViewHolder extends RecyclerView.ViewHolder {
                ImageView authorPhotoImageView, likeImageView, mediaImageView;
                TextView authorTextView, contentTextView, numLikesTextView;
                PostViewHolder(@NonNull View itemView) {
                    super(itemView);
                    likeImageView = itemView.findViewById(R.id.likeImageView);
                    authorPhotoImageView = itemView.findViewById(R.id.photoImageView);
                    mediaImageView = itemView.findViewById(R.id.mediaImage);
                    authorTextView = itemView.findViewById(R.id.authorTextView);
                    contentTextView = itemView.findViewById(R.id.contentTextView);
                    numLikesTextView = itemView.findViewById(R.id.numLikesTextView);
                }
            }


        }

    }
