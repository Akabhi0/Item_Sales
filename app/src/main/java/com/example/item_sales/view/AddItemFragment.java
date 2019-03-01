package com.example.item_sales.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.item_sales.adapter.CustomCustomerAdapter;
import com.example.item_sales.basicutality.BasicConstant;
import com.example.item_sales.basicutality.ErrorLogs;
import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.R;
import com.example.item_sales.callbacks.CallingItemFragment;
import com.example.item_sales.databinding.FragmentAddItemBinding;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.viewmodel.AddItemViewModel;
import com.example.item_sales.viewmodel.AddItemViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddItemFragment extends Fragment {

    public static final String TAG = AddItemFragment.class.getName();
    private FragmentAddItemBinding binding;
    private CallingItemFragment callback;
    private AddItemViewModel viewModel;
    private AddItemViewModelFactory viewModelFactory;
    private CustomerDataBase model;
    private Context context;
    private CustomCustomerAdapter adapter;
    private LinearLayoutManager manager;
    final Calendar myCalendar = Calendar.getInstance();
    private List<CustomerItemDataBase> customerItems;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.callback = (CallingItemFragment) context;
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            customerItems = (List<CustomerItemDataBase>) bundle.getSerializable(BasicConstant.CUSTOMER_ITEM);
            binding.setIsEmpty(true);
        } else {
            customerItems = null;
        }

        model = new CustomerDataBase();
        viewModelFactory = new AddItemViewModelFactory(model, getActivity().getApplication(), customerItems);
        viewModel = ViewModelProviders.of(AddItemFragment.this, viewModelFactory).get(AddItemViewModel.class);
        binding.setAddItemViewModel(viewModel);
        binding.setCustomerDataBase(model);


        viewModel.getIsEmpty().observe(AddItemFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            }
        });


        viewModel.getIsValid().observe(AddItemFragment.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (model.getTotal_amount_customer() < Double.parseDouble(binding.acetAmount.getText().toString())) {
                        Toast.makeText(getContext(), ErrorLogs.VALID_AMOUNT, Toast.LENGTH_SHORT).show();
                    } else {
                        model.setBalance_amount_customer(model.getTotal_amount_customer() - Double.parseDouble(binding.acetAmount.getText().toString()));
                        viewModel.setUserData();
                        getFragmentManager().popBackStack(getFragmentManager().getBackStackEntryAt(1).getId(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            }
        });

        /**
         *This is the observer which is used to observe the customerItem data
         */
        viewModel.getCustomerItem().observe(AddItemFragment.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    adapter = new CustomCustomerAdapter(customerItems, getContext());
                    binding.recyclerItemAdd.setAdapter(adapter);
                    manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    binding.recyclerItemAdd.setLayoutManager(manager);
                }
            }
        });

        viewModel.getCalculationDone(customerItems);

        /**
         * This is the observer which is used to get all total User data
         */
        viewModel.getIsValueGet().observe(AddItemFragment.this, new Observer<CustomerDataBase>() {
            @Override
            public void onChanged(CustomerDataBase customerDataBase) {
                binding.acetName.setText(customerDataBase.getName_customer());
                binding.acetAmount.setText(String.valueOf(customerDataBase.getTotal_amount_customer()));
                binding.setQuantity(String.valueOf(customerDataBase.getQuantitiy()));
                binding.setTotalAmount(String.valueOf(customerDataBase.getTotal_amount_customer()));
            }
        });

        /**
         * This is the two observer for updating the data into database
         */
        viewModel.getIsEmptyInsert().observe(AddItemFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);

        binding.acetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callingItemFragment();
            }
        });
        View view = binding.getRoot();
        return view;
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CHINA);
        binding.acetDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
}
