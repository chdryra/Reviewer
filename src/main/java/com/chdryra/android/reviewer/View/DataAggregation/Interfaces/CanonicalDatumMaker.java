/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface CanonicalDatumMaker<T extends DataReviewIdable> {
    T getCanonical(IdableList<? extends T> data);
}
