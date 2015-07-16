/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link ConfigGvDataAddEditView}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigGvDataUi {
    public static final GvDataType[] TYPES = {GvCommentList.GvComment.TYPE, GvFactList
            .GvFact.TYPE, GvLocationList.GvLocation.TYPE, GvImageList.GvImage.TYPE, GvUrlList
            .GvUrl.TYPE, GvTagList.GvTag.TYPE, GvChildReviewList.GvChildReview.TYPE};

    private static final int DATA_ADD  = 2718;
    private static final int DATA_EDIT = 2819;
    private static ConfigGvDataUi sConfigGvDataUi;

    private final Map<GvDataType, Config> mConfigsMap = new HashMap<>();

    private ConfigGvDataUi() {
        for (GvDataType type : TYPES) {
            mConfigsMap.put(type, new Config(type));
        }
    }

    public static Config getConfig(GvDataType dataType) {
        return getConfigsMap().get(dataType);
    }

    private static Map<GvDataType, Config> getConfigsMap() {
        if (sConfigGvDataUi == null) {
            sConfigGvDataUi = new ConfigGvDataUi();
        }

        return sConfigGvDataUi.mConfigsMap;
    }

    /**
     * Encapsulates add, edit configs for a given
     * {@link GvDataType}.
     */
    public class Config {
        private final GvDataType       mDataType;
        private final LaunchableConfig mAddConfig;
        private final LaunchableConfig mEditConfig;
        private final LaunchableConfig mViewConfig;

        private Config(GvDataType dataType) {
            mDataType = dataType;
            mAddConfig = initAddConfig();
            mEditConfig = initEditConfig();
            mViewConfig = initViewConfig();
        }

        public GvDataType getGvDataType() {
            return mDataType;
        }

        public LaunchableConfig getAdderConfig() {
            return mAddConfig;
        }

        public LaunchableConfig getEditorConfig() {
            return mEditConfig;
        }

        public LaunchableConfig getViewConfig() {
            return mViewConfig;
        }

        private LaunchableConfig initAddConfig() {
            return new LaunchableConfig(mDataType, ConfigGvDataAddEditView.getAddClass(mDataType),
                    DATA_ADD, mDataType.getDatumName().toUpperCase() + "_ADD_TAG");
        }

        private LaunchableConfig initEditConfig() {
            return new LaunchableConfig(mDataType, ConfigGvDataAddEditView.getEditClass(mDataType),
                    DATA_EDIT, mDataType.getDatumName().toUpperCase() + "_EDIT_TAG");
        }

        private LaunchableConfig initViewConfig() {
            return new LaunchableConfig(mDataType, ConfigGvDataAddEditView.getViewClass(mDataType),
                    DATA_EDIT, mDataType.getDatumName().toUpperCase() + "_VIEW_TAG");
        }
    }

    /**
     * Encapsulates a configuration for a UI that can add or edit review data of a certain
     * {@link GvDataType}. Packages together:
     * <ul>
     * <li>A {@link LaunchableUi} implementation for
     * adding/editing review data of a certain type</li>
     * <li>An integer request code (required when one activity launches another)</li>
     * <li>A String tag that may be used (if ultimately launching a dialog)</li>
     * </ul>
     * The {@link LaunchableUi} is launched using a
     * {@link LauncherUi}
     */
    public class LaunchableConfig {
        private final GvDataType                    mDataType;
        private final Class<? extends LaunchableUi> mUiClass;
        private final int                           mRequestCode;
        private final String                        mTag;

        private LaunchableConfig(GvDataType dataType, Class<? extends LaunchableUi>
                UiClass, int requestCode, String tag) {
            mDataType = dataType;
            mUiClass = UiClass;
            mRequestCode = requestCode;
            mTag = tag;
        }


        public GvDataType getGVType() {
            return mDataType;
        }

        public LaunchableUi getLaunchable() {
            return FactoryLaunchable.newLaunchable(mUiClass);
        }

        public int getRequestCode() {
            return mRequestCode;
        }

        public String getTag() {
            return mTag;
        }
    }
}
