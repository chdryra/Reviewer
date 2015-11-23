package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubjectDataEditTags extends SubjectDataEdit<GvTag> {
    private TagAdjuster mTagAdjuster;

    public SubjectDataEditTags(TagAdjuster tagAdjuster) {
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
