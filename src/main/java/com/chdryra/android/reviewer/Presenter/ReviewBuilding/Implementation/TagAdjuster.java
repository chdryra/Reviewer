package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

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
        if(tag != null && tag.length() > 0) {
            mCurrentSubjectTag = new GvTag(tag);
        }
    }

    public GvTag getCurrentSubjectTag() {
        return mCurrentSubjectTag;
    }

    public void adjustTagsIfNecessary(ReviewDataEditor<GvTag> editor) {
        String camel = TextUtils.toCamelCase(editor.getFragmentSubject());
        GvTag toAdd = new GvTag(camel);
        GvTag toRemove = mCurrentSubjectTag;
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
