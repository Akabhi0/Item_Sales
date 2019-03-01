package com.example.item_sales.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.basicutality.ErrorLogs;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.repository.CustomerItemRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddItemDataFragmentViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> isClear = new MutableLiveData<>();
    private MutableLiveData<String> isEmpty = new MutableLiveData<>();
    private CustomerItem customerItem;
    private CustomerItemRepository repository;

    public AddItemDataFragmentViewModel(Application context, CustomerItem customerItem) {
        this.context = context;
        this.customerItem = customerItem;
        repository = new CustomerItemRepository(context, customerItem);
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public MutableLiveData<String> getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty.setValue(isEmpty);
    }

    public void isVaildItemDataCheck() {
        if (TextUtils.isEmpty(customerItem.getItemName()))
            setIsEmpty(ErrorLogs.EMPTY_NAME);
        else if (TextUtils.isEmpty(customerItem.getQuantity()))
            setIsEmpty(ErrorLogs.QUANTITY);
        else if (TextUtils.isEmpty(customerItem.getRate()))
            setIsEmpty(ErrorLogs.RATE);
        else
            isValid.setValue(true);
    }

    public LiveData<Boolean> resetData() {
        if (customerItem != null)
            isClear.setValue(true);
        return isClear;
    }

    public LiveData<List<CustomerItem>> getCustomerItemData() {
        return repository.getCustomerItem();
    }

    public void setUserItemData() {
        repository.setUserData();
    }

    public void deleteCustomerItem() {
        repository.deleteCustomerItem();
    }


}
