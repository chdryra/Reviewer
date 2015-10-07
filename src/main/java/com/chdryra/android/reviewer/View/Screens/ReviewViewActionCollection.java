package com.chdryra.android.reviewer.View.Screens;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewActionCollection {
    private ReviewViewAction.SubjectAction mSubjectAction;
    private ReviewViewAction.RatingBarAction mRatingBarAction;
    private ReviewViewAction.BannerButtonAction mBannerButtonAction;
    private ReviewViewAction.GridItemAction mGridItemAction;
    private ReviewViewAction.MenuAction mMenuAction;

    //Constructors
    public ReviewViewActionCollection() {
        mSubjectAction = new ReviewViewAction.SubjectAction();
        mRatingBarAction = new ReviewViewAction.RatingBarAction();
        mBannerButtonAction = new ReviewViewAction.BannerButtonAction();
        mGridItemAction = new ReviewViewAction.GridItemAction();
        mMenuAction = new ReviewViewAction.MenuAction();
    }

    //public methods
    public ReviewViewAction.SubjectAction getSubjectAction() {
        return mSubjectAction;
    }

    public ReviewViewAction.RatingBarAction getRatingBarAction() {
        return mRatingBarAction;
    }

    public ReviewViewAction.BannerButtonAction getBannerButtonAction() {
        return mBannerButtonAction;
    }

    public ReviewViewAction.GridItemAction getGridItemAction() {
        return mGridItemAction;
    }

    public ReviewViewAction.MenuAction getMenuAction() {
        return mMenuAction;
    }

    public void setAction(ReviewViewAction.MenuAction action) {
        mMenuAction = action;
    }

    public void setAction(ReviewViewAction.SubjectAction action) {
        mSubjectAction = action;
    }

    public void setAction(ReviewViewAction.RatingBarAction action) {
        mRatingBarAction = action;
    }

    public void setAction(ReviewViewAction.BannerButtonAction action) {
        mBannerButtonAction = action;
    }

    public void setAction(ReviewViewAction.GridItemAction action) {
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
