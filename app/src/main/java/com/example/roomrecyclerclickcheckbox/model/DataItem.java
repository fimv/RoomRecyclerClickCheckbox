package com.example.roomrecyclerclickcheckbox.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity
public class DataItem {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String name;
    private String content;
    private String details;
    private String section;

    @Ignore
    public DataItem(String name, String content, String details, String section) {
        this.name = name;
        this.content = content;
        this.details = details;
        this.section = section;
    }

    public DataItem(List<DataItem> mDataItemList) {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public DataItem() {
        this.name = "name";
        this.content = "content";
        this.details = "details";
        this.section = "section";
    }
}