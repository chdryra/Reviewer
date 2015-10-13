package com.chdryra.android.reviewer.View.Screens;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditor<T extends GvData> extends ReviewView {
    private String mSubject;
    private float mRating;
    private boolean mRatingIsAverage;

    private FragmentReviewView mParent;
    private ReviewBuilderAdapter.DataBuilderAdapter<T> mBuilder;

    //Constructors
    public ReviewDataEditor(ReviewBuilderAdapter.DataBuilderAdapter<T> builder,
                            ReviewViewParams params,
                            ReviewViewActions actions) {
        super(new ReviewViewPerspective(builder, params, actions));
        mBuilder = builder;
        mSubject = builder.getSubject();
        mRating = builder.getRating();
        mRatingIsAverage = builder.isRatingAverage();
    }

    //Static methods
    public static <T extends GvData> ReviewDataEditor<T> cast(ReviewView view, GvDataType<T> type) {
        try {
            //TODO make type safe
            return (ReviewDataEditor<T>) view;
        } catch (ClassCastException e) {
            throw new ClassCastException("ReviewView must be an Editor of type " + type.getDatumName());
        }
    }

    //public methods
    public void setSubject() {
        mSubject = getFragmentSubject();
    }

    public boolean isRatingAverage() {
        return mRatingIsAverage;
    }

    public void setRatingAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
        if(isAverage) setRating(mBuilder.getAverageRating(), false);
    }

    public void setRating(float rating, boolean fromUser) {
        if(fromUser) {
            setRatingAverage(false);
            mRating = rating;
        } else {
            mRating = rating;
            mParent.setRating(mRating);
        }
    }

    public boolean add(T datum) {
        return mBuilder.add(datum);
    }

    public void replace(T oldDatum, T newDatum) {
        mBuilder.replace(oldDatum, newDatum);
    }

    public void delete(T datum) {
        mBuilder.delete(datum);
    }

    public GvImageList getCovers() {
        return mBuilder.getCovers();
    }

    public void commitEdits() {
        mBuilder.setSubject(getFragmentSubject());
        mBuilder.setRatingIsAverage(mRatingIsAverage);
        mBuilder.setRating(getFragmentRating());
        mBuilder.setData();
    }

    public void discardEdits() {
        mBuilder.reset();
    }

    //Overridden
    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
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
