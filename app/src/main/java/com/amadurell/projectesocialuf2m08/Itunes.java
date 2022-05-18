package com.amadurell.projectesocialuf2m08;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Itunes {

    class Result {
        List<Pokemon> documents;
    }

    class Pokemon {
        String name;
        Fields fields;
        String createTime;
        String updateTime;
    }

    class Fields{
        StringValue nombre, elemento, imagen;
    }

    class StringValue{
        String stringValue;
    }

    public static Api api = new Retrofit.Builder()
            //.baseUrl("https://itunes.apple.com/")
            .baseUrl("https://firestore.googleapis.com/v1/projects/pokemones-1d7c6/databases/(default)/documents/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("Pokemones/")
        Call<Result> buscar(/*@Query("term") String texto*/);
    }
}
