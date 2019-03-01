package com.example.item_sales;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.item_sales.basicutality.BasicConstant;
import com.example.item_sales.callbacks.CallingFragment;
import com.example.item_sales.callbacks.CallingItemFragment;
import com.example.item_sales.callbacks.CallingPopToBackStackFragment;
import com.example.item_sales.databinding.ActivityMainBinding;
import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.view.AddItemDataFragment;
import com.example.item_sales.view.AddItemFragment;
import com.example.item_sales.view.PartialFragment;
import com.example.item_sales.view.SubMainFragment;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallingFragment, CallingItemFragment {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        SubMainFragment subMainFragment = new SubMainFragment();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.constainer, subMainFragment, SubMainFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void callingFragment(List<CustomerItemDataBase> customerItemDataBases) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BasicConstant.CUSTOMER_ITEM, (Serializable) customerItemDataBases);
        AddItemFragment addItemFragment = new AddItemFragment();
        addItemFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.constainer, addItemFragment, AddItemFragment.TAG)
                .addToBackStack(SubMainFragment.TAG)
                .commit();
    }

    @Override
    public void callingItemFragment() {
        AddItemDataFragment addItemFragment = new AddItemDataFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.constainer, addItemFragment, AddItemDataFragment.TAG)
                .addToBackStack(AddItemFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Log.e("count", "f" + getSupportFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }


}
