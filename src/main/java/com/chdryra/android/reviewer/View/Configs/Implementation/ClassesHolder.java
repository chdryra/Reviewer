package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;

/**
 * Packages together add, edit and view UIs.
 */
public class ClassesHolder {
    private final Class<? extends LaunchableUi> mAdd;
    private final Class<? extends LaunchableUi> mEdit;
    private final Class<? extends LaunchableUi> mView;

    public ClassesHolder(Class<? extends LaunchableUi> add,
                         Class<? extends LaunchableUi> edit,
                         Class<? extends LaunchableUi> view) {
        mAdd = add;
        mEdit = edit;
        mView = view;
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
