/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityReviewView extends ActivitySingleFragment {
    private static final String TYPE        = "com.chdryra.android.review.activityreviewview_type";
    private static final String EDIT        = "com.chdryra.android.review.activityreviewview_edit";

    public static void packParameters(GvDataList.GvType dataType, boolean isEdit, Intent i) {
        i.putExtra(TYPE, dataType);
        i.putExtra(EDIT, isEdit);
    }

    protected Fragment createFragment() {
        GvDataList.GvType dataType = (GvDataList.GvType) getIntent().getSerializableExtra(TYPE);
        Boolean isEdit = getIntent().getBooleanExtra(EDIT, false);
        return FragmentReviewView.newInstance(dataType, isEdit);
    }
}
