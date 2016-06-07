/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.GridItemAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActions<T extends GvData> {
    private SubjectAction<T> mSubjectAction;
    private RatingBarAction<T> mRatingBarAction;
    private BannerButtonAction<T> mBannerButtonAction;
    private GridItemAction<T> mGridItemAction;
    private MenuAction<T> mMenuAction;
    private ContextualButtonAction<T> mContextualAction;
    private ArrayList<ReviewViewAttachedObserver> mObservers;

    public interface ReviewViewAttachedObserver {
        <T extends GvData> void onReviewViewAttached(ReviewView<T> reviewView);

        void onReviewViewDetached();
    }

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             BannerButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction) {
        this(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction, null);
    }

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             BannerButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction,
                             @Nullable ContextualButtonAction<T> contextualAction) {
        mSubjectAction = subjectAction;
        mRatingBarAction = ratingBarAction;
        mBannerButtonAction = bannerButtonAction;
        mGridItemAction = gridItemAction;
        mMenuAction = menuAction;
        mContextualAction = contextualAction;
        mObservers = new ArrayList<>();
    }

    public SubjectAction<T> getSubjectAction() {
        return mSubjectAction;
    }

    public RatingBarAction<T> getRatingBarAction() {
        return mRatingBarAction;
    }

    public BannerButtonAction<T> getBannerButtonAction() {
        return mBannerButtonAction;
    }

    public GridItemAction<T> getGridItemAction() {
        return mGridItemAction;
    }

    public MenuAction<T> getMenuAction() {
        return mMenuAction;
    }

    @Nullable
    public ContextualButtonAction<T> getContextualAction() {
        return mContextualAction;
    }

    public void attachReviewView(ReviewView<T> view) {
        mMenuAction.attachReviewView(view);
        mSubjectAction.attachReviewView(view);
        mRatingBarAction.attachReviewView(view);
        mBannerButtonAction.attachReviewView(view);
        mGridItemAction.attachReviewView(view);
        if(mContextualAction != null ) mContextualAction.attachReviewView(view);
        notifyAttach(view);
    }

    public void detachReviewView() {
        mMenuAction.detachReviewView();
        mSubjectAction.detachReviewView();
        mRatingBarAction.detachReviewView();
        mBannerButtonAction.detachReviewView();
        mGridItemAction.detachReviewView();
        if(mContextualAction != null ) mContextualAction.detachReviewView();
        notifyDetach();
    }

    private void notifyAttach(ReviewView<T> reviewView) {
        for(ReviewViewAttachedObserver observer : mObservers) {
            observer.onReviewViewAttached(reviewView);
        }
    }

    private void notifyDetach() {
        for(ReviewViewAttachedObserver observer : mObservers) {
            observer.onReviewViewDetached();
        }
    }

    public void registerObserver(ReviewViewAttachedObserver observer) {
        mObservers.add(observer);
    }
}
