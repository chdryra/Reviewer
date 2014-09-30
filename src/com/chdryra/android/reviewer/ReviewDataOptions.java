/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.DialogFragment;
import android.util.Log;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;

public class ReviewDataOptions {
    private final static String TAG = "ReviewDataOptions";

    private final static String DIALOG_TAG_TAG      = "TagDialog";
    private final static String DIALOG_COMMENT_TAG  = "CommentDialog";
    private final static String DIALOG_IMAGE_TAG    = "ImageDialog";
    private final static String DIALOG_LOCATION_TAG = "LocationDialog";
    private final static String DIALOG_URL_TAG      = "URLDialog";
    private final static String DIALOG_CHILD_TAG    = "ChildDialog";
    private final static String DIALOG_FACTS_TAG    = "FactsDialog";

    private final static int IMAGE_REQUEST    = 10;
    private final static int IMAGE_ADD        = 11;
    private final static int LOCATION_REQUEST = 20;
    private final static int LOCATION_ADD     = 21;
    private final static int COMMENT_REQUEST  = 30;
    private final static int COMMENT_ADD      = 31;
    private final static int FACTS_REQUEST    = 40;
    private final static int FACTS_ADD        = 41;
    private final static int URL_REQUEST      = 50;
    private final static int URL_ADD          = 51;
    private final static int CHILDREN_REQUEST = 60;
    private final static int CHILDREN_ADD     = 61;
    private final static int TAGS_REQUEST     = 80;
    private final static int TAGS_ADD         = 81;
    private static ReviewDataOptions                 sReviewDataOptions;
    private final  HashMap<GVType, ReviewDataOption> mReviewDataOptions;

    private ReviewDataOptions() {
        mReviewDataOptions = new HashMap<GVReviewDataList.GVType,
                ReviewDataOptions.ReviewDataOption>();
        mReviewDataOptions.put(GVType.TAGS,
                new ReviewDataOption(DialogTagAddFragment.class, TAGS_ADD, DIALOG_TAG_TAG,
                        ActivityReviewTags.class, TAGS_REQUEST, new VHTagView()));
        mReviewDataOptions.put(GVType.CRITERIA,
                new ReviewDataOption(DialogChildAddFragment.class, CHILDREN_ADD,
                        DIALOG_CHILD_TAG, ActivityReviewChildren.class, CHILDREN_REQUEST,
                        new VHReviewNodeSubjectRating()));
        mReviewDataOptions.put(GVType.COMMENTS,
                new ReviewDataOption(DialogCommentAddFragment.class, COMMENT_ADD,
                        DIALOG_COMMENT_TAG, ActivityReviewComments.class, COMMENT_REQUEST,
                        new VHCommentView()));
        mReviewDataOptions.put(GVType.IMAGES,
                new ReviewDataOption(DialogImageEditFragment.class, IMAGE_ADD, DIALOG_IMAGE_TAG,
                        ActivityReviewImages.class, IMAGE_REQUEST, new VHImageView()));
        mReviewDataOptions.put(GVType.FACTS,
                new ReviewDataOption(DialogFactAddFragment.class, FACTS_ADD, DIALOG_FACTS_TAG,
                        ActivityReviewFacts.class, FACTS_REQUEST, new VHFactView()));
        mReviewDataOptions.put(GVType.LOCATIONS,
                new ReviewDataOption(DialogLocationFragment.class, LOCATION_ADD,
                        DIALOG_LOCATION_TAG, ActivityReviewLocations.class, LOCATION_REQUEST,
                        new VHLocationView(true)));
        mReviewDataOptions.put(GVType.URLS,
                new ReviewDataOption(DialogUrlFragment.class, URL_ADD, DIALOG_URL_TAG,
                        ActivityReviewUrls.class, URL_REQUEST, new VHUrlView()));

    }

    public static ReviewDataOption get(GVType dataType) {
        return getOptions().mReviewDataOptions.get(dataType);
    }

    private static ReviewDataOptions getOptions() {
        if (sReviewDataOptions == null) {
            sReviewDataOptions = new ReviewDataOptions();
        }

        return sReviewDataOptions;
    }

    class ReviewDataOption {

        private final Class<? extends DialogFragment> mDialogClass;
        private final Class<? extends Activity>       mActivityClass;
        private final int                             mActivityRequestCode;
        private final int                             mDialogRequestCode;
        private final String                          mDialogTag;
        private final ViewHolder                      mViewHolder;

        private ReviewDataOption(Class<? extends DialogFragment> dialogClass,
                                 int dialogRequestCode, String dialogTag,
                                 Class<? extends Activity> activityClass,
                                 int activityRequestCode, ViewHolder viewHolder) {
            mDialogClass = dialogClass;
            mDialogRequestCode = dialogRequestCode;
            mDialogTag = dialogTag;
            mActivityClass = activityClass;
            mActivityRequestCode = activityRequestCode;
            mViewHolder = viewHolder;
        }

        public DialogFragment getDialogFragment() throws RuntimeException {
            try {
                return mDialogClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "Couldn't create dialog for " + mDialogClass.getName(), e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "IllegalAccessEception: trying to create " + mDialogClass.getName(), e);
                throw new RuntimeException(e);
            }
        }

        public Class<? extends Activity> getActivityRequestClass() {
            return mActivityClass;
        }

        public int getActivityRequestCode() {
            return mActivityRequestCode;
        }

        public int getDialogRequestCode() {
            return mDialogRequestCode;
        }

        public String getDialogTag() {
            return mDialogTag;
        }

        public ViewHolder getViewHolder() {
            return mViewHolder;
        }
    }
}
