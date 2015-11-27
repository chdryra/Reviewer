/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseIdableList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvDataCollection<T extends GvData> extends GvData, VerboseIdableList<T> {
    void sort();

    GvDataList<T> toList();

    boolean contains(T item);

    @Override
    GvDataType<? extends GvData> getGvDataType();

    @Override
    GvReviewId getGvReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();

    @Override
    String getReviewId();

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
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    int size();

    @Override
    T getItem(int position);

    @Override
    void add(T datum);

    @Override
    void addCollection(IdableCollection<T> data);

    @Override
    Iterator<T> iterator();
}
