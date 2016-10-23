/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.DataBuilderAdapterDefault;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FactoryDataBuilderAdapter {
    public <T extends GvDataParcelable> DataBuilderAdapterDefault<T> newDataBuilderAdapter
            (GvDataType<T> dataType, ReviewBuilderAdapter<?> parent) {
        return new DataBuilderAdapterDefault<>(dataType, parent);
    }
}
