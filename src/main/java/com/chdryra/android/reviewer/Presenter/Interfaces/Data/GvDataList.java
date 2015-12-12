package com.chdryra.android.reviewer.Presenter.Interfaces.Data;

import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvDataList<T extends GvData> extends ViewHolderDataList<T>, GvDataCollection<T> {
    boolean hasData();

    @Override
    GvDataType<T> getGvDataType();

    @Override
    boolean add(T item);

    @Override
    boolean contains(Object item);
}
