/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewEditFragmentLayout implements ReviewViewLayout {
    private static final int LAYOUT = R.layout.fragment_review_edit;
    private static final int SUBJECT = R.id.subject_edit_text;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXTUAL_VIEW = R.id.contextual_view;
    private static final int CONTEXTUAL_BUTTON = R.id.contextual_button;

    private View mView;

    private MenuUi mMenu;
    private SimpleViewUi<?, Bitmap> mCover;
    private SubjectUi<?> mSubject;
    private SimpleViewUi<?, Float> mRatingBar;
    private ViewUi<?, ?> mBannerButton;
    private DataViewUi<?, ?> mDataView;
    private ViewUi<?, ?> mContextual;

    @Override
    public View inflateLayout(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(LAYOUT, container, false);
        return mView;
    }

    @Override
    public <T extends GvData> void attachReviewView(ReviewView<T> reviewView,
                                                    CellDimensionsCalculator calculator) {
        mMenu = newMenuUi(reviewView);
        mSubject = newSubjectUi(reviewView);
        mRatingBar = newRatingUi(reviewView);
        mBannerButton = newBannerButtonUi(reviewView);
        mDataView = newDataViewUi(reviewView, calculator);
        mCover = newCoverUi(reviewView);
        mContextual = newContextualUi(reviewView);
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return mMenu.onItemSelected(item);
    }

    @Override
    public String getSubject() {
        return mSubject.getViewValue();
    }

    @Override
    public float getRating() {
        return mRatingBar.getViewValue();
    }

    @Override
    public void setRating(float rating) {
        mRatingBar.setViewValue(rating);
    }

    @Override
    public void setCover(@Nullable Bitmap cover) {
        mCover.setViewValue(cover);
    }

    @Override
    public void update(boolean forceSubject) {
        mSubject.update(forceSubject);
        mRatingBar.update();
        mBannerButton.update();
        mDataView.update();
        mContextual.update();
        mCover.update();
    }

    @NonNull
    private ViewUi<?, ?> newContextualUi(ReviewView<?> reviewView) {
        return new ContextualUi(mView.findViewById(CONTEXTUAL_VIEW),
                CONTEXTUAL_BUTTON, reviewView.getActions().getContextualAction());
    }

    @NonNull
    private CoverUi newCoverUi(ReviewView<?> reviewView) {
        return new CoverRvUi(reviewView, (ImageView) mView.findViewById(COVER), mDataView);
    }

    @NonNull
    private MenuUi newMenuUi(ReviewView<?> reviewView) {
        return new MenuUi(reviewView.getActions().getMenuAction());
    }

    @NonNull
    private <T extends GvData> DataViewUi<?, ?> newDataViewUi(ReviewView<T> reviewView, CellDimensionsCalculator calculator) {
        return new RecyclerViewUi<>(reviewView, (RecyclerView) mView.findViewById(GRID),
                calculator);
    }

    @NonNull
    private BannerButtonUi newBannerButtonUi(ReviewView<?> reviewView) {
        return new BannerButtonUi((Button) mView.findViewById(BANNER),
                reviewView.getActions().getBannerButtonAction());
    }

    @NonNull
    private SimpleViewUi<?, Float> newRatingUi(ReviewView<?> reviewView) {
        return new RatingBarRvUi(reviewView, (RatingBar) mView.findViewById(RATING));
    }

    @NonNull
    private SubjectUi<?> newSubjectUi(ReviewView<?> reviewView) {
        return new SubjectEditUi(reviewView, (EditText) mView.findViewById(SUBJECT));
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return false;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return false;
    }
}
