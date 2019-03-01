package com.example.item_sales.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.basicutality.ErrorLogs;
import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.repository.Repository;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddItemViewModel extends ViewModel {

    private CustomerDataBase customerDataBase;
    private Repository repository;
    private MutableLiveData<Boolean> isValid = new MutableLiveData<>();
    private MutableLiveData<String> isEmpty = new MutableLiveData<>();
    private MutableLiveData<String> isEmptyInsert = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCustomerItem = new MutableLiveData<>();
    private MutableLiveData<CustomerDataBase> isValueGet = new MutableLiveData<>();
    private List<CustomerItemDataBase> customerItems;
    private double totalAmount, balancedAmount;
    private int totalQuantity;

    public MutableLiveData<String> getIsEmpty() {
        return isEmpty;
    }

    public AddItemViewModel(CustomerDataBase customerDataBase, @Nullable Application application, List<CustomerItemDataBase> customerItems) {
        this.customerDataBase = customerDataBase;
        this.customerItems = customerItems;
        customerDataBase.setCustomerItems(this.customerItems);
        repository = new Repository(application, customerDataBase);
    }

    public LiveData<Boolean> getCustomerItem() {
        if (customerItems != null)
            isCustomerItem.setValue(true);
        return isCustomerItem;
    }

    public void isValid() {
        if (customerItems != null) {
            if (TextUtils.isEmpty(customerDataBase.getName_customer())) {
                setIsEmptyInsert(ErrorLogs.EMPTY_NAME);
            } else if (TextUtils.isEmpty(customerDataBase.getDate_customer())) {
                setIsEmptyInsert(ErrorLogs.DATE);
            } else if (customerItems.size() < 0) {
                setIsEmptyInsert(ErrorLogs.ADD_ITME);
            } else {
                isValid.setValue(true);
                setIsValid(isValid);
            }
        } else {
            setIsEmptyInsert(ErrorLogs.ADD_ITME);
        }
    }

    public void setUserData() {
        repository.setUserData();
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public void setIsValid(MutableLiveData<Boolean> isValid) {
        this.isValid = isValid;
    }


    public void getCalculationDone(List<CustomerItemDataBase> customerItems) {
        if (customerItems != null) {
            for (int i = 0; i < customerItems.size(); i++) {
                totalAmount = totalAmount + customerItems.get(i).getTotalAmount();
                totalQuantity = totalQuantity + Integer.parseInt(customerItems.get(i).getQuantity());
            }
        }
    }

    public LiveData<CustomerDataBase> getIsValueGet() {
        customerDataBase.setTotal_amount_customer(totalAmount);
        customerDataBase.setQuantitiy(totalQuantity);
        isValueGet.setValue(customerDataBase);
        return isValueGet;
    }

    public MutableLiveData<String> getIsEmptyInsert() {
        return isEmptyInsert;
    }

    public void setIsEmptyInsert(String isEmptyInsert) {
        this.isEmptyInsert.setValue(isEmptyInsert);
    }
}
