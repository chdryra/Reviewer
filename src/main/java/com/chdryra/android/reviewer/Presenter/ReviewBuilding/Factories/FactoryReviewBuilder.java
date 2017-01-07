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
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderInitialiser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilder {
    private static final ReviewBuilderImpl.TemplateOrEdit EDIT = ReviewBuilderImpl.TemplateOrEdit
            .EDIT;
    private static final ReviewBuilderImpl.TemplateOrEdit TEMPLATE = ReviewBuilderImpl
            .TemplateOrEdit.TEMPLATE;
    private final FactoryReviews mFactoryReviews;
    private final DataValidator mValidator;
    private final FactoryDataBuilder mBuilderFactory;
    private final ReviewBuilderInitialiser mInitialiser;

    public FactoryReviewBuilder(DataValidator validator,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder builderfactory,
                                ReviewBuilderInitialiser initialiser) {
        mFactoryReviews = factoryReviews;
        mValidator = validator;
        mBuilderFactory = builderfactory;
        mInitialiser = initialiser;
    }

    ReviewBuilder newBuilder(@Nullable Review template) {
        ReviewBuilder builder;
        if (template == null) {
            builder = new ReviewBuilderImpl(mFactoryReviews, mBuilderFactory, mValidator);
        } else {
            builder = new ReviewBuilderImpl(template, mInitialiser, TEMPLATE, mFactoryReviews, mBuilderFactory, mValidator);
        }

        return builder;
    }

    ReviewBuilder newEditBuilder(Review toEdit) {
        return new ReviewBuilderImpl(toEdit, mInitialiser, EDIT, mFactoryReviews, mBuilderFactory, mValidator);
    }
}
