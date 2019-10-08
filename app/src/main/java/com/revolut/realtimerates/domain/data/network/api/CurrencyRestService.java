package com.revolut.realtimerates.domain.data.network.api;

import com.revolut.realtimerates.domain.data.model.CurrencyDTO;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyRestService {
    String BASE_URL = "https://revolut.duckdns.org/";

    @GET("latest")
    Observable<CurrencyDTO> getLatestRate(@Query("base") String baseCurrency);
}