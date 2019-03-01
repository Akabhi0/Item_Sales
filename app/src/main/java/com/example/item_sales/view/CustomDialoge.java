package com.example.item_sales.view;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.item_sales.R;
import com.example.item_sales.basicutality.BasicConstant;
import com.example.item_sales.basicutality.ErrorLogs;
import com.example.item_sales.databinding.CustomDialogBinding;
import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.viewmodel.CustomDialogeViewModel;
import com.example.item_sales.viewmodel.CustomerDialogeViewModelFactory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

@SuppressLint("ValidFragment")
public class CustomDialoge extends DialogFragment {
    private CustomDialogeViewModel viewModel;
    private CustomerDialogeViewModelFactory viewModelFactory;
    private double balancedAmount;
    private int id;
    private CustomDialogBinding binding;

    public CustomDialoge(int id, double balancedAmount) {
        this.id = id;
        this.balancedAmount = balancedAmount;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.custom_dialog, null, false);
        binding.setTitle(BasicConstant.CUSTOMER_CUSTOM_DIALOGE);
        binding.setDoubleBalanceAmount(balancedAmount);

        binding.amountFill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence amount, int start, int before, int count) {
                if (amount.length() > 0) {
                    viewModelFactory = new CustomerDialogeViewModelFactory(balancedAmount, id, getActivity().getApplication(), amount.toString());
                    viewModel = ViewModelProviders.of(CustomDialoge.this, viewModelFactory).get(CustomDialogeViewModel.class);
                    binding.setCustomDialoge(viewModel);

                    /**
                     * This is the observer when user write wrong amount
                     */
                    viewModel.getIsEmpty().observe(getParentFragment(), new Observer<String>() {
                        @Override
                        public void onChanged(String message) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });

                    /**
                     *this is the observer which is used to update the customerDataBase
                     */
                    viewModel.getIsValid().observe(CustomDialoge.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                viewModel.updateCustomerData();
                                dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), ErrorLogs.VALID_AMOUNT, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog_Alert)
                .setView(binding.getRoot())
                .create();
    }
}
