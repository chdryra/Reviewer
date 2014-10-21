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
    private static ConfigReviewDataUI sConfigReviewDataUI;

    private HashMap<GVType, ReviewDataConfig> mOptionsMap;

    private ConfigReviewDataUI() {
        mOptionsMap = new HashMap<GVReviewDataList.GVType, ReviewDataConfig>();

        ReviewDataDialogConfig addConfig;
        ReviewDataDialogConfig editConfig;
        ReviewDataActivityConfig activityConfig;

        //Tags
        addConfig = new ReviewDataDialogConfig(GVType.TAGS,
                ConfigAddEditActivity.getAddClass(GVType.TAGS), DATA_ADD, DIALOG_TAG_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.TAGS,
                ConfigAddEditActivity.getEditClass(GVType.TAGS), DATA_EDIT, DIALOG_TAG_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.TAGS,
                ConfigAddEditActivity.getActivityClass(GVType.TAGS), TAGS_REQUEST, new VHTag());
        ReviewDataConfig tagConfig = new ReviewDataConfig(addConfig, editConfig, activityConfig);

        //Children
        addConfig = new ReviewDataDialogConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getAddClass(GVType.CHILDREN), DATA_ADD, DIALOG_CHILD_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getEditClass(GVType.CHILDREN), DATA_EDIT,
                DIALOG_CHILD_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getActivityClass(GVType.CHILDREN), CHILD_REQUEST,
                new VHReviewNodeSubjectRating());
        ReviewDataConfig childConfig = new ReviewDataConfig(addConfig, editConfig, activityConfig);

        //Comments
        addConfig = new ReviewDataDialogConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getAddClass(GVType.COMMENTS), DATA_ADD,
                DIALOG_COMMENT_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getEditClass(GVType.COMMENTS), DATA_EDIT,
                DIALOG_COMMENT_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getActivityClass(GVType.COMMENTS), COMMENT_REQUEST,
                new VHComment());
        ReviewDataConfig commentConfig = new ReviewDataConfig(addConfig, editConfig,
                activityConfig);

        //Images
        addConfig = new ReviewDataDialogConfig(GVType.IMAGES,
                ConfigAddEditActivity.getAddClass(GVType.IMAGES), DATA_ADD, DIALOG_IMAGE_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.IMAGES,
                ConfigAddEditActivity.getEditClass(GVType.IMAGES), DATA_EDIT,
                DIALOG_IMAGE_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.IMAGES,
                ConfigAddEditActivity.getActivityClass(GVType.IMAGES), IMAGE_REQUEST,
                new VHImage());
        ReviewDataConfig imageConfig = new ReviewDataConfig(addConfig, editConfig,
                activityConfig);

        //Facts
        addConfig = new ReviewDataDialogConfig(GVType.FACTS,
                ConfigAddEditActivity.getAddClass(GVType.FACTS), DATA_ADD, DIALOG_FACTS_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.FACTS,
                ConfigAddEditActivity.getEditClass(GVType.FACTS), DATA_EDIT, DIALOG_FACTS_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.FACTS,
                ConfigAddEditActivity.getActivityClass(GVType.FACTS), FACTS_REQUEST, new VHFact());
        ReviewDataConfig factConfig = new ReviewDataConfig(addConfig, editConfig,
                activityConfig);

        //***Locations/URLs work in a different way so don't fit withing the AddEdit framework***
        //Locations
        addConfig = new ReviewDataDialogConfig(GVType.LOCATIONS,
                DialogLocationFragment.class, LOCATION_ADD, DIALOG_LOCATION_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.LOCATIONS,
                DialogLocationFragment.class, DATA_EDIT, DIALOG_LOCATION_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.LOCATIONS,
                ActivityReviewLocations.class, LOCATION_REQUEST, new VHLocation(true));
        ReviewDataConfig locationConfig = new ReviewDataConfig(addConfig, editConfig,
                activityConfig);

        //URLs
        addConfig = new ReviewDataDialogConfig(GVType.URLS,
                DialogURLFragment.class, DATA_ADD, DIALOG_URL_ADD_TAG);
        editConfig = new ReviewDataDialogConfig(GVType.URLS,
                DialogURLFragment.class, DATA_EDIT, DIALOG_URL_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.URLS,
                ActivityReviewURLs.class, URL_REQUEST, new VHUrl());
        ReviewDataConfig UrlConfig = new ReviewDataConfig(addConfig, editConfig,
                activityConfig);

        mOptionsMap.put(GVType.TAGS, tagConfig);
        mOptionsMap.put(GVType.CHILDREN, childConfig);
        mOptionsMap.put(GVType.COMMENTS, commentConfig);
        mOptionsMap.put(GVType.IMAGES, imageConfig);
        mOptionsMap.put(GVType.FACTS, factConfig);
        mOptionsMap.put(GVType.LOCATIONS, locationConfig);
        mOptionsMap.put(GVType.URLS, UrlConfig);
    }

    static ReviewDataConfig get(GVType dataType) {
        return getOptionsMap().get(dataType);
    }

    private static HashMap<GVType, ReviewDataConfig> getOptionsMap() {
        if (sConfigReviewDataUI == null) {
            sConfigReviewDataUI = new ConfigReviewDataUI();
        }

        return sConfigReviewDataUI.mOptionsMap;
    }

    /**
     * Encapsulates UI configs for various data types.
     */
    class ReviewDataConfig {
        private final ReviewDataDialogConfig   mAddConfig;
        private final ReviewDataDialogConfig   mEditConfig;
        private final ReviewDataActivityConfig mActivityConfig;

        private ReviewDataConfig(ReviewDataDialogConfig addConfig,
                                 ReviewDataDialogConfig editConfig,
                                 ReviewDataActivityConfig activityConfig) {
            if (!(addConfig.getGVType() == editConfig.getGVType() && editConfig.getGVType() ==
                    activityConfig.getGVType())) {
                throw new RuntimeException();
            }
            mAddConfig = addConfig;
            mEditConfig = editConfig;
            mActivityConfig = activityConfig;
        }

        GVType getGVType() {
            return mActivityConfig.getGVType();
        }

        ReviewDataDialogConfig getDialogAddConfig() {
            return mAddConfig;
        }

        ReviewDataDialogConfig getDialogEditConfig() {
            return mEditConfig;
        }

        ReviewDataActivityConfig getActivityConfig() {
            return mActivityConfig;
        }
    }

    class ReviewDataDialogConfig {
        private final GVType                          mDataType;
        private final Class<? extends DialogFragment> mDialogClass;
        private final int                             mDialogRequestCode;
        private final String                          mDialogTag;

        private ReviewDataDialogConfig(GVType dataType, Class<? extends DialogFragment> dialogClass,
                                       int dialogRequestCode, String dialogTag) {
            mDataType = dataType;
            mDialogClass = dialogClass;
            mDialogRequestCode = dialogRequestCode;
            mDialogTag = dialogTag;
        }

        GVType getGVType() {
            return mDataType;
        }

        DialogFragment getDialogFragment()
                throws RuntimeException {
            try {
                return mDialogClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "Couldn't create dialog for " + mDialogClass.getName(), e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                //If this happens not good so throwing runtime exception
                Log.e(TAG, "IllegalAccessEception: trying to create " + mDialogClass.getName()
                        , e);
                throw new RuntimeException(e);
            }
        }

        int getRequestCode() {
            return mDialogRequestCode;
        }

        String getTag() {
            return mDialogTag;
        }
    }

    class ReviewDataActivityConfig {
        private final GVType                    mDataType;
        private final Class<? extends Activity> mActivityClass;
        private final int                       mActivityRequestCode;
        private final ViewHolder                mViewHolder;

        private ReviewDataActivityConfig(GVType dataType, Class<? extends Activity> activityClass,
                                         int requestCode, ViewHolder viewHolder) {
            mDataType = dataType;
            mActivityClass = activityClass;
            mActivityRequestCode = requestCode;
            mViewHolder = viewHolder;
        }

        GVType getGVType() {
            return mDataType;
        }

        Intent requestIntent(Context context) {
            return new Intent(context, mActivityClass);
        }

        int getRequestCode() {
            return mActivityRequestCode;
        }

        ViewHolder getViewHolder() {
            return mViewHolder;
        }
    }
}
