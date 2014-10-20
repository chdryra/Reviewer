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
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

import java.util.HashMap;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit the data.
 *
 * @see com.chdryra.android.reviewer.FragmentReviewBuild;
 */
class ConfigReviewDataUI {
    private final static String TAG = "OptionsReviewBuild";

    private final static String DIALOG_TAG_ADD_TAG       = "TagAddDialog";
    private final static String DIALOG_TAG_EDIT_TAG      = "TagEditDialog";
    private final static String DIALOG_COMMENT_ADD_TAG   = "CommentAddDialog";
    private final static String DIALOG_COMMENT_EDIT_TAG  = "CommentEditDialog";
    private final static String DIALOG_IMAGE_ADD_TAG     = "ImageAddDialog";
    private final static String DIALOG_IMAGE_EDIT_TAG    = "ImageEditDialog";
    private final static String DIALOG_LOCATION_ADD_TAG  = "LocationAddDialog";
    private final static String DIALOG_LOCATION_EDIT_TAG = "LocationEditDialog";
    private final static String DIALOG_URL_ADD_TAG       = "URLAddDialog";
    private final static String DIALOG_URL_EDIT_TAG      = "URLEditDialog";
    private final static String DIALOG_CHILD_ADD_TAG     = "ChildAddDialog";
    private final static String DIALOG_CHILD_EDIT_TAG    = "ChildEditDialog";
    private final static String DIALOG_FACTS_ADD_TAG     = "FactsAddDialog";
    private final static String DIALOG_FACTS_EDIT_TAG    = "FactsEditDialog";

    private final static int DATA_ADD  = 1;
    private final static int DATA_EDIT = 2;

    private final static int IMAGE_REQUEST    = 10;
    private final static int LOCATION_REQUEST = 20;
    private final static int LOCATION_ADD     = 21;
    private final static int COMMENT_REQUEST  = 30;
    private final static int FACTS_REQUEST    = 40;
    private final static int URL_REQUEST      = 50;
    private final static int CHILD_REQUEST    = 60;
    private final static int TAGS_REQUEST     = 70;
    private static ConfigReviewDataUI                sConfigReviewDataUI;
    private final  HashMap<GVType, ReviewDataConfig> mOptionsMap;

    private ConfigReviewDataUI() {
        mOptionsMap = new HashMap<GVReviewDataList.GVType, ReviewDataConfig>();

        mOptionsMap.put(GVType.TAGS,
                new ReviewDataConfig(DialogTagAddFragment.class, DATA_ADD, DIALOG_TAG_ADD_TAG,
                        DialogTagEditFragment.class, DATA_EDIT, DIALOG_TAG_EDIT_TAG,
                        ActivityReviewTags.class, TAGS_REQUEST, new VHTag()));
        mOptionsMap.put(GVType.CHILDREN,
                new ReviewDataConfig(DialogChildAddFragment.class, DATA_ADD, DIALOG_CHILD_ADD_TAG,
                        DialogChildEditFragment.class, DATA_EDIT, DIALOG_CHILD_EDIT_TAG,
                        ActivityReviewChildren.class, CHILD_REQUEST,
                        new VHReviewNodeSubjectRating()));
        mOptionsMap.put(GVType.COMMENTS,
                new ReviewDataConfig(DialogCommentAddFragment.class, DATA_ADD,
                        DIALOG_COMMENT_ADD_TAG,
                        DialogCommentEditFragment.class, DATA_EDIT, DIALOG_COMMENT_EDIT_TAG,
                        ActivityReviewComments.class, COMMENT_REQUEST,
                        new VHComment()));
        mOptionsMap.put(GVType.IMAGES,
                new ReviewDataConfig(DialogImageEditFragment.class, DATA_ADD, DIALOG_IMAGE_ADD_TAG,
                        DialogImageEditFragment.class, DATA_EDIT, DIALOG_IMAGE_EDIT_TAG,
                        ActivityReviewImages.class, IMAGE_REQUEST, new VHImage()));
        mOptionsMap.put(GVType.FACTS,
                new ReviewDataConfig(DialogFactAddFragment.class, DATA_ADD, DIALOG_FACTS_ADD_TAG,
                        DialogFactEditFragment.class, DATA_EDIT, DIALOG_FACTS_EDIT_TAG,
                        ActivityReviewFacts.class, FACTS_REQUEST, new VHFact()));
        mOptionsMap.put(GVType.LOCATIONS,
                new ReviewDataConfig(DialogLocationFragment.class, LOCATION_ADD,
                        DIALOG_LOCATION_ADD_TAG,
                        DialogLocationFragment.class, DATA_EDIT,
                        DIALOG_LOCATION_EDIT_TAG,
                        ActivityReviewLocations.class, LOCATION_REQUEST,
                        new VHLocation(true)));
        mOptionsMap.put(GVType.URLS,
                new ReviewDataConfig(DialogURLFragment.class, DATA_ADD, DIALOG_URL_ADD_TAG,
                        DialogURLFragment.class, DATA_EDIT, DIALOG_URL_EDIT_TAG,
                        ActivityReviewURLs.class, URL_REQUEST, new VHUrl()));

    }

    static ReviewDataConfig get(GVType dataType) {
        return getOptions().mOptionsMap.get(dataType);
    }

    private static ConfigReviewDataUI getOptions() {
        if (sConfigReviewDataUI == null) {
            sConfigReviewDataUI = new ConfigReviewDataUI();
        }

        return sConfigReviewDataUI;
    }

    /**
     * Encapsulates UI configs for various data types.
     */
    class ReviewDataConfig {
        private final Class<? extends DialogFragment> mDialogAddClass;
        private final int                             mDialogAddRequestCode;
        private final String                          mDialogAddTag;

        private final Class<? extends DialogFragment> mDialogEditClass;
        private final int                             mDialogEditRequestCode;
        private final String                          mDialogEditTag;

        private final Class<? extends Activity> mActivityClass;
        private final int                       mActivityRequestCode;


        private final ViewHolder mViewHolder;

        private ReviewDataConfig(Class<? extends DialogFragment> dialogAddDataClass,
                                 int dialogAddDataRequestCode, String dialogAddDataTag,
                                 Class<? extends DialogFragment> dialogEditDataClass,
                                 int dialogEditDataRequestCode, String dialogEditDataTag,
                                 Class<? extends Activity> activityDataClass,
                                 int activityDataRequestCode, ViewHolder viewHolder) {
            mDialogAddClass = dialogAddDataClass;
            mDialogAddRequestCode = dialogAddDataRequestCode;
            mDialogAddTag = dialogAddDataTag;
            mDialogEditClass = dialogEditDataClass;
            mDialogEditRequestCode = dialogEditDataRequestCode;
            mDialogEditTag = dialogEditDataTag;
            mActivityClass = activityDataClass;
            mActivityRequestCode = activityDataRequestCode;
            mViewHolder = viewHolder;
        }

        DialogFragment getDialogEditFragment() throws RuntimeException {
            return getDialogFragment(mDialogEditClass);
        }

        DialogFragment getDialogAddFragment() throws RuntimeException {
            return getDialogFragment(mDialogAddClass);
        }

        private DialogFragment getDialogFragment(Class<? extends DialogFragment> dialogClass)
                throws RuntimeException {
            try {
                return dialogClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "Couldn't create dialog for " + dialogClass.getName(), e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "IllegalAccessEception: trying to create " + dialogClass.getName()
                        , e);
                throw new RuntimeException(e);
            }
        }

        Intent requestActivityIntent(Context context) {
            return new Intent(context, mActivityClass);
        }

        int getActivityRequestCode() {
            return mActivityRequestCode;
        }

        int getDialogAddRequestCode() {
            return mDialogAddRequestCode;
        }

        int getDialogEditRequestCode() {
            return mDialogEditRequestCode;
        }

        String getDialogAddDataTag() {
            return mDialogAddTag;
        }

        String getDialogEditDataTag() {
            return mDialogEditTag;
        }

        ViewHolder getViewHolder() {
            return mViewHolder;
        }
    }
}
