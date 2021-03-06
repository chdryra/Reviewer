/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BuildScreenGridUi<GC extends GvDataList> {
    void setParentAdapter(ReviewBuilderAdapter<?> adapter);

    GvDataList<GC> getGridWrapper();
}
