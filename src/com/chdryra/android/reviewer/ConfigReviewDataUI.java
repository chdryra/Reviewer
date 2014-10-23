/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
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

    private HashMap<GVType, Config> mOptionsMap;

    private ConfigReviewDataUI() {
        mOptionsMap = new HashMap<GVReviewDataList.GVType, Config>();

        ReviewDataUIConfig addConfig;
        ReviewDataUIConfig editConfig;
        ReviewDataActivityConfig activityConfig;

        //Tags
        addConfig = new ReviewDataUIConfig(GVType.TAGS,
                ConfigAddEditActivity.getAddClass(GVType.TAGS), DATA_ADD, DIALOG_TAG_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.TAGS,
                ConfigAddEditActivity.getEditClass(GVType.TAGS), DATA_EDIT, DIALOG_TAG_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.TAGS,
                ConfigAddEditActivity.getActivityClass(GVType.TAGS), TAGS_REQUEST, new VHTag());
        Config tagConfig = new Config(addConfig, editConfig, activityConfig);

        //Children
        addConfig = new ReviewDataUIConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getAddClass(GVType.CHILDREN), DATA_ADD, DIALOG_CHILD_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getEditClass(GVType.CHILDREN), DATA_EDIT,
                DIALOG_CHILD_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.CHILDREN,
                ConfigAddEditActivity.getActivityClass(GVType.CHILDREN), CHILD_REQUEST,
                new VHReviewNodeSubjectRating());
        Config childConfig = new Config(addConfig, editConfig, activityConfig);

        //Comments
        addConfig = new ReviewDataUIConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getAddClass(GVType.COMMENTS), DATA_ADD,
                DIALOG_COMMENT_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getEditClass(GVType.COMMENTS), DATA_EDIT,
                DIALOG_COMMENT_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.COMMENTS,
                ConfigAddEditActivity.getActivityClass(GVType.COMMENTS), COMMENT_REQUEST,
                new VHComment());
        Config commentConfig = new Config(addConfig, editConfig,
                activityConfig);

        //Images
        addConfig = new ReviewDataUIConfig(GVType.IMAGES,
                ConfigAddEditActivity.getAddClass(GVType.IMAGES), DATA_ADD, DIALOG_IMAGE_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.IMAGES,
                ConfigAddEditActivity.getEditClass(GVType.IMAGES), DATA_EDIT,
                DIALOG_IMAGE_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.IMAGES,
                ConfigAddEditActivity.getActivityClass(GVType.IMAGES), IMAGE_REQUEST,
                new VHImage());
        Config imageConfig = new Config(addConfig, editConfig,
                activityConfig);

        //Facts
        addConfig = new ReviewDataUIConfig(GVType.FACTS,
                ConfigAddEditActivity.getAddClass(GVType.FACTS), DATA_ADD, DIALOG_FACTS_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.FACTS,
                ConfigAddEditActivity.getEditClass(GVType.FACTS), DATA_EDIT, DIALOG_FACTS_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.FACTS,
                ConfigAddEditActivity.getActivityClass(GVType.FACTS), FACTS_REQUEST, new VHFact());
        Config factConfig = new Config(addConfig, editConfig,
                activityConfig);

        //***Locations/URLs work in a different way so don't fit withing the AddEdit framework***
        //Locations
        addConfig = new ReviewDataUIConfig(GVType.LOCATIONS,
                ConfigAddEditActivity.getAddClass(GVType.LOCATIONS), LOCATION_ADD,
                DIALOG_LOCATION_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.LOCATIONS,
                ConfigAddEditActivity.getEditClass(GVType.LOCATIONS), DATA_EDIT,
                DIALOG_LOCATION_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.LOCATIONS,
                ConfigAddEditActivity.getActivityClass(GVType.LOCATIONS), LOCATION_REQUEST,
                new VHLocation(true));
        Config locationConfig = new Config(addConfig, editConfig,
                activityConfig);

        //URLs
        addConfig = new ReviewDataUIConfig(GVType.URLS,
                ConfigAddEditActivity.getAddClass(GVType.URLS), DATA_ADD, DIALOG_URL_ADD_TAG);
        editConfig = new ReviewDataUIConfig(GVType.URLS,
                ConfigAddEditActivity.getEditClass(GVType.URLS), DATA_EDIT, DIALOG_URL_EDIT_TAG);
        activityConfig = new ReviewDataActivityConfig(GVType.URLS,
                ConfigAddEditActivity.getActivityClass(GVType.URLS), URL_REQUEST, new VHUrl());
        Config UrlConfig = new Config(addConfig, editConfig,
                activityConfig);

        mOptionsMap.put(GVType.TAGS, tagConfig);
        mOptionsMap.put(GVType.CHILDREN, childConfig);
        mOptionsMap.put(GVType.COMMENTS, commentConfig);
        mOptionsMap.put(GVType.IMAGES, imageConfig);
        mOptionsMap.put(GVType.FACTS, factConfig);
        mOptionsMap.put(GVType.LOCATIONS, locationConfig);
        mOptionsMap.put(GVType.URLS, UrlConfig);
    }

    static Config get(GVType dataType) {
        return getOptionsMap().get(dataType);
    }

    private static HashMap<GVType, Config> getOptionsMap() {
        if (sConfigReviewDataUI == null) {
            sConfigReviewDataUI = new ConfigReviewDataUI();
        }

        return sConfigReviewDataUI.mOptionsMap;
    }

    static ReviewDataUI getReviewDataUI(Class<? extends ReviewDataUI> uiClass)
            throws RuntimeException {
        try {
            return uiClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + uiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessEception: trying to create " + uiClass.getName()
                    , e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Encapsulates UI configs for various data types.
     */
    class Config {
        private final ReviewDataUIConfig       mAddConfig;
        private final ReviewDataUIConfig       mEditConfig;
        private final ReviewDataActivityConfig mActivityConfig;

        private Config(ReviewDataUIConfig addConfig,
                       ReviewDataUIConfig editConfig,
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

        ReviewDataUIConfig getAdderConfig() {
            return mAddConfig;
        }

        ReviewDataUIConfig getEditorConfig() {
            return mEditConfig;
        }

        ReviewDataActivityConfig getActivityConfig() {
            return mActivityConfig;
        }
    }

    class ReviewDataUIConfig {
        private final GVType                        mDataType;
        private final Class<? extends ReviewDataUI> mUIClass;
        private final int                           mRequestCode;
        private final String                        mTag;

        private ReviewDataUIConfig(GVType dataType, Class<? extends ReviewDataUI> UIClass,
                                   int requestCode, String tag) {
            mDataType = dataType;
            mUIClass = UIClass;
            mRequestCode = requestCode;
            mTag = tag;
        }

        GVType getGVType() {
            return mDataType;
        }

        ReviewDataUI getReviewDataUI() throws RuntimeException {
            return ConfigReviewDataUI.getReviewDataUI(mUIClass);
        }

        int getRequestCode() {
            return mRequestCode;
        }

        String getTag() {
            return mTag;
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
