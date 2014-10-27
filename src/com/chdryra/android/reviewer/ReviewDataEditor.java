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
 * A launchable UI that provides review data that can be added to a {@link com.chdryra.android
 * .reviewer.ReviewDataEditListener}.
 *
 * @param <T>: {@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData} type.
 */
public interface ReviewDataEditor<T extends GVReviewDataList.GVReviewData> extends ReviewDataUI {
    void reviewDataDelete(ReviewDataEditListener<T> listener, T data);

    void reviewDataEdit(ReviewDataEditListener<T> listener, T oldDatum, T newDatum);
}
