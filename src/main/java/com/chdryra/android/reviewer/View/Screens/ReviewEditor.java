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
    private boolean mRatingIsAverage = false;

    //Constructors
    public ReviewEditor(ReviewBuilderAdapter.DataBuilderAdapter builder,
                        ReviewViewParams params,
                        ReviewViewActionCollection actions) {
        super(new ReviewViewPerspective(builder, params, actions));
        mEditor = new ReviewDataBuilderEditor(builder);
        mRatingIsAverage = builder.getParentBuilder().isRatingAverage();
    }

    public ReviewEditor(ReviewBuilderAdapter builder, ReviewViewParams params,
                        ReviewViewActionCollection actions,
                        ReviewViewPerspective.ReviewViewModifier modifier) {
        super(new ReviewViewPerspective(builder, params, actions, modifier));
        mEditor = new ReviewBuilderEditor(builder);
        mRatingIsAverage = builder.isRatingAverage();
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
        return mRatingIsAverage;
    }

    public void setRatingAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
    }

    public void setRating(float rating, boolean fromUser) {
        mEditor.setRating(rating, fromUser, mParent);
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
    public float getFragmentRating() {
        return mRatingIsAverage ? mEditor.getAverageRating() : super.getFragmentRating();
    }

    @Override
    public void attachFragment(FragmentReviewView parent) {
        mParent = parent;
        super.attachFragment(parent);
    }

    private static class ReviewBuilderEditor {
        private ReviewBuilderAdapter mBuilder;

        private ReviewBuilderEditor(ReviewBuilderAdapter builder) {
            mBuilder = builder;
        }

        //public methods
        public float getRating() {
            return mBuilder.getRating();
        }

        public float getAverageRating() {
            return mBuilder.getAverageRating();
        }

        public void setRating(float rating, boolean fromUser, FragmentReviewView parent) {
            if (!fromUser) parent.setRating(rating);
        }
    }

    private static class ReviewDataBuilderEditor extends ReviewBuilderEditor {
        private float mRating;

        private ReviewDataBuilderEditor(ReviewBuilderAdapter.DataBuilderAdapter builder) {
            super(builder.getParentBuilder());
            mRating = builder.getRating();
        }

        //Overridden
        @Override
        public float getRating() {
            return mRating;
        }

        @Override
        public void setRating(float rating, boolean fromUser, FragmentReviewView parent) {
            mRating = rating;
            super.setRating(rating, fromUser, parent);
        }
    }
}
