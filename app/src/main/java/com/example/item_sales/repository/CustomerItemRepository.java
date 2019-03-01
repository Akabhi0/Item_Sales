package com.example.item_sales.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.item_sales.database.CustomerDataBaseFile;
import com.example.item_sales.database.Dao;
import com.example.item_sales.model.CustomerItem;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CustomerItemRepository {
    private CustomerDataBaseFile customerDataBaseFile;
    private Dao dao;
    private CustomerItem customerItem;
    private LiveData<List<CustomerItem>> customerItemBaseMutableLiveData = new MutableLiveData<>();

    public CustomerItemRepository(@Nullable Application application, CustomerItem customerItem) {
        customerDataBaseFile = CustomerDataBaseFile.getcustomerDatabse(application);
        dao = customerDataBaseFile.getDao();
        this.customerItem = customerItem;
    }

    public LiveData<List<CustomerItem>> getCustomerItem() {
        customerItemBaseMutableLiveData = dao.getAllCustomerItem();
        return customerItemBaseMutableLiveData;
    }

    public void setUserData() {
        new InsertUserItemData(dao).execute(customerItem);
    }

    public void deleteCustomerItem() {
        new DeleteUserItemData(dao).execute();
    }

    //This is the class is for inserting the user data into the roomdatabase
    public class InsertUserItemData extends AsyncTask<CustomerItem, Void, Void> {
        private Dao getDao;

        public InsertUserItemData(Dao dao) {
            getDao = dao;
        }

        @Override
        protected Void doInBackground(CustomerItem... customerItems) {
            getDao.insertCustomerItem(customerItems[0]);
            return null;
        }
    }

    //This is the class is used to delete the customer item from the dataBase
    public class DeleteUserItemData extends AsyncTask<Void, Void, Void> {
        private Dao getDao;

        public DeleteUserItemData(Dao dao) {
            getDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getDao.deleteAllCustomerItem();
            return null;
        }
    }
}
