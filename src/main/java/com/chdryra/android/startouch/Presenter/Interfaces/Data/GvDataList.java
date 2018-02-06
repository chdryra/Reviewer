/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Data;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderDataList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

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
