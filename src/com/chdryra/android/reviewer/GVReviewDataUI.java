/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface GVReviewDataUI<T extends GVReviewDataList.GVReviewData> {
    void initialiseView(T data);

    void updateView(T data);

    T getGVData();
}
