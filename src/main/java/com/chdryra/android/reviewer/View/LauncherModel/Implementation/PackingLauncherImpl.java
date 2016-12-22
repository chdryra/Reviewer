/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.CacheUtils.ItemPacker;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.PackingLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PackingLauncherImpl<T> implements PackingLauncher<T> {
    private final LaunchableConfig mUi;
    private final ItemPacker<T> mPacker;

    public PackingLauncherImpl(LaunchableConfig ui) {
        mUi = ui;
        mPacker = new ItemPacker<>();
    }

    @Override
    public void launch(@Nullable T item) {
        launch(item, new Bundle());
    }

    void launch(@Nullable T item, Bundle args) {
        if (item != null) mPacker.pack(item, args);
        onPrelaunch();
        launchUi(args);
    }

    @Override
    public void setUiLauncher(UiLauncher uiLauncher) {
        mUi.setLauncher(uiLauncher);
    }

    @Override
    public T unpack(Bundle args) {
        return mPacker.unpack(args);
    }

    void onPrelaunch() {

    }

    private void launchUi(Bundle args) {
        mUi.launch(new UiLauncherArgs(mUi.getDefaultRequestCode()).setBundle(args));
    }
}
