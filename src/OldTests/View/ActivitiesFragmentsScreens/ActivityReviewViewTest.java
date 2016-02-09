/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.View.ActivitiesFragmentsScreens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.GridView;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ActivityReviewViewTest<T extends GvData> extends
        ActivityInstrumentationTestCase2<ActivityReviewView> {
    protected ReviewViewAdapter<T> mAdapter;
    protected Activity mActivity;
    protected Solo mSolo;

    protected abstract void setAdapter();

    //Constructors
    public ActivityReviewViewTest() {
        super(ActivityReviewView.class);
    }

    @SmallTest
    public void testSubjectRating() {
        FragmentReviewView fragment = getFragmentViewReview();
        assertEquals(mAdapter.getSubject(), fragment.getSubject());
        assertEquals(mAdapter.getRating(), fragment.getRating());
    }

    //protected methods
    protected abstract ReviewView getView();

    protected int getGridSize() {
        return getGridView().getAdapter().getCount();
    }

    protected GridView getGridView() {
        ArrayList views = mSolo.getCurrentViews(GridView.class);
        assertEquals(1, views.size());
        return (GridView) views.get(0);
    }

    protected FragmentReviewView getFragmentViewReview() {
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment f = manager.findFragmentById(ActivityReviewView.FRAGMENT_ID);
        return (FragmentReviewView) f;
    }

    protected T getGridItem(int position) {
        //TODO make type safe
        return (T) getGridView().getItemAtPosition(position);
    }

    //Overridden
    @Override
    protected void setUp() {
        getInstrumentation().setInTouchMode(false);

        setAdapter();

        Intent i = new Intent();
        ReviewViewPacker.packView(getInstrumentation().getTargetContext(), getView(), i);
        setActivityIntent(i);
        mActivity = getActivity();

        mSolo = new Solo(getInstrumentation(), mActivity);
    }

    @Override
    protected void tearDown() throws Exception {
        if (mActivity != null) mActivity.finish();
    }
}
