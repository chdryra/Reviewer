/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentNodeMapper;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityNodeMapper extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = TagKeyGenerator.getTag(ActivityNodeMapper.class);
    private static final String NODE = TagKeyGenerator.getKey(ActivityNodeMapper.class, "Node");

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(getClass(), NODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
    }

    @Override
    protected Fragment createFragment() {
        FragmentNodeMapper fragment = new FragmentNodeMapper();
        fragment.setNode(getReviewNode());
        return fragment;
    }

    @NonNull
    private ReviewNode getReviewNode() {
        Bundle args = getIntent().getBundleExtra(NODE);
        if (args == null) throwNoReview();
        ReviewNode node = AppInstanceAndroid.getInstance(this).unpackNode(args);
        if (node == null) throwNoReview();
        return node;
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppInstanceAndroid.setActivity(this);
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }
}
