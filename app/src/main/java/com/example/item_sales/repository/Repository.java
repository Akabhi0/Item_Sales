package com.example.item_sales.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.item_sales.database.Dao;
import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.database.CustomerDataBaseFile;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Repository {
    private CustomerDataBaseFile customerDataBaseFile;
    private Dao dao;
    private CustomerDataBase customerDataBase;
    private LiveData<List<CustomerDataBase>> customerDataBaseMutableLiveData = new MutableLiveData<>();

    public Repository(@Nullable Application application, CustomerDataBase customerDataBase) {
        customerDataBaseFile = CustomerDataBaseFile.getcustomerDatabse(application);
        dao = customerDataBaseFile.getDao();
        this.customerDataBase = customerDataBase;
    }

    public void setUserData() {
        new InsertUserData(dao).execute(customerDataBase);
    }

    //This is the class is for inserting the user data into the roomdatabase
    public class InsertUserData extends AsyncTask<CustomerDataBase, Void, Void> {
        private Dao getDao;

        public InsertUserData(Dao dao) {
            getDao = dao;
        }

        @Override
        protected Void doInBackground(CustomerDataBase... customerDataBases) {
            getDao.insertCustomerData(customerDataBases[0]);
            return null;
        }
    }

}
