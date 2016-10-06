/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Packages together add, edit and view UIs.
 */
public class DataLaunchables<T extends GvData> {
    private final GvDataType<T> mDataType;
    private final Class<? extends LaunchableUi> mAdd;
    private final Class<? extends LaunchableUi> mEdit;
    private final Class<? extends LaunchableUi> mView;

    public DataLaunchables(GvDataType<T> dataType,
                           Class<? extends LaunchableUi> add,
                           Class<? extends LaunchableUi> edit,
                           Class<? extends LaunchableUi> view) {
        mDataType = dataType;
        mAdd = add;
        mEdit = edit;
        mView = view;
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    public Class<? extends LaunchableUi> getAddClass() {
        return mAdd;
    }

    public Class<? extends LaunchableUi> getEditClass() {
        return mEdit;
    }

    public Class<? extends LaunchableUi> getViewClass() {
        return mView;
    }
}
