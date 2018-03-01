/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagAdjuster {
    private GvTag mCurrentSubjectTag;

    public TagAdjuster() {
    }

    public void setCurrentSubjectTag(String tag) {
        mCurrentSubjectTag = null;
        if(tag != null && tag.length() > 0) {
            mCurrentSubjectTag = new GvTag(tag);
        }
    }

    public void adjustTagsIfNecessary(ReviewDataEditor<GvTag> editor) {
        GvTag toAdd = new GvTag(editor.getContainerSubject());
        GvTag toRemove = mCurrentSubjectTag;
        if(toAdd.equals(toRemove)) return;

        GvDataList<GvTag> tags = editor.getGridData();
        String subject = toAdd.getTag();
        if(subject != null && subject.length() > 0) {
            if(!tags.contains(toAdd)) {
                if(toRemove != null ) {
                    editor.replace(toRemove, toAdd);
                } else {
                    editor.add(toAdd);
                }
                mCurrentSubjectTag = toAdd;
            }
        } else if(mCurrentSubjectTag != null){
            editor.delete(toRemove);
            mCurrentSubjectTag = null;
        }
    }
}
