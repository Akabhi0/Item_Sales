package com.example.item_sales.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.item_sales.R;
import com.example.item_sales.adapter.PartiatPaidAdapter;
import com.example.item_sales.basicutality.BasicConstant;
import com.example.item_sales.callbacks.CallingFragment;
import com.example.item_sales.databinding.FragmentPaidBinding;
import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.viewmodel.PaidFragmentViewModel;
import com.example.item_sales.viewmodel.PaidFragmentViewModelFactory;
import com.example.item_sales.viewmodel.PartialFragmentViewModel;
import com.example.item_sales.viewmodel.PartialFragmentViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PaidFragment extends Fragment {
    private FragmentPaidBinding binding;
    private CallingFragment callingFragment;
    private PaidFragmentViewModel viewModel;
    private PaidFragmentViewModelFactory viewModelFactory;
    private PartiatPaidAdapter adapter;
    private Context context;
    private LinearLayoutManager manager;
    private List<CustomerItemDataBase> customerItemDataBases;
    private List<CustomerDataBase> customerDataBase = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callingFragment = (CallingFragment) getParentFragment();
            this.context = context;
        } catch (NullPointerException n) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_paid, container, false);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingFragment.callingFragment(customerItemDataBases);
            }
        });

        /**
         * This is for taking the real time text from edit text
         */
        binding.paidSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelFactory = new PaidFragmentViewModelFactory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(PaidFragment.this, viewModelFactory).get(PaidFragmentViewModel.class);

        viewModel.getCustomerBaseData().observe(PaidFragment.this, new Observer<List<CustomerDataBase>>() {
            @Override
            public void onChanged(List<CustomerDataBase> customerDataBases) {
                customerDataBase = customerDataBases;
                adapter = new PartiatPaidAdapter(customerDataBases, context);
                binding.paidRecycler.setAdapter(adapter);
                manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                binding.paidRecycler.setLayoutManager(manager);

                /**
                 * This listner is used for the deleting the element from the database
                 */
                adapter.setOnclickListner(new PartiatPaidAdapter.OnItemClicklistner() {
                    @Override
                    public void onPositionClicked(int id) {
                        viewModel.setDeletedDataBase(id);
                    }

                    @Override
                    public void onPositionClickedBalanceAmount(double balanceAmount, int id) {
                        Toast.makeText(context, BasicConstant.CUSTOMER_NOT_PERMIT, Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });


    }

    private void filter(String newText) {
        List<CustomerDataBase> list = new ArrayList<>();
        for (CustomerDataBase name : customerDataBase) {
            if (name.getName_customer().toLowerCase().contains(newText.toLowerCase())) {
                list.add(name);
            }
        }
        adapter.UpdateList(list);
    }

}
