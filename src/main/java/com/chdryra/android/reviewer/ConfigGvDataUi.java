/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link ConfigGvDataAddEdit}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigGvDataUi {
    private final static String TAG       = "ConfigReviewDataUI";
    private final static int    DATA_ADD  = 2718;
    private final static int    DATA_EDIT = 2819;
    private static ConfigGvDataUi sConfigGvDataUi;

    private final Map<GvDataList.GvType, Config> mConfigsMap = new HashMap<GvDataList
            .GvType,
            Config>();

    private ConfigGvDataUi() {
        mConfigsMap.put(GvDataList.GvType.TAGS, new Config(GvDataList.GvType.TAGS));
        mConfigsMap.put(GvDataList.GvType.CHILDREN, new Config(GvDataList.GvType.CHILDREN));
        mConfigsMap.put(GvDataList.GvType.COMMENTS, new Config(GvDataList.GvType.COMMENTS));
        mConfigsMap.put(GvDataList.GvType.IMAGES, new Config(GvDataList.GvType.IMAGES));
        mConfigsMap.put(GvDataList.GvType.FACTS, new Config(GvDataList.GvType.FACTS));
        mConfigsMap.put(GvDataList.GvType.LOCATIONS, new Config(GvDataList.GvType.LOCATIONS));
        mConfigsMap.put(GvDataList.GvType.URLS, new Config(GvDataList.GvType.URLS));
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
     * Encapsulates add, edit configs for a given
     * {@link GvDataList.GvType}.
     */
    public class Config {
        private final GvDataList.GvType mDataType;
        private final LaunchableConfig  mAddConfig;
        private final LaunchableConfig  mEditConfig;

        private Config(GvDataList.GvType dataType) {
            mDataType = dataType;
            mAddConfig = initAddConfig();
            mEditConfig = initEditConfig();
        }

        public GvDataList.GvType getGvType() {
            return mDataType;
        }

        public LaunchableConfig getAdderConfig() {
            return mAddConfig;
        }

        public LaunchableConfig getEditorConfig() {
            return mEditConfig;
        }

        private LaunchableConfig initAddConfig() {
            return new LaunchableConfig(mDataType, ConfigGvDataAddEdit.getAddClass(mDataType),
                    DATA_ADD, mDataType.getDatumString().toUpperCase() + "_ADD_TAG");
        }

        private LaunchableConfig initEditConfig() {
            return new LaunchableConfig(mDataType, ConfigGvDataAddEdit.getEditClass(mDataType),
                    DATA_EDIT, mDataType.getDatumString().toUpperCase() + "_EDIT_TAG");
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
     * The {@link com.chdryra.android.reviewer.LaunchableUi} is launched using a
     * {@link LauncherUi}
     */
    public class LaunchableConfig {
        private final GvDataList.GvType             mDataType;
        private final Class<? extends LaunchableUi> mUiClass;
        private final int                           mRequestCode;
        private final String                        mTag;

        private LaunchableConfig(GvDataList.GvType dataType, Class<? extends LaunchableUi>
                UiClass, int requestCode, String tag) {
            mDataType = dataType;
            mUiClass = UiClass;
            mRequestCode = requestCode;
            mTag = tag;
        }


        public GvDataList.GvType getGVType() {
            return mDataType;
        }

        public LaunchableUi getLaunchable() throws RuntimeException {
            return ConfigGvDataUi.getLaunchable(mUiClass);
        }

        public int getRequestCode() {
            return mRequestCode;
        }

        public String getTag() {
            return mTag;
        }
    }
}
