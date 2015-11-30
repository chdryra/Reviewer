package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdIdableList;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterionList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderImpl implements ReviewBuilder {
    private final Map<GvDataType, DataBuilder> mDataBuilders;

    private String mSubject;
    private float mRating;
    private ArrayList<ReviewBuilderImpl> mChildren;
    private boolean mIsAverage = false;

    private final ConverterGv mConverter;
    private final TagsManager mTagsManager;
    private final FactoryReviews mReviewFactory;
    private final FactoryDataBuilder mDataBuilderFactory;
    private final FactoryGvData mDataFactory;
    private final DataValidator mDataValidator;

    //Constructors
    public ReviewBuilderImpl(ConverterGv converter,
                             TagsManager tagsManager,
                             FactoryReviews reviewFactory,
                             FactoryDataBuilder dataBuilderFactory,
                             FactoryGvData dataFactory,
                             DataValidator dataValidator) {
        mConverter = converter;
        mTagsManager = tagsManager;
        mReviewFactory = reviewFactory;
        mDataValidator = dataValidator;

        mChildren = new ArrayList<>();
        mDataBuilders = new HashMap<>();
        mDataBuilderFactory = dataBuilderFactory;
        mDataFactory = dataFactory;

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
        mDataBuilders.put(dataType, dataBuilder);
    }

    @Override
    public GvImageList getCovers() {
        return ((GvImageList) getData(GvImage.TYPE)).getCovers();
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
        mTagsManager.tagItem(review.getReviewId(), tags.toStringArray());

        return review;
    }

    //private methods
    private <T extends GvData> DataBuilder<T> createDataBuilder(GvDataType<T> dataType) {
        DataBuilder<T> db = mDataBuilderFactory.newDataBuilder(mDataFactory.newDataList(dataType));
        db.registerObserver(this);
        mDataBuilders.put(dataType, db);
        return db;
    }

    private boolean isValidForPublication() {
        return mDataValidator.validateString(mSubject) && hasTags();
    }

    private Review assembleReview() {
        IdableCollection<Review> criteria = new MdIdableList<>(null);
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
                    mReviewFactory, mDataBuilderFactory, mDataFactory, mDataValidator);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
    }
}
