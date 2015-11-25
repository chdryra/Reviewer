package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewParams;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditorImpl<T extends GvData> extends ReviewViewDefault<T>
        implements ReviewDataEditor<T> {
    private String mSubject;
    private float mRating;
    private boolean mRatingIsAverage;

    private FragmentReviewView mParent;
    private DataBuilderAdapter<T> mBuilder;

    //Constructors
    public ReviewDataEditorImpl(DataBuilderAdapter<T> builder,
                                ReviewViewParams params,
                                ReviewViewActions<T> actions) {
        super(new ReviewViewPerspective<>(builder, params, actions));
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
    public GvImage getCover() {
        return mBuilder.getCovers().getItem(0);
    }

    @Override
    public void commitEdits() {
        mBuilder.setSubject(getFragmentSubject());
        mBuilder.setRatingIsAverage(mRatingIsAverage);
        mBuilder.setRating(getFragmentRating());
        mBuilder.publishData();
    }

    @Override
    public void discardEdits() {
        mBuilder.resetData();
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
