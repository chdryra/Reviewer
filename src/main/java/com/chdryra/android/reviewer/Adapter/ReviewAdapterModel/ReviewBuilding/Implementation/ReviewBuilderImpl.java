package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryDataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewBuilderImpl implements ReviewBuilder {
    private static final ArrayList<GvDataType> TYPES = ConfigGvDataUi.BUILD_TYPES;

    private final Map<GvDataType, GvDataList> mData;
    private final Map<GvDataType, DataBuilder> mDataBuilders;

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
                             FactoryDataBuilder databuilderFactory,
                             DataValidator dataValidator) {
        mConverter = converter;
        mTagsManager = tagsManager;
        mReviewFactory = reviewFactory;
        mDataValidator = dataValidator;

        mChildren = new ArrayList<>();
        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        mDataBuilderFactory = databuilderFactory;
        for (GvDataType dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newDataList(dataType));
            mDataBuilders.put(dataType, mDataBuilderFactory.newDataBuilder(dataType, this));
        }

        mSubject = "";
        mRating = 0f;
    }

    //public methods
    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public boolean isRatingAverage() {
        return mIsAverage;
    }

    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    public void setRating(float rating) {
        if (!isRatingAverage()) mRating = rating;
    }

    public float getAverageRating() {
        GvCriterionList criteria = (GvCriterionList) getData(GvCriterionList.GvCriterion.TYPE);
        return criteria.getAverageRating();
    }

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getAverageRating();
    }

    //TODO make type safe
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        return mDataBuilders.get(dataType);
    }

    //TODO make type safe
    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        return mData.get(dataType);
    }

    public <T extends GvData> void setData(DataBuilder<T> dataBuilder) {
        GvDataList<T> data = dataBuilder.getData();
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            setCriteria(data);
        } else if (TYPES.contains(dataType)) {
            mData.put(dataType, data);
        }
    }

    public Review buildReview() {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        Review review = assembleReview();
        GvTagList tags = (GvTagList) getData(GvTagList.GvTag.TYPE);
        mTagsManager.tagItem(review.getReviewId(), tags.toStringArray());

        return review;
    }

    //private methods
    private boolean isValidForPublication() {
        return mDataValidator.validateString(mSubject) && getData(GvTagList.GvTag.TYPE).size() > 0;
    }

    private Review assembleReview() {
        IdableCollection<Review> criteria = new MdIdableCollection<>();
        for (ReviewBuilderImpl child : mChildren) {
            criteria.add(child.assembleReview());
        }

        return mReviewFactory.createUserReview(getSubject(), getRating(),
                getData(GvCommentList.GvComment.TYPE),
                getData(GvImageList.GvImage.TYPE),
                getData(GvFactList.GvFact.TYPE),
                getData(GvLocationList.GvLocation.TYPE),
                criteria, mIsAverage);
    }

    private void setCriteria(GvDataList children) {
        mChildren = new ArrayList<>();
        for (GvCriterionList.GvCriterion child : (GvCriterionList) children) {
            ReviewBuilderImpl childBuilder = new ReviewBuilderImpl(mConverter, mTagsManager,
                    mReviewFactory, mDataBuilderFactory, mDataValidator);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvCriterionList.GvCriterion.TYPE, mConverter.copy(children));
    }
}
