package com.example.item_sales.viewmodel;

import android.app.Application;
import android.content.Context;

import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.repository.PartialPaidRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PartialFragmentViewModel extends ViewModel {
    private Context context;
    private PartialPaidRepository repository;

    PartialFragmentViewModel(Application context) {
        this.context = context;
        repository = new PartialPaidRepository(context);
    }

    public LiveData<List<CustomerDataBase>> getCustomerBaseData() {
        return repository.getCustomerPartialData();
    }

    public void setDeletedDataBase(int id) {
        repository.setDeletedDataBase(id);
    }
}
