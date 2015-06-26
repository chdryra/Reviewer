/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 25/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvComparator<T extends GvData> {
    int compare(T datum1, T datum2);

    GvKey getKey(T datum);
}
