package com.amadurell.projectesocialuf2m08;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItunesViewModel extends AndroidViewModel {

    MutableLiveData<Itunes.Result> respuestaMutableLiveData = new MutableLiveData<>();

    public ItunesViewModel(@NonNull Application application) {
        super(application);
    }

    public void buscar(String texto){
        Itunes.api.buscar().enqueue(new Callback<Itunes.Result>() {
            @Override
            public void onResponse(@NonNull Call<Itunes.Result> call, @NonNull Response<Itunes.Result> response) {
                respuestaMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Itunes.Result> call, @NonNull Throwable t) {}
        });
    }
}