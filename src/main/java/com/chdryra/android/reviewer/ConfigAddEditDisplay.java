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
    private static ConfigAddEditDisplay                          sConfig;
    private final  HashMap<VgDataList.GvType, AddEditDisplayUIs> mDialogAddEditMap;

    private ConfigAddEditDisplay() {
        mDialogAddEditMap = new HashMap<VgDataList.GvType, AddEditDisplayUIs>();

        mDialogAddEditMap.put(VgDataList.GvType.TAGS,
                new AddEditDisplayUIs(
                        AddTag.class,
                        EditTag.class,
                        ActivityReviewTags.class));

        mDialogAddEditMap.put(VgDataList.GvType.CHILDREN,
                new AddEditDisplayUIs(
                        AddChild.class,
                        EditChild.class,
                        ActivityReviewChildren.class));

        mDialogAddEditMap.put(VgDataList.GvType.COMMENTS,
                new AddEditDisplayUIs(
                        AddComment.class,
                        EditComment.class,
                        ActivityReviewComments.class));

        mDialogAddEditMap.put(VgDataList.GvType.IMAGES,
                new AddEditDisplayUIs(
                        null,
                        EditImage.class,
                        ActivityReviewImages.class));

        mDialogAddEditMap.put(VgDataList.GvType.FACTS,
                new AddEditDisplayUIs(
                        AddFact.class,
                        EditFact.class,
                        ActivityReviewFacts.class));

        mDialogAddEditMap.put(VgDataList.GvType.LOCATIONS,
                new AddEditDisplayUIs(
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocations.class));

        mDialogAddEditMap.put(VgDataList.GvType.URLS,
                new AddEditDisplayUIs(
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLs.class));

        mDialogAddEditMap.put(VgDataList.GvType.REVIEW,
                new AddEditDisplayUIs(
                        null,
                        null,
                        ActivityFeed.class));

        mDialogAddEditMap.put(VgDataList.GvType.SOCIAL,
                new AddEditDisplayUIs(
                        null,
                        null,
                        ActivityReviewShare.class));
    }

    public static Class<? extends LaunchableUI> getAddClass(VgDataList.GvType dataType) {
        return get().mDialogAddEditMap.get(dataType).getAddClass();
    }

    public static Class<? extends LaunchableUI> getEditClass(VgDataList.GvType dataType) {
        return get().mDialogAddEditMap.get(dataType).getEditClass();
    }

    public static Class<? extends Activity> getDisplayClass(VgDataList.GvType dataType) {
        return get().mDialogAddEditMap.get(dataType).getDisplayClass();
    }

    private static ConfigAddEditDisplay get() {
        if (sConfig == null) sConfig = new ConfigAddEditDisplay();

        return sConfig;
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Tag
    public static class AddTag extends DialogReviewDataAddFragment<VgTagList.VgTag> {
        public AddTag() {
            super(VgDataList.GvType.TAGS);
        }
    }

    //Child
    public static class AddChild extends
            DialogReviewDataAddFragment<VgSubjectRatingList.GvSubjectRating> {
        public AddChild() {
            super(new InputHandlerChildren());
        }
    }

    //Comment
    public static class AddComment extends DialogReviewDataAddFragment<VgCommentList.GvComment> {
        public AddComment() {
            super(VgDataList.GvType.COMMENTS);
        }
    }

    //Fact
    public static class AddFact extends DialogReviewDataAddFragment<VgFactList.VgFact> {
        public AddFact() {
            super(VgDataList.GvType.FACTS);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogReviewDataEditFragment<VgTagList.VgTag> {
        public EditTag() {
            super(VgDataList.GvType.TAGS);
        }
    }

    //Child
    public static class EditChild extends DialogReviewDataEditFragment<VgSubjectRatingList
            .GvSubjectRating> {
        public EditChild() {
            super(VgDataList.GvType.CHILDREN, new InputHandlerChildren());
        }
    }

    //Comment
    public static class EditComment extends DialogReviewDataEditFragment<VgCommentList.GvComment> {
        public EditComment() {
            super(VgDataList.GvType.COMMENTS);
        }
    }

    //Image
    public static class EditImage extends DialogReviewDataEditFragment<VgImageList.GvImage> {
        public EditImage() {
            super(VgDataList.GvType.IMAGES);
        }
    }

    //Fact
    public static class EditFact extends DialogReviewDataEditFragment<VgFactList.VgFact> {
        public EditFact() {
            super(VgDataList.GvType.FACTS);
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
