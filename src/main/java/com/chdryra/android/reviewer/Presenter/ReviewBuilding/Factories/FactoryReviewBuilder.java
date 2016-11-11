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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewBuilder {
    private final ConverterGv mConverterGv;
    private final FactoryReviews mFactoryReviews;
    private final DataValidator mValidator;
    private final FactoryDataBuilder mBuilderFactory;

    public FactoryReviewBuilder(ConverterGv converterGv,
                                DataValidator validator,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder builderfactory) {
        mConverterGv = converterGv;
        mFactoryReviews = factoryReviews;
        mValidator = validator;
        mBuilderFactory = builderfactory;
    }

    ReviewBuilder newBuilder(@Nullable Review template) {
        ReviewBuilder builder = new ReviewBuilderImpl(mFactoryReviews, mBuilderFactory, mValidator);

        if (template != null) {
            builder.setSubject(template.getSubject().getSubject());
            setCover(template, builder);
            setData(builder, GvTag.TYPE, mConverterGv.newConverterTags(), template.getTags());
            setData(builder, GvCriterion.TYPE, mConverterGv.newConverterCriteriaSubjects(),
                    template.getCriteria());
            setData(builder, GvLocation.TYPE, mConverterGv.newConverterLocations(), template
                    .getLocations());
            setData(builder, GvFact.TYPE, mConverterGv.newConverterFacts(), template.getFacts());
        }

        return builder;
    }

    private <T1 extends HasReviewId, T2 extends GvData> void setData(ReviewBuilder builder,
                                                                     GvDataType<T2> dataType,
                                                                     DataConverter<T1, T2, ?
                                                                             extends
                                                                             GvDataList<T2>>
                                                                             converter,
                                                                     IdableList<? extends T1>
                                                                             data) {
        DataBuilder<T2> dataBuilder = builder.getDataBuilder(dataType);
        for (T2 datum : converter.convert(data)) {
            dataBuilder.add(datum);
        }

        dataBuilder.buildData();
    }

    private void setCover(Review template, ReviewBuilder builder) {
        DataBuilder<GvImage> dataBuilder = builder.getDataBuilder(GvImage.TYPE);
        dataBuilder.add(mConverterGv.newConverterImages().convert(template.getCover()));
        dataBuilder.buildData();
    }
}
