package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewParams {
    public <T extends GvData> ReviewViewParams getParams(GvDataType<T> dataType) {
        ReviewViewParams params = new ReviewViewParams();
        if (dataType.equals(GvImage.TYPE)) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            params.setCellHeight(half).setCellWidth(half);
        }

        return params;
    }
}
