/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.corelibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentProfileEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentAuthorView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

public class ActivityAuthorView extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = TagKeyGenerator.getTag(ActivityAuthorView.class);
    private static final String KEY = TagKeyGenerator.getKey(ActivityAuthorView.class, "Key");

    private ActivityResultListener mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), KEY);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        Bundle bundleArgs = getIntent().getBundleExtra(KEY);
        ParcelablePacker<GvAuthorId> unpacker = new ParcelablePacker<>();
        GvAuthorId args = unpacker.unpack(ParcelablePacker.CurrentNewDatum.CURRENT, bundleArgs);
        mFragment = args != null ? FragmentAuthorView.newInstance(args) : FragmentProfileEdit.newInstance();
        return (Fragment) mFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppInstanceAndroid.setActivity(this);
    }
}
