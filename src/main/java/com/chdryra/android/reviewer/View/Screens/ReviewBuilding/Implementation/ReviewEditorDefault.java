package com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Screens.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewDefault;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditorDefault extends ReviewViewDefault implements ReviewEditor {
    private FragmentReviewView mParent;
    private ReviewBuilderAdapter<?> mBuilder;

    //Constructors
    public ReviewEditorDefault(ReviewBuilderAdapter<?> builder,
                               ReviewViewParams params,
                               ReviewViewActions actions,
                               ReviewViewPerspective.ReviewViewModifier modifier) {
        super(new ReviewViewPerspective(builder, params, actions, modifier));
        mBuilder = builder;
    }

    //public methods
    @Override
    public void setSubject() {
        mBuilder.setSubject(getFragmentSubject());
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mBuilder.setRatingIsAverage(isAverage);
        if (isAverage) setRating(mBuilder.getRating(), false);
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        if (fromUser) {
            mBuilder.setRating(rating);
        } else {
            mParent.setRating(rating);
        }
    }

    @Override
    public GvImageList.GvImage getCover() {
        return mBuilder.getCovers().getItem(0);
    }

    @Override
    public void setCover(GvImageList.GvImage image) {
        for(GvImageList.GvImage cover : mBuilder.getCovers()) {
            cover.setIsCover(false);
        }
        image.setIsCover(true);
        DataBuilderAdapter<GvImageList.GvImage> builder;
        builder = mBuilder.getDataBuilderAdapter(GvImageList.GvImage.TYPE);
        builder.add(image);
        builder.setData();
    }

    @Override
    public void notifyBuilder() {
        mBuilder.notifyGridDataObservers();
    }

    @Override
    public boolean hasTags() {
        return mBuilder.hasTags();
    }

    @Override
    public ImageChooser getImageChooser() {
        return mBuilder.getImageChooser();
    }

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
