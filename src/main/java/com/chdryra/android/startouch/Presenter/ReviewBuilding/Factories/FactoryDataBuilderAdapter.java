/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.CommentBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation.DataBuilderAdapterDefault;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryDataBuilderAdapter {
    public <T extends GvDataParcelable> DataBuilderAdapterDefault<T> newDataBuilderAdapter
            (GvDataType<T> dataType, ReviewBuilderAdapter<?> parent) {

        //TODO make type safe
        if (dataType.equals(GvComment.TYPE)){
            return (DataBuilderAdapterDefault<T>) new CommentBuilderAdapter(parent);

        } else {
            return new DataBuilderAdapterDefault<>(dataType, parent);
        }
    }
}
