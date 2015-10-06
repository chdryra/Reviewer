/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;
import com.chdryra.android.reviewer.View.Screens.ReviewView;

/**
 * Adapter for {@link Review} for passing {@link com.chdryra
 * .android.reviewer.MdData} to View layer as {@link GvData}
 */
public interface ReviewViewAdapter<T extends GvData> extends GridDataObservable,
        GridDataViewer<T> {
    String getSubject();

    float getRating();

    float getAverageRating();

    GvDataList<T> getGridData();

    GvImageList getCovers();

    void registerReviewView(ReviewView view);

    ReviewView getReviewView();

    @Override
    void registerGridDataObserver(GridDataObserver observer);

    @Override
    void unregisterGridDataObserver(GridDataObserver observer);

    @Override
    void notifyGridDataObservers();

    @Override
    boolean isExpandable(T datum);

    @Override
    ReviewViewAdapter expandGridCell(T datum);

    @Override
    ReviewViewAdapter expandGridData();

    @Override
    void setData(GvDataCollection<T> data);
}
