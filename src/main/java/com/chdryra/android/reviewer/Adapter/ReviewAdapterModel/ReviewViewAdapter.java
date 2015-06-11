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
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.GridDataObservable;

/**
 * Adapter for {@link Review} for passing {@link com.chdryra
 * .android.reviewer.MdData} to View layer as {@link GvData}
 */
public interface ReviewViewAdapter extends GridDataObservable, GridDataExpander {
    String getSubject();

    float getRating();

    float getAverageRating();

    GvDataList getGridData();

    GvImageList getCovers();

    @Override
    void registerGridDataObserver(GridDataObserver observer);

    @Override
    void notifyGridDataObservers();

    @Override
    boolean isExpandable(GvData datum);

    @Override
    ReviewViewAdapter expandItem(GvData datum);
}
