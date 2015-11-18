package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;

/**
 * Encapsulates a configuration for a UI that can add, edit, view review data of a certain
 * {@link GvDataType}. Packages together:
 * <ul>
 * <li>A {@link LaunchableUi} implementation for
 * adding/editing review data of a certain type</li>
 * <li>An integer request code (required when one activity launches another)</li>
 * <li>A String tag that may be used (if ultimately launching a dialog)</li>
 * </ul>
 * The {@link LaunchableUi} is launched using a
 * {@link LauncherUiImpl}
 */
public class LaunchableConfigImpl<T extends GvData> implements LaunchableConfig<T> {
    private final GvDataType<T> mDataType;
    private final Class<? extends LaunchableUi> mUiClass;
    private final int mRequestCode;
    private final String mTag;

    public LaunchableConfigImpl(GvDataType<T> dataType,
                                Class<? extends LaunchableUi> UiClass,
                                int requestCode, String tag) {
        mDataType = dataType;
        mUiClass = UiClass;
        mRequestCode = requestCode;
        mTag = tag;
    }


    //public methods
    @Override
    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    @Override
    public LaunchableUi getLaunchable(FactoryLaunchableUi launchableFactory) {
        return launchableFactory.newLaunchable(mUiClass);
    }

    @Override
    public int getRequestCode() {
        return mRequestCode;
    }

    @Override
    public String getTag() {
        return mTag;
    }
}
