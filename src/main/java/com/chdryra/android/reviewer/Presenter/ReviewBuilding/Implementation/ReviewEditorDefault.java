/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewModifier;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditorDefault<GC extends GvDataList<?>> extends ReviewViewDefault<GC>
        implements ReviewEditor<GC> {
    public static final GvImage NULL_COVER = new GvImage();
    private ReviewBuilderAdapter<?> mBuilder;

    public ReviewEditorDefault(ReviewBuilderAdapter<GC> builder,
                               ReviewViewActions<GC> actions, ReviewViewParams params,
                               ReviewViewModifier modifier) {
        super(new ReviewViewPerspective<>(builder, actions, params, modifier));
        mBuilder = builder;
    }

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
            getParent().setRating(rating);
        }
    }

    @Override
    public GvImage getCover() {
        GvImageList covers = mBuilder.getCovers();
        return covers.size() > 0 ? covers.getItem(0) : NULL_COVER;
    }

    @Override
    public void setCover(GvImage image) {
        for(GvImage cover : mBuilder.getCovers()) {
            cover.setIsCover(false);
        }
        image.setIsCover(true);
        DataBuilderAdapter<GvImage> builder;
        builder = mBuilder.getDataBuilderAdapter(GvImage.TYPE);
        builder.add(image);
        builder.publishData();
    }

    @Override
    public void notifyBuilder() {
        mBuilder.notifyDataObservers();
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
}
