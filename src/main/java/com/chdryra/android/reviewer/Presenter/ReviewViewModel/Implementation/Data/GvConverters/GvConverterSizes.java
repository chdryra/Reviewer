/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSizeList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterSizes extends GvConverterBasic<DataSize, GvSize, GvSizeList> {
    private GvDataType<?> mType;

    public GvConverterSizes(GvDataType<?> type) {
        super(GvSizeList.class);
        mType = type;
    }

    public void setType(GvDataType<?> type) {
        mType = type;
    }

    public GvDataType<?> getType() {
        return mType;
    }

    @Override
    public GvSize convert(DataSize datum, ReviewId reviewId) {
        return new GvSize(newId(datum.getReviewId()), mType, datum.getSize());
    }
}
