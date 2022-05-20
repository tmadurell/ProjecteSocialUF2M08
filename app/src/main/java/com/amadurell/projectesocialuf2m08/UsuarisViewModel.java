package com.amadurell.projectesocialuf2m08;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarisViewModel extends AndroidViewModel {

    MutableLiveData<UsuarisBBDD.Result> respuestaMutableLiveData = new MutableLiveData<>();

    public UsuarisViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscar(String texto){
        UsuarisBBDD.api.buscar().enqueue(new Callback<UsuarisBBDD.Result>() {
            @Override
            public void onResponse(@NonNull Call<UsuarisBBDD.Result> call, @NonNull Response<UsuarisBBDD.Result> response) {
                respuestaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<UsuarisBBDD.Result> call, @NonNull Throwable t) {}
        });
    }
}