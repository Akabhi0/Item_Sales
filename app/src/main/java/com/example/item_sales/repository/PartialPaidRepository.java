package com.example.item_sales.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.item_sales.database.CustomerDataBaseFile;
import com.example.item_sales.database.Dao;
import com.example.item_sales.model.CustomerDataBase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PartialPaidRepository {
    private CustomerDataBaseFile dataBaseFile;
    private Dao dao;
    private Context context;
    private LiveData<List<CustomerDataBase>> customerLiveData = new MutableLiveData<>();
    private CustomerDataBase customerDataBase;
    private int id;

    public PartialPaidRepository(Application context) {
        dataBaseFile = CustomerDataBaseFile.getcustomerDatabse(context);
        this.dao = dataBaseFile.getDao();
    }

    public LiveData<List<CustomerDataBase>> getCustomerPartialData() {
        customerLiveData = dao.getAllPartialCustomer();
        return customerLiveData;
    }

    public LiveData<List<CustomerDataBase>> getCustomerPaidData() {
        customerLiveData = dao.getAllPaidCustomer();
        return customerLiveData;
    }

    public void setDeletedDataBase(int id) {
        this.id = id;
        new DeleteDataBase(dao).execute();
    }

    public class DeleteDataBase extends AsyncTask<Void, Void, Void> {
        private Dao getDao;

        public DeleteDataBase(Dao dao) {
            getDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getDao.deleteAllCustomerData(id);
            return null;
        }
    }
}
