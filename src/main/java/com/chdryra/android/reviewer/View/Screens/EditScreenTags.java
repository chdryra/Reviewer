package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenTags {
    private static final GvDataType<GvTagList.GvTag> TYPE = GvTagList.GvTag.TYPE;

    public static class SubjectEditTags extends SubjectEdit<GvTagList.GvTag> {
        private GvTagList.GvTag mCurrentSubjectTag;

        public SubjectEditTags() {
            super(TYPE);
        }

        //Overridden
        @Override
        public void onEditorDone(CharSequence s) {
            super.onEditorDone(s);
            adjustTagsIfNecessary();
        }

        @Override
        public void onAttachReviewView() {
            super.onAttachReviewView();
            String subject = getEditor().getSubject();
            if(DataValidator.validateString(subject)) {
                mCurrentSubjectTag = new GvTagList.GvTag(subject);
            }
        }

        private void adjustTagsIfNecessary() {
            ReviewDataEditor<GvTagList.GvTag> editor = getEditor();

            String camel = TextUtils.toCamelCase(editor.getFragmentSubject());
            GvTagList.GvTag toAdd = new GvTagList.GvTag(camel);
            GvTagList.GvTag toRemove = mCurrentSubjectTag;
            if (toAdd.equals(toRemove)) return;

            GvTagList tags = (GvTagList) editor.getGridData();
            if(DataValidator.validateString(camel)) {
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
}
