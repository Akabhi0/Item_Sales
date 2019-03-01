package com.example.item_sales.database;

import android.app.Application;

import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.model.CustomerItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CustomerDataBase.class, CustomerItem.class}, version = 6)
@TypeConverters(TypeConverter.class)
public abstract class CustomerDataBaseFile extends RoomDatabase {

    private static CustomerDataBaseFile customerDataBase;
    private static final String DATABASE_NAME = "customerData_db";

    public abstract Dao getDao();

    public static CustomerDataBaseFile getcustomerDatabse(Application application) {
        if (customerDataBase == null) {
            customerDataBase = Room.databaseBuilder(application.getApplicationContext(),
                    CustomerDataBaseFile.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return customerDataBase;
    }

    //method to remove instance
    public static void closeDatabase() {
        customerDataBase = null;
    }
}
