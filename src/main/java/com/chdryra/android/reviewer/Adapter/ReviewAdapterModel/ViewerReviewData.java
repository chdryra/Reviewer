package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ViewerReviewData implements GridDataViewer<GvData> {
    private ReviewNode mNode;
    private MdGvConverter mConverter;
    private TagsManager mTagsManager;
    private FactoryReviewViewAdapter mAdapterFactory;
    private GvList mCache;

    ViewerReviewData(ReviewNode node,
                     MdGvConverter converter,
                     TagsManager tagsManager,
                     FactoryReviewViewAdapter adapterFactory) {
        mNode = node;
        mConverter = converter;
        mTagsManager = tagsManager;
        mAdapterFactory = adapterFactory;
    }

    //protected methods
    protected ReviewNode getReviewNode() {
        return mNode;
    }

    protected FactoryReviewViewAdapter getAdapterFactory() {
        return mAdapterFactory;
    }

    protected MdGvConverter getConverter() {
        return mConverter;
    }

    protected TagsManager getTagsManager() {
        return mTagsManager;
    }

    protected GvList makeGridData() {
        Review review = mNode.getReview();
        GvReviewId id = GvReviewId.getId(review.getMdReviewId().toString());

        GvList data = new GvList(id);
        data.add(getTags(review.getMdReviewId().toString()));
        data.add(mConverter.toGvDataList(review.getCriteria()));
        data.add(mConverter.toGvDataList(review.getImages()));
        data.add(mConverter.toGvDataList(review.getComments()));
        data.add(mConverter.toGvDataList(review.getLocations()));
        data.add(mConverter.toGvDataList(review.getFacts()));

        return data;
    }

    //Overridden
    @Override
    public GvList getGridData() {
        GvList data = makeGridData();
        mCache = data;
        return data;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if (!datum.hasElements() || mCache == null) return false;

        GvDataCollection data = (GvDataCollection) datum;
        for (GvData list : mCache) {
            ((GvDataCollection) list).sort();
        }
        data.sort();

        return mCache.contains(datum);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        if (isExpandable(datum)) {
            return mAdapterFactory.newDataToDataAdapter(mNode,
                    (GvDataCollection<? extends GvData>) datum);
        } else {
            return null;
        }
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return null;
    }

    protected GvTagList getTags(String reviewId) {
        MdReviewId id = MdReviewId.fromString(reviewId);
        ItemTagCollection tags = mTagsManager.getTags(id);
        GvReviewId gvid = GvReviewId.getId(reviewId);
        GvTagList tagList = new GvTagList(gvid);
        for (ItemTag tag : tags) {
            tagList.add(new GvTagList.GvTag(gvid, tag.getTag()));
        }

        return tagList;
    }
}
