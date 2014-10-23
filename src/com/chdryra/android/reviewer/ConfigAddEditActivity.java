/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;

import com.chdryra.android.reviewer.GVReviewDataList.GVReviewData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ConfigAddEditActivity {
    private static ConfigAddEditActivity                                    sConfig;
    private final  HashMap<GVType, AddEditActivity<? extends GVReviewData>> mDialogAddEditMap;

    private ConfigAddEditActivity() {
        mDialogAddEditMap = new HashMap<GVType, AddEditActivity<? extends GVReviewData>>();

        mDialogAddEditMap.put(GVType.TAGS,
                new AddEditActivity<GVTagList.GVTag>(
                        AddTag.class,
                        EditTag.class,
                        ActivityReviewTags.class));

        mDialogAddEditMap.put(GVType.CHILDREN,
                new AddEditActivity<GVReviewSubjectRatingList.GVReviewSubjectRating>(
                        AddChild.class,
                        EditChild.class,
                        ActivityReviewChildren.class));

        mDialogAddEditMap.put(GVType.COMMENTS,
                new AddEditActivity<GVCommentList.GVComment>(
                        AddComment.class,
                        EditComment.class,
                        ActivityReviewComments.class));

        mDialogAddEditMap.put(GVType.IMAGES,
                new AddEditActivity<GVImageList.GVImage>(
                        null,
                        EditImage.class,
                        ActivityReviewImages.class));

        mDialogAddEditMap.put(GVType.FACTS,
                new AddEditActivity<GVFactList.GVFact>(
                        AddFact.class,
                        EditFact.class,
                        ActivityReviewFacts.class));

        mDialogAddEditMap.put(GVType.LOCATIONS,
                new AddEditActivity<GVLocationList.GVLocation>(
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocationMap.class,
                        ActivityReviewLocations.class));

        mDialogAddEditMap.put(GVType.URLS,
                new AddEditActivity<GVUrlList.GVUrl>(
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLBrowser.class,
                        ActivityReviewURLs.class));
    }

    private static ConfigAddEditActivity get() {
        if (sConfig == null) sConfig = new ConfigAddEditActivity();

        return sConfig;
    }

    static Class<? extends ReviewDataUI>
    getAddClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getAdd();
    }

    static Class<? extends ReviewDataUI>
    getEditClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getEdit();
    }

    static Class<? extends Activity> getActivityClass(GVType dataType) {
        return get().mDialogAddEditMap.get(dataType).getActivity();
    }

    //***Add***
    //Tag
    public static class AddTag extends DialogReviewDataAddFragment<GVTagList.GVTag> {
        public AddTag() {
            super(GVType.TAGS);
        }
    }

    //Child
    public static class AddChild extends
            DialogReviewDataAddFragment<GVReviewSubjectRatingList.GVReviewSubjectRating> {
        public AddChild() {
            super(GVType.CHILDREN);
            mHandler = new InputHandlerChildren();
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

    //***Edit***
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
            super(GVType.CHILDREN);
            mHandler = new InputHandlerChildren();
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

    class AddEditActivity<T extends GVReviewDataList.GVReviewData> {
        private Class<? extends ReviewDataUI> mAdd;
        private Class<? extends ReviewDataUI> mEdit;
        private Class<? extends Activity>     mActivity;

        private AddEditActivity(Class<? extends ReviewDataUI> add,
                                Class<? extends ReviewDataUI> edit,
                                Class<? extends Activity> activity) {
            mAdd = add;
            mEdit = edit;
            mActivity = activity;
        }

        Class<? extends ReviewDataUI> getAdd() {
            return mAdd;
        }

        Class<? extends ReviewDataUI> getEdit() {
            return mEdit;
        }

        Class<? extends Activity> getActivity() {
            return mActivity;
        }
    }
}
