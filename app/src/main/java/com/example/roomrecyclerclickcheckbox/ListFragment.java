package com.example.roomrecyclerclickcheckbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.roomrecyclerclickcheckbox.model.DataItem;
import com.example.roomrecyclerclickcheckbox.model.DataViewModel;


public class ListFragment extends Fragment {

    private DataViewModel viewModel;
    private List<DataItem> mDataItemList;
    private ListRecyclerViewAdapter mListAdapter;
    private OnListFragmentInteractionListener mListener;
    private Context context;

    public ListFragment() {
    }

    public void setListData(List<DataItem> dataItemList) {
        //if data changed, set new list to adapter of recyclerview
        if (mDataItemList == null) {
            mDataItemList = new ArrayList<>();
        }
        mDataItemList.clear();
        mDataItemList.addAll(dataItemList);

        if (mListAdapter != null) {
            mListAdapter.setListData(dataItemList);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        context = view.getContext();
        //set recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mListAdapter = new ListRecyclerViewAdapter(mListener);

        if (mDataItemList != null) {
            mListAdapter.setListData(mDataItemList);
        }
        recyclerView.setAdapter(mListAdapter);

        //Add new item to db
        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();
            }
        });


        //Delete item from db
        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<mListAdapter.numberCheckedItemList.size(); i++) {
                    viewModel.deleteItemById (mListAdapter.numberCheckedItemList.get(i));
                    mListAdapter.checked  = mListAdapter.getChecked();
                }
                mListAdapter.numberCheckedItemList.clear();
            }
        });

        return view;
    }


    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.add_contact_layout, null);

        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);
        final EditText phoneField = (EditText) subView.findViewById(R.id.enter_phone);
        final EditText detailsField = (EditText) subView.findViewById(R.id.enter_details);
        final EditText cityField = (EditText) subView.findViewById(R.id.enter_city);


        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add new CONTACT");
        builder.getContext();
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameField.getText().toString();
                String phone = phoneField.getText().toString();
                String details = detailsField.getText().toString();
                String city = cityField.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getContext(), "Something went wroncontextg. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    viewModel.insertItem(new DataItem(name, phone, details, city));
                    //finish();
                    //startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get viewmodel
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        //bind to Livedata
        viewModel.getAllData().observe(this, new Observer<List<DataItem>>() {
            @Override
            public void onChanged(@Nullable List<DataItem> dataItems) {
                if (dataItems != null) {
                    setListData(dataItems);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        //onClick items of list
        void onListClickItem(DataItem dataItem);

        //onClick delte item from list
        void onListFragmentDeleteItemById(long itemId);
    }

}
