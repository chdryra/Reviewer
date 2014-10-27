/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Provides a callback that can be called by {@link com.chdryra.android.reviewer.ReviewDataAdder}
 *
 * @param <T>:{@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData} type
 */
public interface ReviewDataAddListener<T extends GVReviewDataList.GVReviewData> {
    boolean onReviewDataAdd(T data);
}
