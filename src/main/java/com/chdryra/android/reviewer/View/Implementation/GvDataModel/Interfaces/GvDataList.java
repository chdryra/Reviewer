package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces;

import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvDataList<T extends GvData> extends Iterable<T>, ViewHolderDataList<T>, GvDataCollection<T> {
    boolean hasData();

    @Override
    GvDataType<T> getGvDataType();

    @Override
    void add(T item);

    @Override
    boolean contains(T item);
}
