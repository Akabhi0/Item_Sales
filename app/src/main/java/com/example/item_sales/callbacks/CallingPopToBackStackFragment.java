package com.example.item_sales.callbacks;

import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;

import java.util.List;

public interface CallingPopToBackStackFragment {
    void callingPriviousFragment(List<CustomerItemDataBase> customerItems);
}
