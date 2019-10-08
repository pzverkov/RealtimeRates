package com.revolut.realtimerates.domain.data.network.wrapper;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.revolut.realtimerates.domain.data.network.api.CurrencyRestService;
import com.revolut.realtimerates.util.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkWrapper {
  private static NetworkWrapper instance;
  private Retrofit retrofit;

  private NetworkWrapper() {
    retrofit = new Retrofit.Builder()
        .baseUrl(CurrencyRestService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().
                registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
            try {
                return new SimpleDateFormat(Constants.YYYY_MM_DD, Locale.ENGLISH).parse(json.getAsString());
            } catch (ParseException e) {
                return null;
            }}).setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  public static NetworkWrapper getInstance() {
    if (instance == null) {
        instance = new NetworkWrapper();
    }
    return instance;
  }

  public CurrencyRestService getCurrencyService() {
    return retrofit.create(CurrencyRestService.class);
  }
}