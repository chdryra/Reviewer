package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Dialogs.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.Dialogs.Interfaces.DialogLayout;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Packages together add, edit and view UIs.
 */
public class ClassesHolder<T extends GvData> {
    private final GvDataType<T> mDataType;
    private final Class<? extends LaunchableUi> mAdd;
    private final Class<? extends LaunchableUi> mEdit;
    private final Class<? extends LaunchableUi> mView;
    private final Class<? extends DialogLayout<T>> mViewLayout;
    private final Class<? extends AddEditLayout<T>> mAddEditLayout;

    public ClassesHolder(GvDataType<T> dataType, Class<? extends LaunchableUi> add,
                         Class<? extends LaunchableUi> edit,
                         Class<? extends LaunchableUi> view,
                         Class<? extends DialogLayout<T>> viewLayout,
                         Class<? extends AddEditLayout<T>> addEditLayout) {
        mDataType = dataType;
        mAdd = add;
        mEdit = edit;
        mView = view;
        mViewLayout = viewLayout;
        mAddEditLayout = addEditLayout;
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

    public Class<? extends DialogLayout<T>> getViewLayoutClass() {
        return mViewLayout;
    }

    public Class<? extends AddEditLayout<T>> getAddEditLayoutClass() {
        return mAddEditLayout;
    }
}
