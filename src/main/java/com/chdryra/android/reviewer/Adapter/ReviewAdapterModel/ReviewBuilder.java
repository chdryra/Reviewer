package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataHandler;
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

    private final Context mContext;
    private final Map<GvDataType, GvDataList> mData;
    private final Map<GvDataType, DataBuilder> mDataBuilders;

    private String mSubject;
    private float mRating;
    private ArrayList<ReviewBuilder> mChildren;
    private boolean mIsAverage = false;
    private Author mAuthor;
    private TagsManager mTagsManager;

    //Constructors
    public ReviewBuilder(Context context, Author author, TagsManager tagsManager) {
        mContext = context;
        mAuthor = author;
        mTagsManager = tagsManager;
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataType dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newDataList(dataType));
            mDataBuilders.put(dataType, newDataBuilder(dataType));
        }

        mSubject = "";
        mRating = 0f;
    }

    //public methods
    public Context getContext() {
        return mContext;
    }

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
        return getCriteria().getAverageRating();
    }

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getCriteria().getAverageRating();
    }

    //TODO make type safe
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        return (DataBuilder<T>) mDataBuilders.get(dataType);
    }

    public <T extends GvData> void resetDataBuilder(GvDataType<T> dataType) {
        getDataBuilder(dataType).resetData();
    }

    public Review buildReview() {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        PublishDate date = PublishDate.now();
        Review review = assembleReview(new ReviewPublisher(mAuthor, date));
        GvTagList tags = (GvTagList) getData(GvTagList.GvTag.TYPE);
        mTagsManager.tagReview(review.getId(), tags.toStringArray());

        return review;
    }

    //TODO make type safe
    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        GvDataList data = mData.get(dataType);
        return data != null ? MdGvConverter.copy(data) : null;
    }

    public <T extends GvData> void setData(GvDataList<T> data, boolean copy) {
        GvDataType<T> dataType = data.getGvDataType();
        if (dataType == GvCriterionList.GvCriterion.TYPE) {
            setCriteria(data);
        } else if (TYPES.contains(dataType)) {
            if (copy) {
                mData.put(dataType, MdGvConverter.copy(data));
            } else {
                mData.put(dataType, data);
            }
        }
    }

    //private methods
    private boolean isValidForPublication() {
        return DataValidator.validateString(mSubject);
    }

    private GvCriterionList getCriteria() {
        return (GvCriterionList) getData(GvCriterionList.GvCriterion.TYPE);
    }

    private Review assembleReview(ReviewPublisher publisher) {
        IdableList<Review> criteria = new IdableList<>();
        for (ReviewBuilder child : mChildren) {
            criteria.add(child.assembleReview(publisher));
        }

        return FactoryReview.createReviewUser(publisher, getSubject(), getRating(),
                getData(GvCommentList.GvComment.TYPE),
                getData(GvImageList.GvImage.TYPE),
                getData(GvFactList.GvFact.TYPE),
                getData(GvLocationList.GvLocation.TYPE),
                criteria, mIsAverage);
    }

    //TODO make type safe
    private <T extends GvData> DataBuilder<T> newDataBuilder(GvDataType<T> dataType) {
        return new DataBuilder<>(MdGvConverter.copy(getData(dataType)));
    }

    private void setCriteria(GvDataList children) {
        mChildren = new ArrayList<>();
        for (GvCriterionList.GvCriterion child : (GvCriterionList) children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mContext, mAuthor, mTagsManager);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvCriterionList.GvCriterion.TYPE, MdGvConverter.copy(children));
    }

    public class DataBuilder<T extends GvData> {
        private GvDataHandler<T> mHandler;

        private DataBuilder(GvDataList<T> data) {
            mHandler = FactoryGvDataHandler.newHandler(data);
        }

        //public methods
        public ReviewBuilder getParentBuilder() {
            return ReviewBuilder.this;
        }

        public GvDataList<T> getGvData() {
            return mHandler.getData();
        }

        public void reset() {
            getParentBuilder().resetDataBuilder(mHandler.getGvDataType());
        }

        public boolean add(T datum) {
            return mHandler.add(datum, mContext);
        }

        public void delete(T datum) {
            mHandler.delete(datum);
        }

        public void deleteAll() {
            mHandler.deleteAll();
        }

        public void replace(T oldDatum, T newDatum) {
            mHandler.replace(oldDatum, newDatum, mContext);
        }

        public void setData() {
            getParentBuilder().setData(getGvData(), true);
        }

        public void resetData() {
            GvDataType<T> type = mHandler.getGvDataType();
            mHandler = FactoryGvDataHandler.newHandler(getData(type));
        }
    }
}
