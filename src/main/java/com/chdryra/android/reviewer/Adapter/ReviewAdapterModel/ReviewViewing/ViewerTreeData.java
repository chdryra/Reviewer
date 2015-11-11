package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ViewerTreeData extends ViewerReviewData {
    private GvDataAggregater mAggregater;

    ViewerTreeData(ReviewNode node,
                   ConverterGv converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   GvDataAggregater aggregater) {
        super(node, converter, tagsManager, adapterFactory);
        mAggregater = aggregater;
    }

    //Overridden
    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        String id = node.getReviewId();
        IdableList<ReviewNode> nodes = node.getChildren();

        ConverterGv converter = getConverter();
        GvList data = new GvList(new GvReviewId(id));
        data.add(converter.toGvReviewOverviewList(nodes));
        data.add(mAggregater.getAggregate(converter.toGvAuthorList(nodes, id)));
        data.add(mAggregater.getAggregate(converter.toGvSubjectList(nodes, id)));
        data.add(mAggregater.getAggregate(converter.toGvDateList(nodes, id)));
        data.add(mAggregater.getAggregate(collectTags()));
        data.add(mAggregater.getAggregate(converter.toGvCriterionList(node.getCriteria()), false));
        data.add(mAggregater.getAggregate(converter.toGvImageList(node.getImages())));
        data.add(mAggregater.getAggregate(converter.toGvCommentList(node.getComments())));
        data.add(mAggregater.getAggregate(converter.toGvLocationList(node.getLocations())));
        data.add(mAggregater.getAggregate(converter.toGvFactList(node.getFacts())));

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
        String nodeId = node.getReviewId();
        ArrayList<String> ids = new ArrayList<>();
        for (Review review : VisitorReviewsGetter.flatten(node)) {
            ids.add(review.getReviewId());
        }

        GvTagList gvTags = new GvTagList(new GvReviewId(nodeId));
        for (String id : ids) {
            gvTags.addList(getConverter().toGvTagList(getTagsManager().getTags(id), nodeId));
        }

        return gvTags;
    }
}
