/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Factories.FactoryActionsReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActions<T extends GvData> implements OptionSelectListener{
    private final SubjectAction<T> mSubjectAction;
    private final RatingBarAction<T> mRatingBarAction;
    private final ButtonAction<T> mBannerButtonAction;
    private final GridItemAction<T> mGridItemAction;
    private final MenuAction<T> mMenuAction;
    private final ButtonAction<T> mContextualAction;

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             ButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction) {
        this(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction, null);
    }

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             ButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction,
                             @Nullable ButtonAction<T> contextualAction) {
        mSubjectAction = subjectAction;
        mRatingBarAction = ratingBarAction;
        mBannerButtonAction = bannerButtonAction;
        mGridItemAction = gridItemAction;
        mMenuAction = menuAction;
        mContextualAction = contextualAction;
    }

    public ReviewViewActions(FactoryActionsReviewView<T> factory) {
        this(factory.newSubject(),
                factory.newRatingBar(), factory.newBannerButton(), factory.newGridItem(),
                factory.newMenu(), factory.newContextButton());
    }

    public SubjectAction<T> getSubjectAction() {
        return mSubjectAction;
    }

    public RatingBarAction<T> getRatingBarAction() {
        return mRatingBarAction;
    }

    public ButtonAction<T> getBannerButtonAction() {
        return mBannerButtonAction;
    }

    public GridItemAction<T> getGridItemAction() {
        return mGridItemAction;
    }

    public MenuAction<T> getMenuAction() {
        return mMenuAction;
    }

    @Nullable
    public ButtonAction<T> getContextualAction() {
        return mContextualAction;
    }

    public void attachReviewView(ReviewView<T> view) {
        mMenuAction.attachReviewView(view);
        mSubjectAction.attachReviewView(view);
        mRatingBarAction.attachReviewView(view);
        mBannerButtonAction.attachReviewView(view);
        mGridItemAction.attachReviewView(view);
        if(mContextualAction != null ) mContextualAction.attachReviewView(view);
    }

    public void detachReviewView() {
        mMenuAction.detachReviewView();
        mSubjectAction.detachReviewView();
        mRatingBarAction.detachReviewView();
        mBannerButtonAction.detachReviewView();
        mGridItemAction.detachReviewView();
        if(mContextualAction != null ) mContextualAction.detachReviewView();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        boolean consumed = mMenuAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mSubjectAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mRatingBarAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mBannerButtonAction.onOptionSelected(requestCode, option);
        if(!consumed) consumed = mGridItemAction.onOptionSelected(requestCode, option);
        if(!consumed && mContextualAction != null ) {
            consumed = mContextualAction.onOptionSelected(requestCode, option);
        }

        return consumed;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        boolean consumed = mMenuAction.onOptionsCancelled(requestCode);
        if(!consumed) consumed = mSubjectAction.onOptionsCancelled(requestCode);
        if(!consumed) consumed = mRatingBarAction.onOptionsCancelled(requestCode);
        if(!consumed) consumed = mBannerButtonAction.onOptionsCancelled(requestCode);
        if(!consumed) consumed = mGridItemAction.onOptionsCancelled(requestCode);
        if(!consumed && mContextualAction != null ) {
            consumed = mContextualAction.onOptionsCancelled(requestCode);
        }

        return consumed;
    }
}
