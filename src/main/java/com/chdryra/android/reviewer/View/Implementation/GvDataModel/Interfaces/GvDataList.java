package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvDataList<T extends GvData> extends Iterable<T>, ViewHolderDataList<T>, GvDataCollection<T> {
    boolean hasData();

    @Override
    GvDataList<T> toList();

    @Override
    GvDataType<T> getGvDataType();

    @Override
    String getStringSummary();

    @Override
    String getReviewId();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();

    @Override
    int describeContents();

    @Override
    void writeToParcel(Parcel dest, int flags);

    @Override
    boolean hasData(DataValidator dataValidator);

    @Override
    ViewHolder getViewHolder();

    @Override
    boolean isValidForDisplay();

    @Override
    GvReviewId getGvReviewId();

    @Override
    void addCollection(IdableCollection<T> data);

    @Override
    void add(T item);

    @Override
    boolean contains(T item);

    @Override
    void remove(T item);

    @Override
    void removeAll();

    @Override
    int size();

    @Override
    T getItem(int position);

    @Override
    void sort();

    @Override
    void sort(Comparator<T> comparator);

    @Override
    void addList(Iterable<T> list);

    @Override
    ArrayList<T> toArrayList();

    @Override
    Iterator<T> iterator();
}
