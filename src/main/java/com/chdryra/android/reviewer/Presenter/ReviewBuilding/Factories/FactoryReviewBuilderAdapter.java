/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderAdapterImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilderAdapter<GC extends GvDataList<? extends GvDataParcelable>> {
    private final FactoryReviewBuilder mBuilderFactory;
    private final FactoryGridUi<GC> mGridUiFactory;
    private final DataValidator mDataValidator;
    private final FactoryDataBuilderAdapter mDataBuilderAdapterFactory;
    private final FactoryFileIncrementor mFactoryFileIncrementor;
    private final FactoryImageChooser mFactoryImageChooser;

    public FactoryReviewBuilderAdapter(FactoryReviewBuilder builderFactory,
                                       FactoryGridUi<GC> gridUiFactory,
                                       DataValidator dataValidator,
                                       FactoryDataBuilderAdapter dataBuilderAdapterFactory,
                                       FactoryFileIncrementor factoryFileIncrementor,
                                       FactoryImageChooser factoryImageChooser) {
        mBuilderFactory = builderFactory;
        mGridUiFactory = gridUiFactory;
        mDataValidator = dataValidator;
        mDataBuilderAdapterFactory = dataBuilderAdapterFactory;
        mFactoryFileIncrementor = factoryFileIncrementor;
        mFactoryImageChooser = factoryImageChooser;
    }

    ReviewBuilderAdapter<GC> newAdapter(@Nullable Review template) {
        return newReviewBuilderAdapter(mBuilderFactory.newBuilder(template));
    }

    @NonNull
    private ReviewBuilderAdapter<GC> newReviewBuilderAdapter(ReviewBuilder builder) {
        return new ReviewBuilderAdapterImpl<>(builder,
                mGridUiFactory.newGridUiWrapperFull(),
                mGridUiFactory.newGridUiWrapperQuick(),
                mDataValidator,
                mDataBuilderAdapterFactory,
                mFactoryFileIncrementor,
                mFactoryImageChooser);
    }
}
