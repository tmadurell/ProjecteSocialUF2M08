package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amadurell.projectesocialuf2m08.databinding.FragmentContrasenyaoblidadaBinding;
import com.bumptech.glide.Glide;


public class ContrasenyaOblidada extends Fragment {

    NavController navController;
    private FragmentContrasenyaoblidadaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentContrasenyaoblidadaBinding.inflate(inflater, container, false)).getRoot();    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        Glide.with(ContrasenyaOblidada.this).load(R.drawable.key).into(binding.clau);

        binding.Confirmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_contrasenyaOblidada_to_signInFragment);
            }

        });
        binding.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_contrasenyaOblidada_to_signInFragment);
            }

        });
    }
}
