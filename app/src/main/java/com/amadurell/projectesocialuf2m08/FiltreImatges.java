package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class FiltreImatges extends HomeFragment {

    @Override
    Query getQuery()
    {
        return FirebaseFirestore.getInstance().collection("posts").whereEqualTo("mediaType","image");


    }
}