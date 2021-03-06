/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.chdryra.android.corelibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentEditLocationMap;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentNodeMapper;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityNodeMapper extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = TagKeyGenerator.getTag(ActivityNodeMapper.class);
    private ReviewNode mNode;

    public ReviewNode getReviewNode() {
        return mNode;
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), getLaunchTag());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    protected Fragment createFragment(Bundle savedInstanceState) {
        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        ReviewNode node = getApp().unpackNode(args);
        if (node == null) return noReview();

        mNode = node;
        return FragmentNodeMapper.newInstance(NodeLauncher.isPublished(args));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        getApp().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(this);
    }

    private Fragment noReview() {
        try {
            Toast.makeText(this, "No review found", Toast.LENGTH_SHORT).show();
            return FragmentNodeMapper.newInstance(false);
        } finally {
            finish();
        }
    }
}
