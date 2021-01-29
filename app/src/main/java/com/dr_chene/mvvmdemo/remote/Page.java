package com.dr_chene.mvvmdemo.remote;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public abstract class Page {
    @PrimaryKey
    @ColumnInfo(name = "cur_page")
    public int curPage;
    public int offset;
    public boolean over;
    @ColumnInfo(name = "page_count")
    public int pageCount;
    public int size;
    public int total;
}
