/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewParams {
    public <T extends GvData> ReviewViewParams newViewParams(GvDataType<T> dataType) {
        return newParams(ReviewViewParams.ViewType.VIEW, dataType);
    }

    public ReviewViewParams newEditorParams(GvDataType<?> dataType) {
        return newParams(ReviewViewParams.ViewType.EDIT, dataType);
    }

    public ReviewViewParams newSearchParams(GvDataType<?> dataType, String hint) {
        ReviewViewParams params = newEditorParams(dataType);
        params.getSubjectParams().setEditable(true).setUpdateOnRefresh(false).setHint(hint);
        params.getRatingBarParams().setVisible(false);
        return params;
    }

    public ReviewViewParams newReviewsListParams() {
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.CellDimension wrapped = ReviewViewParams.CellDimension.WRAPPED;
        ReviewViewParams params = new ReviewViewParams().setCoverManager(false);
        params.getGridViewParams().setCellHeight(wrapped).setCellWidth(full);
        params.setAlpha(ReviewViewParams.Alpha.OPAQUE);

        return params;
    }

    public ReviewViewParams newBuildReviewParams() {
        return new ReviewViewParams(ReviewViewParams.ViewType.EDIT).setEditable();
    }

    public ReviewViewParams newPublishParams() {
        return new ReviewViewParams(ReviewViewParams.ViewType.EDIT);
    }

    private <T extends GvData> ReviewViewParams newParams(ReviewViewParams.ViewType viewType,
                                                          GvDataType<T> dataType) {
        ReviewViewParams params = new ReviewViewParams(viewType);
        if (dataType.equals(GvImage.TYPE) || dataType.equals(GvImage.Reference.TYPE)) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            params.getGridViewParams().setCellHeight(half).setCellWidth(half);
        } else if (dataType.equals(GvBucket.TYPE)) {
            ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
            ReviewViewParams.CellDimension eighth = ReviewViewParams.CellDimension.EIGHTH;
            params.getGridViewParams().setCellHeight(eighth).setCellWidth(full);
        }

        return params;
    }
}
