/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVCommentList.GVComment;

class VHCommentView extends VHTextView {

    public VHCommentView() {
        super(new GVDataStringGetter() {
            @Override
            public String getString(GVData data) {
                GVComment comment = (GVComment) data;
                return comment != null ? comment.getCommentHeadline() : null;
            }
        });

    }
}
