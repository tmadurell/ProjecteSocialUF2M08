package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Tendencies extends HomeFragment{

    @Override
    Query getQuery() {

      return FirebaseFirestore.getInstance().collection("posts").limit(50).orderBy("num_likes", Query.Direction.DESCENDING);
    }
}
