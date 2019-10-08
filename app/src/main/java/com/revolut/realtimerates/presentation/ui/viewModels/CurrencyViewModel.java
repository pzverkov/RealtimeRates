package com.revolut.realtimerates.presentation.ui.viewModels;

import com.revolut.realtimerates.domain.repository.Repository;
import com.revolut.realtimerates.domain.data.model.CurrencyDTO;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import com.revolut.realtimerates.util.OnCallResponse;
import com.revolut.realtimerates.util.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CurrencyViewModel extends ViewModel implements OnCallResponse, OnViewModel {
    private List<CurrencyItem> data;
    private MutableLiveData<List<CurrencyItem>> liveData = new MutableLiveData<>();
    private ObservableField<String> baseCurrency = new ObservableField<>();
    private ObservableFloat multiplier = new ObservableFloat(1);
    private Disposable disposable;
    private Repository repository = Repository.getInstance();

    public CurrencyViewModel() {
        disposable = repository.getCurrencyItemObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::setConverterData, throwable -> {
                    // TODO: handle error properly in UI
                });
    }

    public MutableLiveData<List<CurrencyItem>> getLiveData() {
        return liveData;
    }

    public List<CurrencyItem> getData() {
        return data;
    }

    public void setBaseCurrency(String baseCurrency) {
        Logger.e(CurrencyViewModel.class.getCanonicalName(), "setBase currency: " + baseCurrency);

        this.baseCurrency.set(baseCurrency);
        repository.startSync(baseCurrency);
    }

    public ObservableField<String> getBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public void setMultiplier(Float multiplier) {
        this.multiplier.set(multiplier);
    }

    public ObservableFloat getMultiplier() {
        return multiplier;
    }

    @Override
    public void setConverterData(CurrencyDTO parsedData) {
        if (parsedData == null) {
            return;
        }
        Logger.e(CurrencyViewModel.class.getCanonicalName(), "setConverterData. Base: " +
                parsedData.getBaseCurrency() + " Currencies: " + parsedData.getRates().size() + ". Data amount = " + data.size());

        data.clear();
        data.add(new CurrencyItem(parsedData.getBaseCurrency(), 1.0f));
        Iterator it = parsedData.getRates().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            data.add(new CurrencyItem((String) pair.getKey(), (float) pair.getValue()));
            it.remove();
        }
        liveData.postValue(data);
    }

    public List<CurrencyItem> getStoredValues() {
        try {
            data = repository.getDefaultData();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        liveData.postValue(data);
        if (data.size() > 0) {
            baseCurrency.set(data.get(0).getDescription());
        }
        Logger.i("getStoredValues", "Loaded size: " + data.size());
        return data;
    }

    public void refresh() {
        repository.startSync(baseCurrency.get());
    }

    public void pause() {
        repository.stopSync();
        Logger.i("CurrencyViewModel", "Trying to add data : " + data.size() + " items");
        repository.storeItems(data);
    }

    @Override
    protected void onCleared() {
        repository.close();
    }
}