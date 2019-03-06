package com.example.item_sales.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import com.example.item_sales.basicutality.ErrorLogs;
import com.example.item_sales.repository.CustomeDialogRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomDialogeViewModel extends ViewModel {
    private int id;
    private double balamceAmount;
    private String doubleBalanceAmount;
    private MutableLiveData<String> isEmpty = new MutableLiveData<>();
    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private CustomeDialogRepository repository;

    public CustomDialogeViewModel(double balamceAmount, int id, Application context, String doubleBalanceAmount) {
        repository = new CustomeDialogRepository(context);
        this.id = id;
        this.balamceAmount = balamceAmount;
        this.doubleBalanceAmount = doubleBalanceAmount;
    }

    public MutableLiveData<String> getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty.setValue(isEmpty);
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid.setValue(isValid);
    }

    public void onValid() {
        if (Double.parseDouble(doubleBalanceAmount) > balamceAmount) {
            setIsEmpty(ErrorLogs.VALID_AMOUNT);
        } else if (TextUtils.isEmpty(doubleBalanceAmount)) {
            setIsEmpty(ErrorLogs.VALID_AMOUNT);
        } else if (Double.parseDouble(doubleBalanceAmount) <= balamceAmount) {
            balamceAmount = balamceAmount - Double.parseDouble(doubleBalanceAmount);
            setIsValid(true);
        }
    }

    public void updateCustomerData() {
        repository.getAllUpdatedData(id, balamceAmount);
    }
}
