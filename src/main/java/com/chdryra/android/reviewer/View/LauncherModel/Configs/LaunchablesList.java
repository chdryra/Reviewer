package com.chdryra.android.reviewer.View.LauncherModel.Configs;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LaunchablesList {
    private final ArrayList<AddEditViewClasses<?>> mDataLaunchables = new ArrayList<>();
    private final Class<? extends LaunchableUi> mReviewBuilderLaunchable;
    private final Class<? extends LaunchableUi> mMapEditorLaunchable;
    private final Class<? extends LaunchableUi> mShareLaunchable;

    public LaunchablesList(Class<? extends LaunchableUi> reviewBuilderLaunchable, Class<? extends
            LaunchableUi> mapEditorLaunchable, Class<? extends LaunchableUi> shareLaunchable) {
        mReviewBuilderLaunchable = reviewBuilderLaunchable;
        mMapEditorLaunchable = mapEditorLaunchable;
        mShareLaunchable = shareLaunchable;
    }

    protected <T extends GvData> void addDataClasses(AddEditViewClasses<T> classes) {
        mDataLaunchables.add(classes);
    }

    public Class<? extends LaunchableUi> getReviewBuilderConfig() {
        return mReviewBuilderLaunchable;
    }

    public Class<? extends LaunchableUi> getMapEditorConfig() {
        return mMapEditorLaunchable;
    }

    public Class<? extends LaunchableUi> getShareConfig() {
        return mShareLaunchable;
    }

    public ArrayList<AddEditViewClasses<?>> getDataLaunchables() {
        return mDataLaunchables;
    }
}
