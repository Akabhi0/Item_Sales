package com.example.item_sales.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.item_sales.R;
import com.example.item_sales.adapter.ViewPagerAdapter;
import com.example.item_sales.basicutality.BasicConstant;
import com.example.item_sales.callbacks.CallingFragment;
import com.example.item_sales.callbacks.CallingItemFragment;
import com.example.item_sales.databinding.FragmentSubMainBinding;
import com.example.item_sales.model.CustomerItemDataBase;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SubMainFragment extends Fragment implements CallingFragment, CallingItemFragment {

    public static String TAG = SubMainFragment.class.getName();
    private FragmentSubMainBinding binding;
    private ViewPagerAdapter adapter;
    private Context context;
    private CallingFragment callingFragment;
    private CallingItemFragment callingItemFragment;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        callingFragment = (CallingFragment) context;
        callingItemFragment = (CallingItemFragment) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    getActivity().finish();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_main, container, false);
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PartialFragment(), context.getResources().getString(R.string.partial_paid));
        adapter.addFragment(new PaidFragment(), context.getResources().getString(R.string.fully_paid));
        binding.viewPager.setAdapter(adapter);
        binding.tlTablayout.setupWithViewPager(binding.viewPager);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void callingItemFragment() {
        callingItemFragment.callingItemFragment();
    }

    @Override
    public void callingFragment(List<CustomerItemDataBase> customerItemDataBases) {
        callingFragment.callingFragment(customerItemDataBases);
    }
}
