/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

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

    @Override
    public void onKeyboardDone(CharSequence s) {
        super.onKeyboardDone(s);
        mTagAdjuster.adjustTagsIfNecessary(getEditor());
    }

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        getEditor().getEditorSubject().dereference(new DataReference.DereferenceCallback<String>() {
            @Override
            public void onDereferenced(DataValue<String> value) {
                if(value.hasValue()) mTagAdjuster.setCurrentSubjectTag(value.getData());
            }
        });
    }
}
