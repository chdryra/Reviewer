package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.MenuAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.SubjectAction;

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
    private ArrayList<ReviewViewAttachedObserver> mObservers;

    public interface ReviewViewAttachedObserver {
        <T extends GvData> void onReviewViewAttached(ReviewView<T> reviewView);
    }

    public ReviewViewActions(SubjectAction<T> subjectAction, RatingBarAction<T> ratingBarAction,
                             BannerButtonAction<T> bannerButtonAction, GridItemAction<T>
                                     gridItemAction, MenuAction<T> menuAction) {
        mSubjectAction = subjectAction;
        mRatingBarAction = ratingBarAction;
        mBannerButtonAction = bannerButtonAction;
        mGridItemAction = gridItemAction;
        mMenuAction = menuAction;
        mObservers = new ArrayList<>();
    }

    //public methods
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

    public void attachReviewView(ReviewView<T> view) {
        mMenuAction.attachReviewView(view);
        mSubjectAction.attachReviewView(view);
        mRatingBarAction.attachReviewView(view);
        mBannerButtonAction.attachReviewView(view);
        mGridItemAction.attachReviewView(view);
    }

    public void notifyObservers(ReviewView<T> reviewView) {
        for(ReviewViewAttachedObserver observer : mObservers) {
            observer.onReviewViewAttached(reviewView);
        }
    }

    public void registerObserver(ReviewViewAttachedObserver observer) {
        mObservers.add(observer);
    }
}
