/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryGridUi<GC extends GvDataList> {
    BuildScreenGridUi<GC> newGridUiWrapperFull();

    BuildScreenGridUi<GC> newGridUiWrapperQuick();
}
