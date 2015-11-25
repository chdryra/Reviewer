package com.chdryra.android.reviewer.View.Implementation.Configs;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Packages together add, edit and view UIs.
 */
public class AddEditViewClasses<T extends GvData> {
    private final GvDataType<T> mDataType;
    private final Class<? extends LaunchableUi> mAdd;
    private final Class<? extends LaunchableUi> mEdit;
    private final Class<? extends LaunchableUi> mView;

    public AddEditViewClasses(GvDataType<T> dataType, Class<? extends LaunchableUi> add,
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
