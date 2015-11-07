package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ViewerTreeData extends ViewerReviewData {
    private GvDataAggregater mAggregater;
    private FactoryGridDataViewer mViewerFactory;

    ViewerTreeData(ReviewNode node,
                   MdGvConverter converter,
                   TagsManager tagsManager,
                   FactoryGridDataViewer viewerFactory,
                   FactoryReviewViewAdapter adapterFactory,
                   GvDataAggregater aggregater) {
        super(node, converter, tagsManager, adapterFactory);
        mAggregater = aggregater;
        mViewerFactory = viewerFactory;
    }

    //Overridden
    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();

        MdGvConverter converter = getConverter();
        GridDataViewer wrapper = mViewerFactory.newChildListViewer(node, converter,
                getTagsManager(), adapterFactory);
        ReviewId id = node.getId();
        IdableList<ReviewNode> nodes = node.getChildren();
        GvList data = new GvList(GvReviewId.getId(node.getId().toString()));
        data.add(wrapper.getGridData());
        data.add(mAggregater.getAggregate(converter.convertAuthors(nodes, id)));
        data.add(mAggregater.getAggregate(converter.convertSubjects(nodes, id)));
        data.add(mAggregater.getAggregate(converter.convertPublishDates(nodes, id)));
        data.add(mAggregater.getAggregate(collectTags()));
        data.add(mAggregater.getAggregate(converter.convert(node.getCriteria()), false));
        data.add(mAggregater.getAggregate(converter.convert(node.getImages())));
        data.add(mAggregater.getAggregate(converter.convert(node.getComments())));
        data.add(mAggregater.getAggregate(converter.convert(node.getLocations())));
        data.add(mAggregater.getAggregate(converter.convert(node.getFacts())));

        return data;
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();
        ReviewViewAdapter adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType().equals(GvReviewOverviewList.GvReviewOverview.TYPE)) {
                adapter = adapterFactory.newReviewsListAdapter(getReviewNode());
            } else {
                String subject = datum.getStringSummary();
                GvCanonicalCollection data = (GvCanonicalCollection) datum;
                adapter = adapterFactory.newAggregateToReviewsAdapter(data, subject);
            }
        }

        return adapter;
    }

    private GvTagList collectTags() {
        ReviewNode node = getReviewNode();
        MdDataList<ReviewId> ids = new MdDataList<>(node.getId());
        for (Review review : VisitorReviewsGetter.flatten(node)) {
            ids.add(review.getId());
        }

        GvTagList tags = new GvTagList(GvReviewId.getId(node.getId().toString()));
        for (ReviewId id : ids) {
            for (GvTagList.GvTag tag : getTags(id.toString())) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
