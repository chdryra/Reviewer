/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataViewer<T extends GvData> extends DataObservable, AsyncSortable<T>{
    GvDataType<? extends GvData> getGvDataType();

    GvDataList<T> getGridData();

    boolean isExpandable(T datum);

    ReviewStamp getStamp();

    @Nullable
    ReviewViewAdapter<?> expandGridCell(T datum);

    @Nullable
    ReviewViewAdapter<?> expandGridData();
}
