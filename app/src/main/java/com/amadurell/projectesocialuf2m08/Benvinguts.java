package com.amadurell.projectesocialuf2m08;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.lifecycle.*;
import androidx.navigation.*;

import com.amadurell.projectesocialuf2m08.databinding.BenvingutsBinding;
import com.bumptech.glide.Glide;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Benvinguts extends Fragment {

    NavController navController;
    com.amadurell.projectesocialuf2m08.Arranque arranque;
    private BenvingutsBinding binding;
    Executor executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = BenvingutsBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arranque = new ViewModelProvider(requireActivity()).get(com.amadurell.projectesocialuf2m08.Arranque.class);
        navController = Navigation.findNavController(view);

//        Glide.with(Benvinguts.this).load(R.drawable.loading).into(binding.signInProgressBar);

        arranque.finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                navController.navigate(R.id.action_benvinguts_to_usuaris);
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // simular la carga de recursos

                    Thread.sleep(3000);
                    arranque.finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}