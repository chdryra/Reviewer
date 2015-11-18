package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.View.Screens.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.Screens.Implementation.GridItemActionNone;
import com.chdryra.android.reviewer.View.Screens.Implementation.MenuActionNone;
import com.chdryra.android.reviewer.View.Screens.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.View.Screens.Implementation.SubjectActionNone;
import com.chdryra.android.reviewer.View.Screens.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Screens.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Screens.Interfaces.SubjectAction;

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
        mSubjectAction = new SubjectActionNone();
        mRatingBarAction = new RatingBarActionNone();
        mBannerButtonAction = new BannerButtonActionNone();
        mGridItemAction = new GridItemActionNone();
        mMenuAction = new MenuActionNone();
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
