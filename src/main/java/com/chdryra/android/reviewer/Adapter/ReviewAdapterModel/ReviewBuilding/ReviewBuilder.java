package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
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
public class ReviewBuilder {
    public static final ArrayList<GvDataType> TYPES = ConfigGvDataUi.BUILD_TYPES;

    private final Map<GvDataType, GvDataList> mData;
    private final Map<GvDataType, DataBuilder> mDataBuilders;

    private String mSubject;
    private float mRating;
    private ArrayList<ReviewBuilder> mChildren;
    private boolean mIsAverage = false;

    private ConverterGv mConverter;
    private TagsManager mTagsManager;
    private FactoryReview mReviewFactory;
    private FactoryDataBuilder mDataBuilderFactory;
    private DataValidator mDataValidator;

    //Constructors
    public ReviewBuilder(ConverterGv converter,
                         TagsManager tagsManager,
                         FactoryReview reviewFactory,
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

    public <T extends GvData> void resetDataBuilder(GvDataType<T> dataType) {
        getDataBuilder(dataType).resetData();
    }

    //TODO make type safe
    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        return mData.get(dataType);
    }

    public <T extends GvData> void setData(GvDataList<T> data, boolean copy) {
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            setCriteria(data);
        } else if (TYPES.contains(dataType)) {
            if (copy) {
                mData.put(dataType, mConverter.copy(data));
            } else {
                mData.put(dataType, data);
            }
        }
    }

    public Review buildReview(ReviewPublisher publisher) {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        Review review = assembleReview(publisher);
        GvTagList tags = (GvTagList) getData(GvTagList.GvTag.TYPE);
        mTagsManager.tagItem(review.getReviewId(), tags.toStringArray());

        return review;
    }

    //private methods
    private boolean isValidForPublication() {
        return mDataValidator.validateString(mSubject) && getData(GvTagList.GvTag.TYPE).size() > 0;
    }

    private Review assembleReview(ReviewPublisher publisher) {
        IdableCollection<Review> criteria = new MdIdableCollection<>();
        for (ReviewBuilder child : mChildren) {
            criteria.add(child.assembleReview(publisher));
        }

        return mReviewFactory.createUserReview(publisher, getSubject(), getRating(),
                getData(GvCommentList.GvComment.TYPE),
                getData(GvImageList.GvImage.TYPE),
                getData(GvFactList.GvFact.TYPE),
                getData(GvLocationList.GvLocation.TYPE),
                criteria, mIsAverage);
    }

    private void setCriteria(GvDataList children) {
        mChildren = new ArrayList<>();
        for (GvCriterionList.GvCriterion child : (GvCriterionList) children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mConverter, mTagsManager,
                    mReviewFactory, mDataBuilderFactory, mDataValidator);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvCriterionList.GvCriterion.TYPE, mConverter.copy(children));
    }
}
