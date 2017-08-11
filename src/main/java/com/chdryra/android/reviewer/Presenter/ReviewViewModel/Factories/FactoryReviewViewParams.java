/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSize;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 19/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewParams {
    public <T extends GvData> ReviewViewParams newViewParams(GvDataType<T> dataType) {
        ReviewViewParams params = new ReviewViewParams();
        if (dataType.equals(GvImage.TYPE) || dataType.equals(GvImage.Reference.TYPE)) {
            ReviewViewParams.CellDimension half = ReviewViewParams.CellDimension.HALF;
            params.getGridViewParams().setCellHeight(half).setCellWidth(half);
        } else if(dataType.equals(GvBucket.TYPE)) {
            params = new ReviewViewParams(ReviewViewParams.ViewType.REVIEWS_LIST);
            ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
            ReviewViewParams.CellDimension eighth = ReviewViewParams.CellDimension.EIGHTH;
            params.getGridViewParams().setCellHeight(eighth).setCellWidth(full);
        } else if(dataType.equals(GvSize.Reference.TYPE)) {
            params.getGridViewParams().setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);
        }

        return params;
    }

    public ReviewViewParams newEditorParams(GvDataType<?> dataType) {
        ReviewViewParams params = newViewParams(dataType);
        params.getRatingBarParams().setEditable(true);
        params.getSubjectParams().setEditable(true);

        return params;
    }

    public ReviewViewParams newSearchParams(GvDataType<?> dataType, String hint) {
        ReviewViewParams params = newViewParams(dataType);
        params.getSubjectParams().setEditable(true).setUpdateOnRefresh(false).setHint(hint);
        params.getRatingBarParams().setVisible(false);
        return params;
    }

    public ReviewViewParams newReviewsListParams() {
        ReviewViewParams params = new ReviewViewParams(ReviewViewParams.ViewType.REVIEWS_LIST);
        ReviewViewParams.CellDimension full = ReviewViewParams.CellDimension.FULL;
        ReviewViewParams.CellDimension wrapped = ReviewViewParams.CellDimension.WRAPPED;
        ReviewViewParams.GridViewAlpha trans = ReviewViewParams.GridViewAlpha.TRANSPARENT;
        params.setCoverManager(false);
        params.getGridViewParams().setCellHeight(wrapped).setCellWidth(full).setGridAlpha(trans);

        return params;
    }

    public ReviewViewParams newBuildReviewParams() {
        ReviewViewParams params = new ReviewViewParams();
        params.getGridViewParams().setGridAlpha(ReviewViewParams.GridViewAlpha.MEDIUM);
        params.getRatingBarParams().setEditable(true);
        params.getSubjectParams().setEditable(true);

        return params;
    }

    public ReviewViewParams newPublishParams() {
        ReviewViewParams params = new ReviewViewParams();
        params.getGridViewParams().setGridAlpha(ReviewViewParams.GridViewAlpha.MEDIUM);

        return params;
    }
}
