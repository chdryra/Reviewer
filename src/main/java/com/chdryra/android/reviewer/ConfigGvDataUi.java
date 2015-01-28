/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link ConfigGvDataAddEditDisplay}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 *
 * @see com.chdryra.android.reviewer.FragmentReviewBuild;
 */
public final class ConfigGvDataUi {
    private final static String TAG             = "ConfigReviewDataUI";
    private final static int    DATA_ADD        = 2718;
    private final static int    DATA_EDIT       = 2719;
    private static       int    REQUEST_COUNTER = 2720;
    private static ConfigGvDataUi sConfigGvDataUi;

    private final Map<GvDataList.GvType, Config> mConfigsMap = new HashMap<GvDataList
            .GvType,
            Config>();

    private ConfigGvDataUi() {
        mConfigsMap.put(GvDataList.GvType.TAGS, new Config(GvDataList.GvType.TAGS));
        mConfigsMap.put(GvDataList.GvType.CHILDREN, new Config(GvDataList.GvType
                .CHILDREN));
        mConfigsMap.put(GvDataList.GvType.COMMENTS, new Config(GvDataList.GvType
                .COMMENTS));
        mConfigsMap.put(GvDataList.GvType.IMAGES, new Config(GvDataList.GvType.IMAGES));
        mConfigsMap.put(GvDataList.GvType.FACTS, new Config(GvDataList.GvType.FACTS));
        mConfigsMap.put(GvDataList.GvType.LOCATIONS, new Config(GvDataList.GvType
                .LOCATIONS));
        mConfigsMap.put(GvDataList.GvType.URLS, new Config(GvDataList.GvType.URLS));
        mConfigsMap.put(GvDataList.GvType.REVIEW, new Config(GvDataList.GvType.REVIEW));
        mConfigsMap.put(GvDataList.GvType.SOCIAL, new Config(GvDataList.GvType.SOCIAL));
    }

    public static Config getConfig(GvDataList.GvType dataType) {
        return getConfigsMap().get(dataType);
    }

    public static LaunchableUi getLaunchable(Class<? extends LaunchableUi> uiClass) throws
            RuntimeException {
        if (uiClass == null) return null;

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

    private static Map<GvDataList.GvType, Config> getConfigsMap() {
        if (sConfigGvDataUi == null) {
            sConfigGvDataUi = new ConfigGvDataUi();
        }

        return sConfigGvDataUi.mConfigsMap;
    }

    /**
     * Encapsulates add, edit and display configs for a given
     * {@link GvDataList.GvType}.
     */
    public class Config {
        private final GvDataList.GvType       mDataType;
        private final GvDataUiConfig          mAddConfig;
        private final GvDataUiConfig          mEditConfig;
        private final GvDataListDisplayConfig mDisplayConfig;

        private Config(GvDataList.GvType dataType) {
            mDataType = dataType;
            mAddConfig = initAddConfig();
            mEditConfig = initEditConfig();
            mDisplayConfig = initDisplayConfig();
        }

        public GvDataList.GvType getGVType() {
            return mDataType;
        }

        public GvDataUiConfig getAdderConfig() {
            return mAddConfig;
        }

        public GvDataUiConfig getEditorConfig() {
            return mEditConfig;
        }

        public GvDataListDisplayConfig getDisplayConfig() {
            return mDisplayConfig;
        }

        private GvDataUiConfig initAddConfig() {
            return new GvDataUiConfig(mDataType, ConfigGvDataAddEditDisplay.getAddClass
                    (mDataType), DATA_ADD, mDataType.getDatumString().toUpperCase() + "_ADD_TAG");
        }

        private GvDataUiConfig initEditConfig() {
            return new GvDataUiConfig(mDataType, ConfigGvDataAddEditDisplay.getEditClass
                    (mDataType), DATA_EDIT, mDataType.getDatumString().toUpperCase() + "_EDIT_TAG");
        }

        private GvDataListDisplayConfig initDisplayConfig() {
            return new GvDataListDisplayConfig(mDataType, REQUEST_COUNTER++);
        }
    }

    /**
     * Encapsulates a configuration for a UI that can add or edit review data of a certain
     * {@link GvDataList.GvType}. Packages together:
     * <ul>
     * <li>A {@link LaunchableUi} implementation for
     * adding/editing review data of a certain type</li>
     * <li>An integer request code (required when one activity launches another)</li>
     * <li>A String tag that may be used (if ultimately launching a dialog)</li>
     * </ul>
     * The ReviewDataUI is launched using a
     * {@link LauncherUi}
     */
    public class GvDataUiConfig {
        private final GvDataList.GvType             mDataType;
        private final Class<? extends LaunchableUi> mUiClass;
        private final int                           mRequestCode;
        private final String                        mTag;

        private GvDataUiConfig(GvDataList.GvType dataType, Class<? extends LaunchableUi>
                UiClass, int requestCode, String tag) {
            mDataType = dataType;
            mUiClass = UiClass;
            mRequestCode = requestCode;
            mTag = tag;
        }


        public GvDataList.GvType getGVType() {
            return mDataType;
        }

        public LaunchableUi getReviewDataUI() throws RuntimeException {
            return ConfigGvDataUi.getLaunchable(mUiClass);
        }

        public int getRequestCode() {
            return mRequestCode;
        }

        public String getTag() {
            return mTag;
        }
    }

    /**
     * Encapsulates a configuration for displaying a {@link com.chdryra.android.reviewer.GvDataList}
     * of a certain {@link GvDataList.GvType}. Packages together:
     * <ul>
     * <li>An activity class for displaying a collection of review data of a certain
     * type</li>
     * <li>An integer request code (required when one activity launches another)</li>
     * </ul>
     * The display activity is accessed by requesting an Intent object which can be used to start
     * activities via, for example, <code>startActivityForResult(.)</code> etc.
     */
    public class GvDataListDisplayConfig {
        private final GvDataList.GvType mDataType;
        private final int               mRequestCode;

        private GvDataListDisplayConfig(GvDataList.GvType dataType, int requestCode) {
            mDataType = dataType;
            mRequestCode = requestCode;
        }

        public Intent requestIntent(Context context) {
            return new Intent(context, ConfigGvDataAddEditDisplay.getDisplayClass(mDataType));
        }

        public int getRequestCode() {
            return mRequestCode;
        }
    }
}
