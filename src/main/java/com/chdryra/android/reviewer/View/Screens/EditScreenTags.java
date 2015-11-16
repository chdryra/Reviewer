package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.TextUtils;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 10/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenTags extends EditScreenReviewData<GvTagList.GvTag> {
    private static final GvDataType<GvTagList.GvTag> TYPE = GvTagList.GvTag.TYPE;
    private GvTagList.GvTag mCurrentSubjectTag;
    private DataValidator mValidator;

    public EditScreenTags(Context context, ReviewBuilderAdapter builder, DataValidator validator) {
        super(context, builder, TYPE);
        mValidator = validator;
    }

    @Override
    protected MenuDataEdit<GvTagList.GvTag> newMenuAction() {
        return new MenuDataEditTags();
    }

    @Override
    protected SubjectEdit<GvTagList.GvTag> newSubjectAction() {
        return new SubjectEditTags();
    }

    @Override
    public void onGvDataDelete(GvTagList.GvTag data, int requestCode) {
        if(data.equals(mCurrentSubjectTag)) {
            String toast = "Cannot delete subject tag...";
            Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
        } else {
            super.onGvDataDelete(data, requestCode);
        }
    }

    private class SubjectEditTags extends SubjectEdit<GvTagList.GvTag> {

        private SubjectEditTags() {
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
            if(mValidator.validateString(subject)) {
                mCurrentSubjectTag = new GvTagList.GvTag(subject);
            }
        }
    }

    private class MenuDataEditTags extends MenuDataEdit<GvTagList.GvTag> {
        public MenuDataEditTags() {
            super(TYPE);
        }

        @Override
        protected void doDoneSelected() {
            adjustTagsIfNecessary();
            super.doDoneSelected();
        }

        @Override
        protected void doDeleteSelected() {
            super.doDeleteSelected();
            mCurrentSubjectTag = null;
            adjustTagsIfNecessary();
        }
    }

    private void adjustTagsIfNecessary() {
        ReviewDataEditor<GvTagList.GvTag> editor = getEditor();

        String camel = TextUtils.toCamelCase(editor.getFragmentSubject());
        GvTagList.GvTag toAdd = new GvTagList.GvTag(camel);
        GvTagList.GvTag toRemove = mCurrentSubjectTag;
        if(toAdd.equals(toRemove)) return;

        GvTagList tags = (GvTagList) editor.getGridData();
        if(mValidator.validateString(camel)) {
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
