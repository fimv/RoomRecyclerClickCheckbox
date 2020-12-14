package com.example.roomrecyclerclickcheckbox.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    private DataRepository mDataRepository;
    private LiveData<List<DataItem>> mListLiveData;

    public DataViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = new DataRepository((application));
        mListLiveData = mDataRepository.getAllData();
    }

    public LiveData<List<DataItem>> getAllData() {
        return mListLiveData;
    }

    public void insertItem(DataItem dataItem) {
        mDataRepository.insert(dataItem);
    }

    public void deleteItem(DataItem dataItem) {
        mDataRepository.deleteItem(dataItem);
    }

    public void deleteItemById(Long idItem) {
        mDataRepository.deleteItemById(idItem);
    }

}
