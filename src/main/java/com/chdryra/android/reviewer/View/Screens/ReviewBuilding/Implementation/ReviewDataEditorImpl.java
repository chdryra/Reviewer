package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditorImpl<T extends GvData> extends ReviewViewDefault implements ReviewDataEditor<T> {
    private String mSubject;
    private float mRating;
    private boolean mRatingIsAverage;

    private FragmentReviewView mParent;
    private DataBuilderAdapter<T> mBuilder;

    //Constructors
    public ReviewDataEditorImpl(DataBuilderAdapter<T> builder,
                                ReviewViewParams params,
                                ReviewViewActions actions) {
        super(new ReviewViewPerspective(builder, params, actions));
        mBuilder = builder;
        mSubject = builder.getSubject();
        mRating = builder.getRating();
        mRatingIsAverage = builder.isRatingAverage();
    }

    //public methods
    @Override
    public void setSubject() {
        mSubject = getFragmentSubject();
    }

    @Override
    public boolean isRatingAverage() {
        return mRatingIsAverage;
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
        if(isAverage) setRating(mBuilder.getAverageRating(), false);
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        if(fromUser) {
            setRatingIsAverage(false);
            mRating = rating;
        } else {
            mRating = rating;
            mParent.setRating(mRating);
        }
    }

    @Override
    public boolean add(T datum) {
        return mBuilder.add(datum);
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        mBuilder.replace(oldDatum, newDatum);
    }

    @Override
    public void delete(T datum) {
        mBuilder.delete(datum);
    }

    @Override
    public GvImageList.GvImage getCover() {
        return mBuilder.getCovers().getItem(0);
    }

    @Override
    public void commitEdits() {
        mBuilder.setSubject(getFragmentSubject());
        mBuilder.setRatingIsAverage(mRatingIsAverage);
        mBuilder.setRating(getFragmentRating());
        mBuilder.setData();
    }

    @Override
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
