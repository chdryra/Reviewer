/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectEditTags extends SubjectEdit<GvTag> {
    private final TagAdjuster mTagAdjuster;

    public SubjectEditTags(TagAdjuster tagAdjuster) {
        mTagAdjuster = tagAdjuster;
    }

    //Overridden
    @Override
    public void onKeyboardDone(CharSequence s) {
        super.onKeyboardDone(s);
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mTagAdjuster.setCurrentSubjectTag(getEditor().getSubject());
    }
}
