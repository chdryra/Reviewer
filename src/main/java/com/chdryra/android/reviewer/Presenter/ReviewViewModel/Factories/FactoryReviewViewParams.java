package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

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
