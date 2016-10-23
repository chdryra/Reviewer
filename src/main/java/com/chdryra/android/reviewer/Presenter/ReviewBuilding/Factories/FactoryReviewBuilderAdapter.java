/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderAdapterImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilderAdapter<GC extends GvDataList<? extends GvDataParcelable>> {
    private final FactoryReviewBuilder mBuilderFactory;
    private final FactoryDataBuilderAdapter mDataBuilderFactory;
    private final FactoryGridUi<GC> mGridUiFactory;
    private final DataValidator mDataValidator;

    public FactoryReviewBuilderAdapter(FactoryReviewBuilder builderFactory,
                                       FactoryDataBuilderAdapter dataBuilderFactory,
                                       FactoryGridUi<GC> gridUiFactory, DataValidator
                                               dataValidator) {
        mBuilderFactory = builderFactory;
        mDataBuilderFactory = dataBuilderFactory;
        mGridUiFactory = gridUiFactory;
        mDataValidator = dataValidator;
    }

    ReviewBuilderAdapter<GC> newAdapter(@Nullable Review template) {
        return new ReviewBuilderAdapterImpl<>(mBuilderFactory.newBuilder(template),
                mDataBuilderFactory,
                mGridUiFactory.newGridUiWrapperFull(),
                mGridUiFactory.newGridUiWrapperQuick(),
                mDataValidator);
    }
}
