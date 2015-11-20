package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagAdjuster {
    private GvTagList.GvTag mCurrentSubjectTag;

    public TagAdjuster() {
    }

    public void setCurrentSubjectTag(String tag) {
        if(tag != null && tag.length() > 0) {
            mCurrentSubjectTag = new GvTagList.GvTag(tag);
        }
    }

    public GvTagList.GvTag getCurrentSubjectTag() {
        return mCurrentSubjectTag;
    }

    public void adjustTagsIfNecessary(ReviewDataEditor<GvTagList.GvTag> editor) {
        String camel = TextUtils.toCamelCase(editor.getFragmentSubject());
        GvTagList.GvTag toAdd = new GvTagList.GvTag(camel);
        GvTagList.GvTag toRemove = mCurrentSubjectTag;
        if(toAdd.equals(toRemove)) return;

        GvTagList tags = (GvTagList) editor.getGridData();
        if(camel != null && camel.length() > 0) {
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
