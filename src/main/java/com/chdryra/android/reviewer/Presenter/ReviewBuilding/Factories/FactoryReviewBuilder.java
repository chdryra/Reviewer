/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
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

    public ReviewBuilder newBuilder() {
        return new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDataValidator);
    }

    public ReviewBuilder newBuilder(Review template) {
        ReviewBuilder builder = new ReviewBuilderImpl(mConverterGv,
                mTagsManager,
                mFactoryReviews,
                mDataBuilderFactory,
                mDataValidator);

        builder.setSubject(template.getSubject().getSubject());
        builder.setRatingIsAverage(template.isRatingAverageOfCriteria());

        setTags(template, builder);
        setCriteria(template, builder);
        setCovers(template, builder);
        setLocations(template, builder);
        setFacts(template, builder);

        return builder;
    }

    private void setTags(Review template, ReviewBuilder builder) {
        DataBuilder<GvTag> dataBuilder = builder.getDataBuilder(GvTag.TYPE);
        ReviewId reviewId = template.getReviewId();
        ItemTagCollection tags = mTagsManager.getTags(reviewId.toString());
        for(GvTag tag : mConverterGv.getConverterItemTags().convert(tags, reviewId)) {
            dataBuilder.add(tag);
        }

        dataBuilder.publishData();
    }

    private void setCriteria(Review template, ReviewBuilder builder) {
        DataBuilder<GvCriterion> dataBuilder = builder.getDataBuilder(GvCriterion.TYPE);
        for(GvCriterion datum : mConverterGv.getConverterCriteriaSubjects().convert(template.getCriteria())){
            dataBuilder.add(datum);
        }

        dataBuilder.publishData();
    }

    private void setCovers(Review template, ReviewBuilder builder) {
        DataBuilder<GvImage> dataBuilder = builder.getDataBuilder(GvImage.TYPE);
        template.getCovers();
        for(GvImage datum : mConverterGv.getConverterImages().convert(template.getCovers())){
            dataBuilder.add(datum);
        }

        dataBuilder.publishData();
    }

    private void setLocations(Review template, ReviewBuilder builder) {
        DataBuilder<GvLocation> dataBuilder = builder.getDataBuilder(GvLocation.TYPE);
        template.getCovers();
        for(GvLocation datum : mConverterGv.getConverterLocations().convert(template.getLocations())){
            dataBuilder.add(datum);
        }

        dataBuilder.publishData();
    }

    private void setFacts(Review template, ReviewBuilder builder) {
        DataBuilder<GvFact> dataBuilder = builder.getDataBuilder(GvFact.TYPE);
        template.getCovers();
        for(GvFact datum : mConverterGv.getConverterFacts().convert(template.getFacts())){
            dataBuilder.add(datum);
        }

        dataBuilder.publishData();
    }
}
