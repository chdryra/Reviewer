/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.NodeComparatorMostRecent;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.NodePagerAdapter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.NodeLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityFormatReview extends FragmentActivity implements LaunchableUi,
        OptionSelectListener {
    private static final String TAG = TagKeyGenerator.getTag(ActivityFormatReview.class);
    private static final String RETAIN_VIEW
            = TagKeyGenerator.getKey(ActivityFormatReview.class, "RetainView");
    private static final int LAYOUT = R.layout.view_pager;
    private static final int PAGER = R.id.pager;

    private NodePagerAdapter mAdapter;
    private ViewPager mPager;

    public ReviewNode getNode(ReviewId id) {
        return mAdapter.getNode(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Bundle args = getIntent().getBundleExtra(getLaunchTag());
        if (args == null) throwNoReview();
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(this);
        ReviewNode node = app.unpackNode(args);
        if (node == null) throwNoReview();

        boolean isPublished = NodeLauncher.isPublished(args);

        mPager = (ViewPager) findViewById(PAGER);
        mAdapter = new NodePagerAdapter(node, new NodeComparatorMostRecent(),
                getSupportFragmentManager(), isPublished);
        mPager.setAdapter(mAdapter);
        mPager.addOnLayoutChangeListener(new Titler(app.getUi()));
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
    public boolean onOptionSelected(int requestCode, String option) {
        FragmentFormatReview fragment = getVisibleFragment();
        return fragment != null && fragment.onOptionSelected(requestCode, option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppInstanceAndroid.setActivity(this);
    }

    @Nullable
    private FragmentFormatReview getVisibleFragment() {
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
            FragmentFormatReview fragment = getVisibleFragment();
            if (fragment != null) {
                String title = fragment.isPublished() ?
                        mAdapter.getTitle(fragment) : Strings.Screens.PREVIEW;
                mUi.getCurrentScreen().setTitle(title);
            }
        }
    }
}
