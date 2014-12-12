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
 * Additional constraint over base {@link InputHandlerGvData} to
 * ignore addition if current data already includes Bitmap.
 */
class InputHandlerImage extends InputHandlerGvData<GvImageList.GvImage> {
    InputHandlerImage() {
        super(new GvImageList());
    }

    @Override
    boolean passesAddConstraint(GvImageList.GvImage datum, Context context) {
        return super.isNewAndValid(datum, context) && !constraint(datum, context);
    }

    private boolean constraint(GvImageList.GvImage datum, Context context) {
        GvImageList data = (GvImageList) getData();
        if (data != null && data.contains(datum.getBitmap())) {
            makeToastHasItem(context);
            return true;
        }
        return false;
    }
}
