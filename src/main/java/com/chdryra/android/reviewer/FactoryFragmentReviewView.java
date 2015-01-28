/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFragmentReviewView {
    public static Fragment newView(GvDataList.GvType dataType, boolean isEdit) {
        return newEditor(dataType);
    }

    public static Fragment newEditor(GvDataList.GvType dataType) {
        //return get().mFragments.get(dataType);
        return FragmentViewReview.newInstance(dataType, true);
    }
}
