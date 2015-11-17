package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActions {
    private SubjectAction mSubjectAction;
    private RatingBarAction mRatingBarAction;
    private BannerButtonAction mBannerButtonAction;
    private GridItemAction mGridItemAction;
    private MenuAction mMenuAction;

    //Constructors
    public ReviewViewActions() {
        mSubjectAction = new SubjectAction();
        mRatingBarAction = new RatingBarAction();
        mBannerButtonAction = new BannerButtonAction();
        mGridItemAction = new GridItemAction();
        mMenuAction = new MenuAction();
    }

    //public methods
    public SubjectAction getSubjectAction() {
        return mSubjectAction;
    }

    public RatingBarAction getRatingBarAction() {
        return mRatingBarAction;
    }

    public BannerButtonAction getBannerButtonAction() {
        return mBannerButtonAction;
    }

    public GridItemAction getGridItemAction() {
        return mGridItemAction;
    }

    public MenuAction getMenuAction() {
        return mMenuAction;
    }

    public void setAction(MenuAction action) {
        mMenuAction = action;
    }

    public void setAction(SubjectAction action) {
        mSubjectAction = action;
    }

    public void setAction(RatingBarAction action) {
        mRatingBarAction = action;
    }

    public void setAction(BannerButtonAction action) {
        mBannerButtonAction = action;
    }

    public void setAction(GridItemAction action) {
        mGridItemAction = action;
    }

    public void attachReviewView(ReviewView view) {
        mMenuAction.attachReviewView(view);
        mSubjectAction.attachReviewView(view);
        mRatingBarAction.attachReviewView(view);
        mBannerButtonAction.attachReviewView(view);
        mGridItemAction.attachReviewView(view);
    }
}
