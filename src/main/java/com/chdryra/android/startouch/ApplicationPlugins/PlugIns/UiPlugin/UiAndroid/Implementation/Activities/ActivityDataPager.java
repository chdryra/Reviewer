/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.NodeDataPagerAdapter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityDataPager<Data extends HasReviewId, FragmentType
        extends NodeDataPagerAdapter.DataFragment<Data>>
        extends FragmentActivity implements LaunchableUi,
        NodeDataPagerAdapter.FragmentFactory<FragmentType> {
    private static final String TAG = TagKeyGenerator.getTag(ActivityDataPager.class);
    private static final String RETAIN_VIEW
            = TagKeyGenerator.getKey(ActivityDataPager.class, "RetainView");
    private static final int LAYOUT = R.layout.view_pager;
    private static final int PAGER = R.id.pager;

    private NodeDataPagerAdapter<Data, FragmentType> mAdapter;
    private ViewPager mPager;

    protected abstract RefDataList<Data> getData(ReviewNode node);

    public void onFragmentReady(FragmentType fragment) {
        mAdapter.onFragmentReady(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppInstanceAndroid.setActivity(this);
        setContentView(LAYOUT);

        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(this);
        ReviewNode node = app.unpackNode(args);
        if (node == null) {
            noReview();
            return;
        }

        mPager = findViewById(PAGER);
        mAdapter = new NodeDataPagerAdapter<>(getSupportFragmentManager(), getData(node), this);
        mPager.setCurrentItem(NodeLauncher.getIndex(args));
        mPager.setAdapter(mAdapter);
        mPager.addOnLayoutChangeListener(new FragmentInitialiser(app.getUi()));
    }

    @Override
    public abstract FragmentType newFragment(String pageId);

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
    private FragmentType getVisibleFragment() {
        return mAdapter.getFragment(mPager.getCurrentItem());
    }

    private void noReview() {
        Toast.makeText(this, "No review found", Toast.LENGTH_SHORT).show();
        finish();
    }

    private class FragmentInitialiser implements ViewPager.OnLayoutChangeListener, ViewPager
            .OnPageChangeListener {
        private UiSuite mUi;

        private FragmentInitialiser(UiSuite ui) {
            mUi = ui;
        }

        @Override
        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int
                i6, int i7) {
            intialise();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            intialise();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        private void intialise() {
            FragmentType fragment = getVisibleFragment();
            if (fragment != null) mUi.getCurrentScreen().setTitle(mAdapter.getTitle(fragment));
        }
    }
}
