/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 22/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Additional constraint over base {@link com.chdryra.android.reviewer.InputHandlerReviewData} to
 * ignore addition if current data already includes Bitmap.
 */
public class InputHandlerImage extends InputHandlerReviewData<GVImageList.GVImage> {
    InputHandlerImage() {
        super(GVReviewDataList.GVType.IMAGES);
    }

    @Override
    boolean passesAddConstraint(GVImageList.GVImage datum, Context context) {
        return super.isNewAndValid(datum, context) && !constraint(datum, context);
    }

    private boolean constraint(GVImageList.GVImage datum, Context context) {
        GVImageList data = (GVImageList) getData();
        if (data != null && data.contains(datum.getBitmap())) {
            makeToastHasItem(context);
            return true;
        }
        return false;
    }
}
