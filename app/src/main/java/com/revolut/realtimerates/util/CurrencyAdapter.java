package com.revolut.realtimerates.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.revolut.realtimerates.R;
import com.revolut.realtimerates.databinding.CurrencyItemBinding;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import com.revolut.realtimerates.presentation.ui.viewModels.OnViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    private List<CurrencyItem> items;
    private OnUiProcessing handler;
    private OnViewModel viewModel;

    public CurrencyAdapter(OnUiProcessing handler, List<CurrencyItem> items, OnViewModel viewModel) {
        this.items = items;
        this.handler = handler;
        this.viewModel = viewModel;
    }

    public final void setItems(List<CurrencyItem> currencyItems) {
        List<CurrencyItem> items = new ArrayList<>();
        for (CurrencyItem currencyItem : currencyItems) {
            items.add(currencyItem.clone());
        }

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CurrencyComparisonUtil(items, getItems()));
        diffResult.dispatchUpdatesTo(this);
        this.items = items;
    }

    public List<CurrencyItem> getItems() {
        return items;
    }

    @NonNull
    public final CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CurrencyItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.currency_item, parent, false);
        return new CurrencyViewHolder(binding);
    }

    @Override
    public final void onBindViewHolder(@NonNull CurrencyViewHolder holder, final int position) {
        holder.bind(position);
    }

    @Override
    public final int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        CurrencyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            ((CurrencyItemBinding) binding).valueEdittext.setTag(items.get(position));

            binding.setVariable(com.revolut.realtimerates.BR.value, items.get(position).getValue());
            binding.setVariable(com.revolut.realtimerates.BR.model, items.get(position));
            binding.setVariable(com.revolut.realtimerates.BR.handler, handler);
            binding.setVariable(com.revolut.realtimerates.BR.mainViewModel, viewModel);
            binding.setVariable(com.revolut.realtimerates.BR.map, Constants.KNOWN_CURRENCIES);
            binding.executePendingBindings();
        }

        void bindValue(float value) {
            binding.setVariable(com.revolut.realtimerates.BR.value, value);
            binding.executePendingBindings();
        }
    }

    @Override
    public final void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
            return;
        }
        CurrencyItem item = this.items.get(position);
        Bundle args = (Bundle) payloads.get(0);
        if (args.keySet().contains(CurrencyComparisonUtil.KEY_CODE)) {
            holder.bind(position);
            return;
        }
        holder.bindValue(args.getFloat(CurrencyComparisonUtil.KEY_VALUE));
    }
}