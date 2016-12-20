/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentViewImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.NodeDataPagerAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityViewImages extends FragmentActivity implements LaunchableUi,
        NodeDataPagerAdapter.FragmentFactory<FragmentViewImage> {
    private static final String TAG = TagKeyGenerator.getTag(ActivityViewImages.class);
    private static final String RETAIN_VIEW
            = TagKeyGenerator.getKey(ActivityViewImages.class, "RetainView");
    private static final int LAYOUT = R.layout.view_pager;
    private static final int PAGER = R.id.pager;

    private NodeDataPagerAdapter<DataImage, FragmentViewImage> mAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        if (args == null) throwNoReview();
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(this);
        ReviewNode node = app.unpackNode(args);
        if (node == null) throwNoReview();

        mPager = (ViewPager) findViewById(PAGER);
        mAdapter = new NodeDataPagerAdapter<>(getSupportFragmentManager(), node.getImages(), this);
        mPager.setAdapter(mAdapter);
        mPager.addOnLayoutChangeListener(new Titler(app.getUi()));
    }

    @Override
    public FragmentViewImage newFragment(String pageId, int position) {
        return FragmentViewImage.newInstance(pageId, position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(RETAIN_VIEW, true);
        super.onSaveInstanceState(outState);
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
    protected void onResume() {
        super.onResume();
        AppInstanceAndroid.setActivity(this);
    }

    @Nullable
    private FragmentViewImage getVisibleFragment() {
        return mAdapter.getFragment(mPager.getCurrentItem());
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }

    private class Titler implements ViewPager.OnLayoutChangeListener {
        private UiSuite mUi;

        private Titler(UiSuite ui) {
            mUi = ui;
        }

        @Override
        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int
                i6, int i7) {
            FragmentViewImage fragment = getVisibleFragment();
            if (fragment != null) mUi.getCurrentScreen().setTitle(mAdapter.getTitle(fragment));
        }
    }
}
