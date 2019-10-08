package com.revolut.realtimerates.domain.repository;

import android.content.Context;

import com.revolut.realtimerates.domain.data.model.CurrencyDTO;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import com.revolut.realtimerates.domain.room.DatabaseManager;
import com.revolut.realtimerates.domain.data.network.wrapper.NetworkWrapper;
import com.revolut.realtimerates.domain.data.network.api.CurrencyRestService;
import com.revolut.realtimerates.util.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Repository implements Closeable {
    private static Repository instance;
    private Disposable syncDisposable;
    private CurrencyRestService converterService;
    private PublishSubject<CurrencyDTO> currencyItemPublishSubject = PublishSubject.create();

    private Repository() {
        converterService = NetworkWrapper.getInstance().getCurrencyService();
    }

    public static Repository getInstance() {
        if (instance == null) instance = new Repository();
        return instance;
    }

    public Observable<CurrencyDTO> getCurrencyItemObservable() {
        return currencyItemPublishSubject.hide();
    }

    public final void startSync(String baseCurrency) {
        stopSync();
        syncDisposable = converterService.getLatestRate(baseCurrency)
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(converterData -> {
                    currencyItemPublishSubject.onNext(converterData);
                    Logger.i(Repository.class.getCanonicalName(), "Data: " + converterData.toString());
                }, throwable -> {
                    Logger.i(Repository.class.getCanonicalName(), "Error conversionData " + throwable.getMessage());
                    startSync(baseCurrency);
                });
    }

    public final void stopSync() {
        if (syncDisposable != null && !syncDisposable.isDisposed()) syncDisposable.dispose();
    }

    public final void storeItems(@NotNull Context context, List<CurrencyItem> items) {
        Executors.newSingleThreadExecutor().submit(() -> DatabaseManager.getInstance(context).getAppDatabase().currencyDao().insertList(items));
    }

    @Override
    public void close() {
        stopSync();
    }

    public List<CurrencyItem> getDefaultData(Context context) throws ExecutionException, InterruptedException {
        Callable<List<CurrencyItem>> callable = () -> DatabaseManager.getInstance(context).getAppDatabase().currencyDao().getAll();
        Future<List<CurrencyItem>> future = Executors.newSingleThreadExecutor().submit(callable);
        return future.get();
    }
}