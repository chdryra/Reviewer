/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.Data;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvDataCollection<T extends GvData> extends GvData, VerboseIdableList<T> {
    void sort();

    GvDataList<T> toList();

    @Override
    boolean contains(Object item);

    @Override
    T getItem(int position);
}
