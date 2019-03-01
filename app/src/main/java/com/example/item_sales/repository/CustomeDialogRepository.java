package com.example.item_sales.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.item_sales.database.CustomerDataBaseFile;
import com.example.item_sales.database.Dao;
import com.example.item_sales.model.CustomerDataBase;

import androidx.annotation.Nullable;

public class CustomeDialogRepository {
    private CustomerDataBaseFile customerDataBaseFile;
    private Dao dao;

    public CustomeDialogRepository(@Nullable Application application) {
        customerDataBaseFile = CustomerDataBaseFile.getcustomerDatabse(application);
        dao = customerDataBaseFile.getDao();
    }

    public void getAllUpdatedData(int id_customer, double balance_amount_customer) {
        CustomerDataBase customerDataBase = new CustomerDataBase();
        customerDataBase.setBalance_amount_customer(balance_amount_customer);
        customerDataBase.setId_customer(id_customer);
        new UpdateUserData(dao).execute(customerDataBase);
    }

    //This is the class is for inserting the user data into the roomdatabase
    public class UpdateUserData extends AsyncTask<CustomerDataBase, Void, Void> {
        private Dao getDao;

        public UpdateUserData(Dao dao) {
            getDao = dao;
        }

        @Override
        protected Void doInBackground(CustomerDataBase... customerDataBases) {
            getDao.getAllUpdatedData(customerDataBases[0].getId_customer(),
                    customerDataBases[0].getBalance_amount_customer());
            return null;
        }

    }
}
