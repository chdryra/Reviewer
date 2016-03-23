/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderImpl implements ReviewBuilder {
    private final Map<GvDataType<?>, DataBuilder<?>> mDataBuilders;

    private String mSubject;
    private float mRating;
    private ArrayList<ReviewBuilderImpl> mChildren;
    private boolean mIsAverage = false;

    private final ConverterGv mConverter;
    private final TagsManager mTagsManager;
    private final FactoryReviews mReviewFactory;
    private final FactoryDataBuilder mDataBuilderFactory;
    private final DataValidator mDataValidator;

    //Constructors
    public ReviewBuilderImpl(ConverterGv converter,
                             TagsManager tagsManager,
                             FactoryReviews reviewFactory,
                             FactoryDataBuilder dataBuilderFactory,
                             DataValidator dataValidator) {
        mConverter = converter;
        mTagsManager = tagsManager;
        mReviewFactory = reviewFactory;
        mDataValidator = dataValidator;

        mChildren = new ArrayList<>();
        mDataBuilders = new HashMap<>();
        mDataBuilderFactory = dataBuilderFactory;

        mSubject = "";
        mRating = 0f;
    }

    //public methods
    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public void setSubject(String subject) {
        mSubject = subject;
    }

    @Override
    public boolean isRatingAverage() {
        return mIsAverage;
    }

    @Override
    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    @Override
    public void setRating(float rating) {
        if (!isRatingAverage()) mRating = rating;
    }

    @Override
    public float getAverageRating() {
        GvCriterionList criteria = (GvCriterionList) getData(GvCriterion.TYPE);
        return criteria.getAverageRating();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getAverageRating();
    }

    @Override
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        DataBuilder builder = mDataBuilders.get(dataType);
        if(builder == null) builder = createDataBuilder(dataType);
        //TODO make type safe
        return builder;
    }

    private <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        return getDataBuilder(dataType).getData();
    }

    @Override
    public <T extends GvData> void onDataPublished(DataBuilder<T> dataBuilder) {
        GvDataType<T> dataType = dataBuilder.getGvDataType();
        if (dataType.equals(GvCriterion.TYPE)) setCriteria(dataBuilder.getData());
    }

    @Override
    public GvImageList getCovers() {
        GvDataList<GvImage> images = getData(GvImage.TYPE);
        GvImageList covers = new GvImageList(images.getGvReviewId());
        for(GvImage image : images) {
            if(image.isCover()) covers.add(image);
        }

        return covers;
    }

    @Override
    public boolean hasTags() {
        return getData(GvTag.TYPE).size() > 0;
    }

    @Override
    public Review buildReview() {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        Review review = assembleReview();
        GvTagList tags = (GvTagList) getData(GvTag.TYPE);
        mTagsManager.tagItem(review.getReviewId().toString(), tags.toStringArray());

        return review;
    }

    //private methods
    private <T extends GvData> DataBuilder<T> createDataBuilder(GvDataType<T> dataType) {
        DataBuilder<T> db = mDataBuilderFactory.newDataBuilder(dataType);
        db.registerObserver(this);
        mDataBuilders.put(dataType, db);
        return db;
    }

    private boolean isValidForPublication() {
        return mDataValidator.validateString(mSubject) && hasTags();
    }

    private Review assembleReview() {
        IdableCollection<Review> criteria = new IdableDataCollection<>();
        for (ReviewBuilderImpl child : mChildren) {
            criteria.add(child.assembleReview());
        }

        return mReviewFactory.createUserReview(getSubject(), getRating(),
                getData(GvComment.TYPE),
                getData(GvImage.TYPE),
                getData(GvFact.TYPE),
                getData(GvLocation.TYPE),
                criteria, mIsAverage);
    }

    private void setCriteria(GvDataList children) {
        mChildren = new ArrayList<>();
        for (GvCriterion child : (GvCriterionList) children) {
            ReviewBuilderImpl childBuilder = new ReviewBuilderImpl(mConverter, mTagsManager,
                    mReviewFactory, mDataBuilderFactory, mDataValidator);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
    }
}
