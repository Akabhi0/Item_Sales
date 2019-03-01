package com.example.item_sales.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.item_sales.adapter.CustomerItemAdapter;
import com.example.item_sales.callbacks.CallingFragment;
import com.example.item_sales.callbacks.CallingItemFragment;
import com.example.item_sales.callbacks.CallingItenData;
import com.example.item_sales.callbacks.CallingPopToBackStackFragment;
import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.R;
import com.example.item_sales.databinding.FragmentAddDataBinding;
import com.example.item_sales.model.CustomerItemDataBase;
import com.example.item_sales.viewmodel.AddItemDataFragmentViewModel;
import com.example.item_sales.viewmodel.AddItemDataFragmentViewModelFactroy;

import java.text.DecimalFormat;
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

public class AddItemDataFragment extends Fragment {

    public static final String TAG = AddItemDataFragment.class.getName();
    private FragmentAddDataBinding binding;
    private AddItemDataFragmentViewModel viewModel;
    private AddItemDataFragmentViewModelFactroy viewModelFactroy;
    private CustomerItem customerItem;
    private CustomerItemAdapter adapter;
    private LinearLayoutManager manager;
    private CallingFragment callback;
    private List<CustomerItemDataBase> customerItemDataBases;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CallingFragment) context;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setAddItemDataFragmentViewModel(viewModel);
        binding.setCustomerItem(customerItem);
        binding.setIsEmpty(true);
        /**
         *This is the observer for checking the error occur or not
         */
        viewModel.getIsEmpty().observe(AddItemDataFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * This is the observer who is responsible for setting the currect user id to the user as
         * well as the entering the data to the database
         */
        viewModel.getIsValid().observe(AddItemDataFragment.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    viewModel.setUserItemData();
                    /**
                     *This is the observer which is used to return the saved data from the customer item database
                     */
                    viewModel.getCustomerItemData().observe(AddItemDataFragment.this, new Observer<List<CustomerItem>>() {
                        @Override
                        public void onChanged(final List<CustomerItem> customerItems) {
                            if (customerItems != null) {
                                customerItemDataBases = new ArrayList<>();
                                for (int i = 0; i < customerItems.size(); i++) {
                                    CustomerItemDataBase customerItemDataBase = new CustomerItemDataBase();
                                    customerItemDataBase.setItemName(customerItems.get(i).getItemName());
                                    customerItemDataBase.setQuantity(customerItems.get(i).getQuantity());
                                    customerItemDataBase.setRate(customerItems.get(i).getRate());
                                    customerItemDataBase.setSgstAmount(customerItems.get(i).getSgstAmount());
                                    customerItemDataBase.setCgstAmount(customerItems.get(i).getCgstAmount());
                                    customerItemDataBase.setTotalAmount(customerItems.get(i).getTotalAmount());
                                    customerItemDataBases.add(customerItemDataBase);
                                }
                                adapter = new CustomerItemAdapter(getContext(), customerItems);
                                binding.recyclerItemAdd.setAdapter(adapter);
                                manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                binding.recyclerItemAdd.setLayoutManager(manager);
                                binding.setIsEmpty(false);
                            }
                        }
                    });
                }
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callingFragment(customerItemDataBases);
                viewModel.deleteCustomerItem();
            }
        });


        /**
         * This is the function is used for calculating the item details
         */
        RateAmount();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customerItem = new CustomerItem();
        viewModelFactroy = new AddItemDataFragmentViewModelFactroy(getActivity().getApplication(), customerItem);
        viewModel = ViewModelProviders.of(this, viewModelFactroy).get(AddItemDataFragmentViewModel.class);
        viewModel.deleteCustomerItem();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_data, container, false);
        View view = binding.getRoot();
        return view;
    }

    private void RateAmount() {

        binding.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence quantity, int start, int before, int count) {
                if (quantity.length() <= 0) {
                    customerItem.setQuantity("0");
                    customerItem.setRateAmount(0);
                    binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                    customerItem.setTotalAmount(0.0);
                    customerItem.setSgstAmount(0.0);
                    customerItem.setCgstAmount(0.0);
                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                } else {
                    customerItem.setQuantity(String.valueOf(quantity));
                    if (binding.rate.length() > 0) {
                        customerItem.setRateAmount(Double.parseDouble(customerItem.getQuantity()) * Double.parseDouble(customerItem.getRate()));
                        binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                        binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                        customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));

                        binding.sgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    customerItem.setSgstAmount((customerItem.getRateAmount() * 9) / 100);
                                    customerItem.setTotalAmount(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount());
                                    binding.sgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getSgstAmount())));
                                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                } else {
                                    customerItem.setSgstAmount(0);
                                    customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount()));
                                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                    binding.sgstAmount.setText(String.valueOf(customerItem.getSgstAmount()));
                                }
                            }
                        });

                        binding.cgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    customerItem.setCgstAmount((customerItem.getRateAmount() * 9) / 100);
                                    customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                    binding.cgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getCgstAmount())));
                                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                } else {
                                    customerItem.setCgstAmount(0);
                                    customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getSgstAmount()));
                                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                    binding.cgstAmount.setText(String.valueOf(customerItem.getCgstAmount()));
                                }
                            }
                        });
                    }
                    binding.rate.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence rate, int start, int before, int count) {
                            if (rate.length() <= 0) {
                                customerItem.setRate("0");
                                customerItem.setRateAmount(0);
                                binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                customerItem.setTotalAmount(0.0);
                                customerItem.setSgstAmount(0.0);
                                customerItem.setCgstAmount(0.0);
                                binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                            } else {
                                customerItem.setRate(String.valueOf(rate));
                                customerItem.setRateAmount(Double.parseDouble(customerItem.getQuantity()) * Double.parseDouble(customerItem.getRate()));
                                binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                binding.sgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            customerItem.setSgstAmount((customerItem.getRateAmount() * 9) / 100);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                            binding.sgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getSgstAmount())));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                        } else {
                                            customerItem.setSgstAmount(0);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount()));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                            binding.sgstAmount.setText(String.valueOf(customerItem.getSgstAmount()));
                                        }
                                    }
                                });

                                binding.cgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            customerItem.setCgstAmount((customerItem.getRateAmount() * 9) / 100);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                            binding.cgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getCgstAmount())));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                        } else {
                                            customerItem.setCgstAmount(0);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getSgstAmount()));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                            binding.cgstAmount.setText(String.valueOf(customerItem.getCgstAmount()));
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence rate, int start, int count, int after) {
                if (rate.length() <= 0) {
                    customerItem.setRate("0");
                    customerItem.setRateAmount(0);
                    binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                    customerItem.setTotalAmount(0.0);
                    customerItem.setSgstAmount(0.0);
                    customerItem.setCgstAmount(0.0);
                    binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                } else {
                    customerItem.setRate(String.valueOf(rate));
                    binding.quantity.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence quantity, int start, int before, int count) {
                            if (quantity.length() <= 0) {
                                customerItem.setQuantity("0");
                                customerItem.setRateAmount(0);
                                binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                customerItem.setTotalAmount(0.0);
                                customerItem.setSgstAmount(0.0);
                                customerItem.setCgstAmount(0.0);
                                binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                            } else {
                                customerItem.setQuantity(quantity.toString());
                                customerItem.setRateAmount(Double.parseDouble(quantity.toString()) * Double.parseDouble(customerItem.getRate()));
                                binding.rateAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getRateAmount())));
                                customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                binding.sgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            customerItem.setSgstAmount((customerItem.getRateAmount() * 9) / 100);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                            binding.sgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getSgstAmount())));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                        } else {
                                            customerItem.setSgstAmount(0);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount()));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                            binding.sgstAmount.setText(String.valueOf(customerItem.getSgstAmount()));
                                        }
                                    }
                                });

                                binding.cgstChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            customerItem.setCgstAmount((customerItem.getRateAmount() * 9) / 100);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() + customerItem.getCgstAmount() + customerItem.getSgstAmount()));
                                            binding.cgstAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getCgstAmount())));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                        } else {
                                            customerItem.setCgstAmount(0);
                                            customerItem.setTotalAmount(Math.floor(customerItem.getRateAmount() +  customerItem.getSgstAmount()));
                                            binding.totalAmount.setText(String.valueOf(new DecimalFormat("##.##").format(customerItem.getTotalAmount())));
                                            binding.cgstAmount.setText(String.valueOf(customerItem.getCgstAmount()));
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
