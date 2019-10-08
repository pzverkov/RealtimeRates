package com.revolut.realtimerates.presentation.ui.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.revolut.realtimerates.R;
import com.revolut.realtimerates.RateApp;
import com.revolut.realtimerates.databinding.CurrencyFragmentBinding;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import com.revolut.realtimerates.presentation.ui.viewModels.CurrencyViewModel;
import com.revolut.realtimerates.util.Constants;
import com.revolut.realtimerates.util.CurrencyAdapter;
import com.revolut.realtimerates.util.NotificationUtil;
import com.revolut.realtimerates.util.OnUiProcessing;
import com.revolut.realtimerates.util.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CurrencyExchangeFragment extends Fragment implements OnUiProcessing {
    private CurrencyViewModel viewModel;
    private CurrencyFragmentBinding binding;
    private CurrencyAdapter adapter;

    static CurrencyExchangeFragment newInstance() {
        return new CurrencyExchangeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);
        adapter = new CurrencyAdapter(this, viewModel.getStoredValues(getActivity()), viewModel);

        binding = DataBindingUtil.inflate(inflater, R.layout.currency_fragment, container, false);
        binding.setViewModel(viewModel);
        binding.recyclerListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerListView.setAdapter(adapter);

        viewModel.getLiveData().observe(this, adapter::setItems);
        if (adapter.getItemCount() == 0) {
            NotificationUtil.showSnackBarNotification(binding.getRoot(), getActivity().getString(R.string.no_data));
        }
        initNetworkListener();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onPause() {
        viewModel.pause(getActivity().getApplicationContext());
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refresh();
    }

    @Override
    public void onCellClick(View view, CurrencyItem item) {
        if (viewModel.getData() == null || item.getDescription().equals(viewModel.getBaseCurrency().get())) {
            return;
        }

        int i = 1;
        int size = viewModel.getData().size();
        int movedIndex = -1;
        for (; i < size; i++) {
            if (viewModel.getData().get(i).getDescription().equals(item.getDescription())) {
                movedIndex = i;
                break;
            }
        }
        if (movedIndex == -1) {
            return;
        }
        viewModel.setBaseCurrency(item.getDescription());
        viewModel.setMultiplier(1.0f);
        viewModel.getData().remove(i);
        viewModel.getData().add(0, item);
        adapter.notifyItemMoved(movedIndex, 0);
        binding.recyclerListView.scrollToPosition(0);

        if (view instanceof EditText) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

        @Override
    public void onClick(View view) {
        onCellClick(view, (CurrencyItem) view.getTag());
    }

    @Override
    public boolean onTouchListener(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) onClick(view);
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count, CurrencyItem currencyItem) {
        Logger.e(CurrencyExchangeFragment.class.getCanonicalName(), "onTextChanged to " + s);
        if (!currencyItem.getDescription().equals(viewModel.getBaseCurrency().get())) return;

        if (s.length() == 0) {
            viewModel.setMultiplier(1f);
            if (!binding.recyclerListView.isComputingLayout()) {
                adapter.notifyDataSetChanged();
            }
            return;
        }

        String str = String.valueOf(s);
        str = str.replaceAll(Constants.REGEX_RULE, "");
        if (str.length() > 3 && str.contains(Constants.DIVIDER) && str.length() - str.indexOf(Constants.DIVIDER) > 3) {
            str = str.substring(0, str.indexOf(Constants.DIVIDER) + 3);
        }

        viewModel.setMultiplier(Float.valueOf(str));
        if (!binding.recyclerListView.isComputingLayout()) adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressWarnings("ConstantConditions")
    private void initNetworkListener() {
        try {
            MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            mainViewModel.getConnected().observe(this, connected -> {
                if (connected == null || !connected) {
                    viewModel.pause(getActivity());
                    if (getActivity() != null) {
                        NotificationUtil.showSnackBarNotification(binding.getRoot(), getActivity().getString(R.string.no_internet));
                    }
                    return;
                }
                viewModel.refresh();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
