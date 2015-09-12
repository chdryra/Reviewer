package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.Configs.ConfigGvDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvDataHandler;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
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
    public static final ArrayList<GvDataType<? extends GvData>> TYPES = ConfigGvDataUi.TYPES;

    private final Context mContext;
    private final Map<GvDataType<? extends GvData>, GvDataList<?>> mData;
    private final Map<GvDataType<? extends GvData>, DataBuilder<?>> mDataBuilders;

    private String                   mSubject;
    private float                    mRating;
    private ArrayList<ReviewBuilder> mChildren;
    private boolean mIsAverage = false;
    private Author mAuthor;

    public ReviewBuilder(Context context, Author author) {
        mContext = context;
        mAuthor = author;
        mChildren = new ArrayList<>();

        mData = new HashMap<>();
        mDataBuilders = new HashMap<>();
        for (GvDataType<? extends GvData> dataType : TYPES) {
            mData.put(dataType, FactoryGvData.newDataList(dataType));
            mDataBuilders.put(dataType, newDataBuilder(dataType));
        }

        mSubject = "";
        mRating = 0f;
    }

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

    public void setRatingIsAverage(boolean ratingIsAverage) {
        mIsAverage = ratingIsAverage;
        if (ratingIsAverage) mRating = getChildren().getAverageRating();
    }

    //TODO make type safe
    public <T extends GvData> DataBuilder<T> getDataBuilder(GvDataType<T> dataType) {
        return (DataBuilder<T>) mDataBuilders.get(dataType.getElementType());
    }

    public <T extends GvData> void resetDataBuilder(GvDataType<T> dataType) {
        getDataBuilder(dataType).resetData();
    }

    public ReviewNode buildReview(PublishDate publishDate) {
        if (!isValidForPublication()) {
            throw new IllegalStateException("Review is not valid for publication!");
        }

        ReviewTreeNode rootNode = prepareTree(publishDate);
        ReviewNode published = rootNode.createTree();
        tagTree(published);

        return published;
    }

    //TODO make type safe
    public <T extends GvData> GvDataList<T> getData(GvDataType<T> dataType) {
        GvDataList data = mData.get(dataType.getElementType());
        return data != null ? MdGvConverter.copy(data) : null;
    }

    private boolean isValidForPublication() {
        return DataValidator.validateString(mSubject) && getData(GvTagList.GvTag.TYPE).size() > 0;
    }

    private void tagTree(ReviewNode node) {
        GvTagList tags = (GvTagList) getData(GvTagList.GvTag.TYPE);
        TagsManager.tag(node.getId(), tags.toStringArray());
        for (ReviewNode child : node.getChildren()) {
            tagTree(child);
        }
    }

    private ReviewTreeNode prepareTree(PublishDate publishDate) {
        Review root = FactoryReview.createReviewUser(mAuthor,
                publishDate, getSubject(), getRating(),
                getData(GvCommentList.GvComment.TYPE),
                getData(GvImageList.GvImage.TYPE),
                getData(GvFactList.GvFact.TYPE),
                getData(GvLocationList.GvLocation.TYPE));

        ReviewTreeNode rootNode = FactoryReview.createReviewTreeNode(root, mIsAverage);

        for (ReviewBuilder child : mChildren) {
            rootNode.addChild(child.prepareTree(publishDate));
        }

        return rootNode;
    }

    //TODO make type safe
    private <T extends GvData> DataBuilder<T> newDataBuilder(GvDataType<T> dataType) {
        return new DataBuilder<>(MdGvConverter.copy(getData(dataType)));
    }

    //TODO make type safe
    public <T extends GvData> void setData(GvDataList<T> data) {
        GvDataType<T> dataType = data.getGvDataType().getElementType();
        if (dataType == GvChildReviewList.GvChildReview.TYPE) {
            setChildren((GvChildReviewList) data);
        } else if (TYPES.contains(dataType)) {
            mData.put(dataType, MdGvConverter.copy(data));
        }
    }

    private GvChildReviewList getChildren() {
        return (GvChildReviewList) getData(GvChildReviewList.GvChildReview.TYPE);
    }

    private void setChildren(GvChildReviewList children) {
        mChildren = new ArrayList<>();
        for (GvChildReviewList.GvChildReview child : children) {
            ReviewBuilder childBuilder = new ReviewBuilder(mContext, mAuthor);
            childBuilder.setSubject(child.getSubject());
            childBuilder.setRating(child.getRating());
            mChildren.add(childBuilder);
        }
        mData.put(GvChildReviewList.GvChildReview.TYPE, MdGvConverter.copy(children));
    }

    public float getRating() {
        return isRatingAverage() ? getAverageRating() : mRating;
    }

    public void setRating(float rating) {
        if (!isRatingAverage()) mRating = rating;
    }

    public float getAverageRating() {
        return getChildren().getAverageRating();
    }

    public class DataBuilder<T extends GvData> {
        private GvDataHandler<T> mHandler;

        private DataBuilder(GvDataList<T> data) {
            mHandler = FactoryGvDataHandler.newHandler(data);
        }

        public void reset() {
            getParentBuilder().resetDataBuilder(mHandler.getGvDataType());
        }

        public ReviewBuilder getParentBuilder() {
            return ReviewBuilder.this;
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
            getParentBuilder().setData(getGvData());
        }

        public GvDataList<T> getGvData() {
            return mHandler.getData();
        }

        public void resetData() {
            //TODO make type safe
            GvDataType<T> type = mHandler.getGvDataType().getElementType();
            mHandler = FactoryGvDataHandler.newHandler(getData(type));
        }

        public String getSubject() {
            return getParentBuilder().getSubject();
        }

        public void setSubject(String subject) {
            getParentBuilder().setSubject(subject);
        }

        public float getRating() {
            return getParentBuilder().getRating();
        }

        public void setRating(float rating) {
            getParentBuilder().setRating(rating);
        }

        public float getAverageRating() {
            return mHandler.getGvDataType().getElementType() == GvChildReviewList.GvChildReview
                    .TYPE ?
                    ReviewBuilder.this.getAverageRating() : getRating();
        }
    }
}
