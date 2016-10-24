/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityReviewView;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.BannerButtonEditUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ContextualUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.CoverUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.GridViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.MenuUi;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.RatingBarUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.SubjectUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.ViewUi;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.R;

import static com.google.android.gms.plus.model.people.Person.Cover.Layout.BANNER;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentReviewFormatted extends Fragment {
    private static final int LAYOUT = R.layout.fragment_review_formatted;
    private static final int HEADER = R.id.header_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int STAMP = R.id.stamp_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int IMAGES = R.id.images_formatted;
    private static final int FACTS = R.id.facts_formatted;

    private SubjectUi<TextView> mSubject;
    private RatingBarUi mRatingBar;
    private BannerButtonEditUi mBannerButton;
    private GridViewUi<?> mGridView;
    private ContextualUi mContextual;
    private MenuUi mMenu;
    private CoverUi mCover;

    private ReviewWrapper mReview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        Bundle args = getArguments();
        Review review = null;
        if(args != null) review = app.unpackReview(args);
        if(review == null) throw new RuntimeException("No review found passed");
        mReview = new ReviewWrapper(review);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        mSubject = new SubjectUi<>((TextView) v.findViewById(SUBJECT), mReview);
        mRatingBar = new RatingBarUi(mReview, (RatingBar) v.findViewById(RATING));
        int colour = mSubject.getTextColour();
        mBannerButton = new BannerButtonEditUi(mReviewView, (Button) v.findViewById(BANNER), colour);
        mGridView = new GridViewUi<>(mReviewView, (GridView) v.findViewById(GRID), new
                FactoryGridCellAdapter(getActivity()), displayMetrics);
        mMenu = new MenuUi(mReviewView);
        mCover = new CoverUi(mReviewView, (RelativeLayout) v.findViewById(FULL_VIEW),
                mGridView, getActivity());
        mContextual = new ContextualUi(mReviewView,
                (LinearLayout) v.findViewById(CONTEXTUAL_VIEW), CONTEXTUAL_BUTTON, colour);

        return v;
    }

    private void extractReviewFormatted() {
        ActivityReviewView activity;
        try {
            activity = (ActivityReviewView) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity must be an ActivityReviewView", e);
        }

        mReviewView = activity.getReviewView();
    }

    private class ReviewWrapper implements ViewUi.ValueGetter, RatingBarUi.FloatUpdater {
        private Review mReview;

        public ReviewWrapper(Review review) {
            mReview = review;
        }

        @Override
        public String getString() {
            return mReview.getSubject().getSubject();
        }

        @Override
        public float getRating() {
            return mReview.getRating().getRating();
        }

    }
}
