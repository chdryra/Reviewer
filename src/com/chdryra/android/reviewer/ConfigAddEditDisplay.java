/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;

import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
class ConfigAddEditDisplay {
    private static ConfigAddEditDisplay               sConfig;
    private final  HashMap<GVType, AddEditDisplayUIs> mDialogAddEditMap;

    private ConfigAddEditDisplay() {
        mDialogAddEditMap = new HashMap<GVType, AddEditDisplayUIs>();

        mDialogAddEditMap.put(GVType.TAGS,
                              new AddEditDisplayUIs(
                                      AddTag.class,
                                      EditTag.class,
                                      ActivityReviewTags.class));

        mDialogAddEditMap.put(GVType.CHILDREN,
                              new AddEditDisplayUIs(
                                      AddChild.class,
                                      EditChild.class,
                                      ActivityReviewChildren.class));

        mDialogAddEditMap.put(GVType.COMMENTS,
                              new AddEditDisplayUIs(
                                      AddComment.class,
                                      EditComment.class,
                                      ActivityReviewComments.class));

        mDialogAddEditMap.put(GVType.IMAGES,
                              new AddEditDisplayUIs(
                                      null,
                                      EditImage.class,
                                      ActivityReviewImages.class));

        mDialogAddEditMap.put(GVType.FACTS,
                              new AddEditDisplayUIs(
                                      AddFact.class,
                                      EditFact.class,
                                      ActivityReviewFacts.class));

        mDialogAddEditMap.put(GVType.LOCATIONS,
                              new AddEditDisplayUIs(
                                      ActivityReviewLocationMap.class,
                                      ActivityReviewLocationMap.class,
                                      ActivityReviewLocations.class));

        mDialogAddEditMap.put(GVType.URLS,
                              new AddEditDisplayUIs(
                                      ActivityReviewURLBrowser.class,
                                      ActivityReviewURLBrowser.class,
                                      ActivityReviewURLs.class));
    }

    static Class<? extends ReviewDataUI> getAddClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getAddClass();
    }

    static Class<? extends ReviewDataUI> getEditClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getEditClass();
    }

    static Class<? extends Activity> getDisplayClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getDisplayClass();
    }

    private static ConfigAddEditDisplay get() {
        if (sConfig == null) sConfig = new ConfigAddEditDisplay();

        return sConfig;
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Tag
    public static class AddTag extends DialogReviewDataAddFragment<GVTagList.GVTag> {
        public AddTag() {
            super(GVType.TAGS);
        }
    }

    //Child
    public static class AddChild extends
                                 DialogReviewDataAddFragment<GVReviewSubjectRatingList
                                         .GVReviewSubjectRating> {
        public AddChild() {
            super(new InputHandlerChildren());
        }
    }

    //Comment
    public static class AddComment extends DialogReviewDataAddFragment<GVCommentList.GVComment> {
        public AddComment() {
            super(GVType.COMMENTS);
        }
    }

    //Fact
    public static class AddFact extends DialogReviewDataAddFragment<GVFactList.GVFact> {
        public AddFact() {
            super(GVType.FACTS);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogReviewDataEditFragment<GVTagList.GVTag> {
        public EditTag() {
            super(GVType.TAGS);
        }
    }

    //Child
    public static class EditChild extends DialogReviewDataEditFragment<GVReviewSubjectRatingList
            .GVReviewSubjectRating> {
        public EditChild() {
            super(GVType.CHILDREN, new InputHandlerChildren());
        }
    }

    //Comment
    public static class EditComment extends DialogReviewDataEditFragment<GVCommentList.GVComment> {
        public EditComment() {
            super(GVType.COMMENTS);
        }
    }

    //Image
    public static class EditImage extends DialogReviewDataEditFragment<GVImageList.GVImage> {
        public EditImage() {
            super(GVType.IMAGES);
        }
    }

    //Fact
    public static class EditFact extends DialogReviewDataEditFragment<GVFactList.GVFact> {
        public EditFact() {
            super(GVType.FACTS);
        }
    }

    /**
     * Packages together an add, edit and display UI.
     */
    class AddEditDisplayUIs {
        private final Class<? extends ReviewDataUI> mAdd;
        private final Class<? extends ReviewDataUI> mEdit;
        private final Class<? extends Activity>     mActivity;

        private AddEditDisplayUIs(Class<? extends ReviewDataUI> add,
                                  Class<? extends ReviewDataUI> edit,
                                  Class<? extends Activity> activity) {
            mAdd = add;
            mEdit = edit;
            mActivity = activity;
        }

        Class<? extends ReviewDataUI> getAddClass() {
            return mAdd;
        }

        Class<? extends ReviewDataUI> getEditClass() {
            return mEdit;
        }

        Class<? extends Activity> getDisplayClass() {
            return mActivity;
        }
    }
}
