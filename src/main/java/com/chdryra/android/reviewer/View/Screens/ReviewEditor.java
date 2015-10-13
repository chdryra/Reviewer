package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditor extends ReviewView {
    private FragmentReviewView mParent;
    private ReviewBuilderAdapter mBuilder;

    //Constructors
    public ReviewEditor(ReviewBuilderAdapter builder,
                        ReviewViewParams params,
                        ReviewViewActions actions,
                        ReviewViewPerspective.ReviewViewModifier modifier) {
        super(new ReviewViewPerspective(builder, params, actions, modifier));
        mBuilder = builder;
    }

    //public methods
    public void setSubject() {
        mBuilder.setSubject(getFragmentSubject());
    }

    public void setRatingIsAverage(boolean isAverage) {
        mBuilder.setRatingIsAverage(isAverage);
        if (isAverage) setRating(mBuilder.getRating(), false);
    }

    public void setRating(float rating, boolean fromUser) {
        if (fromUser) {
            mBuilder.setRating(rating);
        } else {
            mParent.setRating(rating);
        }
    }

    public GvImageList getCovers() {
        return mBuilder.getCovers();
    }

    public void setCover(GvImageList.GvImage image) {
        for(GvImageList.GvImage cover : mBuilder.getCovers()) {
            cover.setIsCover(false);
        }
        image.setIsCover(true);
        ReviewBuilderAdapter.DataBuilderAdapter<GvImageList.GvImage> builder;
        builder = mBuilder.getDataBuilder(GvImageList.GvImage.TYPE);
        builder.add(image);
        builder.setData();
    }

    public void updateEditor() {
        mBuilder.notifyGridDataObservers();
    }

    public boolean hasTags() {
        return mBuilder.hasTags();
    }

    //Overridden
    @Override
    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    @Override
    public void attachFragment(FragmentReviewView parent) {
        mParent = parent;
        super.attachFragment(parent);
    }
}
