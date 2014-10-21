/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
interface DialogHolder<T extends GVReviewDataList.GVReviewData> extends GVReviewDataUI<T> {
    void inflate(Activity activity);

    View getView();

    @Override
    void initialiseView(T data);

    @Override
    void updateView(T data);

    @Override
    T getGVData();

}
