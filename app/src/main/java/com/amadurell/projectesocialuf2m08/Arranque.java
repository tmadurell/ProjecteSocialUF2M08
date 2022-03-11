package com.amadurell.projectesocialuf2m08;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class Arranque extends AndroidViewModel {


    //imagen del insert
    MutableLiveData<Uri> imagenSeleccionada = new MutableLiveData<>();

    //del splash
    MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();

    public Arranque(@NonNull Application application) {
        super(application);

    }

    void establecerImagenSeleccionada(Uri uri) {
        imagenSeleccionada.setValue(uri);
    }




}
