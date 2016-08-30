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
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ReviewBuilderImpl;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
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
    private ConverterGv mConverterGv;
    private TagsManager mTagsManager;
    private FactoryReviews mFactoryReviews;
    private DataValidator mDataValidator;
    private FactoryDataBuilder mDataBuilderFactory;

    public FactoryReviewBuilder(ConverterGv converterGv,
                                DataValidator dataValidator, TagsManager tagsManager,
                                FactoryReviews factoryReviews,
                                FactoryDataBuilder dataBuilderfactory) {
        mConverterGv = converterGv;
        mTagsManager = tagsManager;
        mFactoryReviews = factoryReviews;
        mDataValidator = dataValidator;
        mDataBuilderFactory = dataBuilderfactory;
    }

    public ReviewBuilder newBuilder(@Nullable Review template) {
        ReviewBuilder builder = new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDataValidator);

        if(template != null) {
            builder.setSubject(template.getSubject().getSubject());

            setTags(template, builder);
            setCriteria(template, builder);
            setCover(template, builder);
            setLocations(template, builder);
            setFacts(template, builder);
        }

        return builder;
    }

    private void setTags(Review template, ReviewBuilder builder) {
        DataBuilder<GvTag> dataBuilder = builder.getDataBuilder(GvTag.TYPE);
        ReviewId reviewId = template.getReviewId();
        ItemTagCollection tags = mTagsManager.getTags(reviewId.toString());
        for(GvTag tag : mConverterGv.newConverterItemTags().convert(tags, reviewId)) {
            dataBuilder.add(tag);
        }

        dataBuilder.buildData();
    }

    private void setCriteria(Review template, ReviewBuilder builder) {
        DataBuilder<GvCriterion> dataBuilder = builder.getDataBuilder(GvCriterion.TYPE);
        for(GvCriterion datum : mConverterGv.newConverterCriteriaSubjects().convert(template.getCriteria())){
            dataBuilder.add(datum);
        }

        dataBuilder.buildData();
    }

    private void setCover(Review template, ReviewBuilder builder) {
        DataBuilder<GvImage> dataBuilder = builder.getDataBuilder(GvImage.TYPE);
        dataBuilder.add(mConverterGv.newConverterImages().convert(template.getCover()));
        dataBuilder.buildData();
    }

    private void setLocations(Review template, ReviewBuilder builder) {
        DataBuilder<GvLocation> dataBuilder = builder.getDataBuilder(GvLocation.TYPE);
        for(GvLocation datum : mConverterGv.newConverterLocations().convert(template.getLocations())){
            dataBuilder.add(datum);
        }

        dataBuilder.buildData();
    }

    private void setFacts(Review template, ReviewBuilder builder) {
        DataBuilder<GvFact> dataBuilder = builder.getDataBuilder(GvFact.TYPE);
        for(GvFact datum : mConverterGv.newConverterFacts().convert(template.getFacts())){
            dataBuilder.add(datum);
        }

        dataBuilder.buildData();
    }
}
