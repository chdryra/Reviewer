/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseIdableList;

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
    T getItem(int position);
}
