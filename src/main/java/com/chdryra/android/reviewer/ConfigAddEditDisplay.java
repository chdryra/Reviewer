/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class ConfigAddEditDisplay {
    private static ConfigAddEditDisplay                                sConfig;
    private final  HashMap<GVReviewDataList.GvType, AddEditDisplayUIs> mDialogAddEditMap;

    private ConfigAddEditDisplay() {
        mDialogAddEditMap = new HashMap<GVReviewDataList.GvType, AddEditDisplayUIs>();

        mDialogAddEditMap.put(GVReviewDataList.GvType.TAGS,
                new AddEditDisplayUIs(
                        AddTag.class,
                        EditTag.class,
                        ActivityReviewTags.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.CHILDREN,
                new AddEditDisplayUIs(
                        AddChild.class,
                        EditChild.class,
                        ActivityReviewChildren.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.COMMENTS,
                new AddEditDisplayUIs(
                        AddComment.class,
                        EditComment.class,
                        ActivityReviewComments.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.IMAGES,
                new AddEditDisplayUIs(
                        null,
                        EditImage.class,
                        ActivityReviewImages.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.FACTS,
                new AddEditDisplayUIs(
                        AddFact.class,
                        EditFact.class,
                        ActivityReviewFacts.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.LOCATIONS,
                new AddEditDisplayUIs(
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocations.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.URLS,
                new AddEditDisplayUIs(
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLs.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.REVIEW,
                new AddEditDisplayUIs(
                        null,
                        null,
                        ActivityFeed.class));

        mDialogAddEditMap.put(GVReviewDataList.GvType.SOCIAL,
                new AddEditDisplayUIs(
                        null,
                        null,
                        ActivityReviewShare.class));
    }

    public static Class<? extends LaunchableUI> getAddClass(GVReviewDataList.GvType dataType) {
        return get().mDialogAddEditMap.get(dataType).getAddClass();
    }

    public static Class<? extends LaunchableUI> getEditClass(GVReviewDataList.GvType dataType) {
        return get().mDialogAddEditMap.get(dataType).getEditClass();
    }

    public static Class<? extends Activity> getDisplayClass(GVReviewDataList.GvType dataType) {
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
            super(GVReviewDataList.GvType.TAGS);
        }
    }

    //Child
    public static class AddChild extends
            DialogReviewDataAddFragment<GVReviewSubjectRatingList.GvSubjectRating> {
        public AddChild() {
            super(new InputHandlerChildren());
        }
    }

    //Comment
    public static class AddComment extends DialogReviewDataAddFragment<GVCommentList.GvComment> {
        public AddComment() {
            super(GVReviewDataList.GvType.COMMENTS);
        }
    }

    //Fact
    public static class AddFact extends DialogReviewDataAddFragment<GVFactList.GvFact> {
        public AddFact() {
            super(GVReviewDataList.GvType.FACTS);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogReviewDataEditFragment<GVTagList.GVTag> {
        public EditTag() {
            super(GVReviewDataList.GvType.TAGS);
        }
    }

    //Child
    public static class EditChild extends DialogReviewDataEditFragment<GVReviewSubjectRatingList
            .GvSubjectRating> {
        public EditChild() {
            super(GVReviewDataList.GvType.CHILDREN, new InputHandlerChildren());
        }
    }

    //Comment
    public static class EditComment extends DialogReviewDataEditFragment<GVCommentList.GvComment> {
        public EditComment() {
            super(GVReviewDataList.GvType.COMMENTS);
        }
    }

    //Image
    public static class EditImage extends DialogReviewDataEditFragment<GVImageList.GvImage> {
        public EditImage() {
            super(GVReviewDataList.GvType.IMAGES);
        }
    }

    //Fact
    public static class EditFact extends DialogReviewDataEditFragment<GVFactList.GvFact> {
        public EditFact() {
            super(GVReviewDataList.GvType.FACTS);
        }
    }

    /**
     * Packages together an add, edit and display UI.
     */
    class AddEditDisplayUIs {
        private final Class<? extends LaunchableUI> mAdd;
        private final Class<? extends LaunchableUI> mEdit;
        private final Class<? extends Activity>     mActivity;

        private AddEditDisplayUIs(Class<? extends LaunchableUI> add,
                Class<? extends LaunchableUI> edit,
                Class<? extends Activity> activity) {
            mAdd = add;
            mEdit = edit;
            mActivity = activity;
        }

        Class<? extends LaunchableUI> getAddClass() {
            return mAdd;
        }

        Class<? extends LaunchableUI> getEditClass() {
            return mEdit;
        }

        Class<? extends Activity> getDisplayClass() {
            return mActivity;
        }
    }
}
