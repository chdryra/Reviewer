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
    private ReviewBuilderEditor mEditor;

    //Constructors
    public ReviewEditor(ReviewBuilderAdapter.DataBuilderAdapter builder,
                        ReviewViewParams params,
                        ReviewViewActionCollection actions) {
        super(new ReviewViewPerspective(builder, params, actions));
        mEditor = new ReviewDataBuilderEditor(builder);
    }

    public ReviewEditor(ReviewBuilderAdapter builder, ReviewViewParams params,
                        ReviewViewActionCollection actions,
                        ReviewViewPerspective.ReviewViewModifier modifier) {
        super(new ReviewViewPerspective(builder, params, actions, modifier));
        mEditor = new ReviewBuilderEditor(builder);
    }

    //Static methods
    public static ReviewEditor cast(ReviewView view) {
        ReviewEditor editor;
        try {
            editor = (ReviewEditor) view;
        } catch (ClassCastException e) {
            throw new ClassCastException("ReviewView must be an Editor");
        }
        return editor;
    }

    //public methods
    public boolean isRatingAverage() {
        return mEditor.isRatingAverage();
    }

    public void setRatingAverage(boolean isAverage) {
        mEditor.setRatingIsAverage(isAverage);
    }

    public void setRating(float rating, boolean fromUser) {
        if(fromUser) setRatingAverage(false);
        mEditor.setRating(rating, fromUser);
    }

    public void proposeCover(GvImageList.GvImage image) {
        if (getParams().manageCover()) {
            GvImageList images = getAdapter().getCovers();
            GvImageList covers = images.getCovers();
            if (covers.size() == 1 && images.contains(image)) {
                covers.getItem(0).setIsCover(false);
                image.setIsCover(true);
            }
        }
    }

    //Overridden
    @Override
    public float getRating() {
        return mEditor.getRating();
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

    private class ReviewBuilderEditor {
        protected ReviewBuilderAdapter mBuilder;

        private ReviewBuilderEditor(ReviewBuilderAdapter builder) {
            mBuilder = builder;
        }

        //public methods
        public float getRating() {
            return mBuilder.getRating();
        }

        public boolean isRatingAverage() {
            return mBuilder.isRatingAverage();
        }

        public void setRatingIsAverage(boolean isAverage) {
            mBuilder.setRatingIsAverage(isAverage);
            if(isAverage) setRating(mBuilder.getRating(), false);
        }

        public void setRating(float rating, boolean fromUser) {
            if (!fromUser) {
                mParent.setRating(rating);
            } else {
                mBuilder.setRating();
            }
        }
    }

    private class ReviewDataBuilderEditor extends ReviewBuilderEditor {
        private float mRating;
        private boolean mRatingIsAverage;
        private ReviewBuilderAdapter.DataBuilderAdapter mDataBuilder;

        private ReviewDataBuilderEditor(ReviewBuilderAdapter.DataBuilderAdapter builder) {
            super(builder.getParentBuilder());
            mDataBuilder = builder;
            mRating = mDataBuilder.getRating();
            mRatingIsAverage = mDataBuilder.isRatingAverage();
        }

        //Overridden
        @Override
        public float getRating() {
            return mRating;
        }

        @Override
        public boolean isRatingAverage() {
            return mRatingIsAverage;
        }

        @Override
        public void setRatingIsAverage(boolean isAverage) {
            mRatingIsAverage = isAverage;
            if(isAverage) setRating(mDataBuilder.getAverageRating(), false);
        }

        public void setRating(float rating, boolean fromUser) {
            if (!fromUser) mParent.setRating(rating);
            mRating = rating;
        }
    }
}
