package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDataList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
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

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ViewerTreeData extends ViewerReviewData {
    private GvDataAggregater mAggregater;
    private FactoryGridDataViewer mViewerFactory;

    ViewerTreeData(ReviewNode node,
                   ConverterGv converter,
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

        ConverterGv converter = getConverter();
        GridDataViewer wrapper = mViewerFactory.newChildListViewer(node, converter, adapterFactory);
        String id = node.getReviewId();
        IdableCollection<ReviewNode> nodes = node.getChildren();
        GvList data = new GvList(new GvReviewId(id));
        data.add(wrapper.getGridData());
        data.add(mAggregater.getAggregate(converter.convertAuthors(nodes, id)));
        data.add(mAggregater.getAggregate(converter.convertSubjects(nodes, id)));
        data.add(mAggregater.getAggregate(converter.convertPublishDates(nodes, id)));
        data.add(mAggregater.getAggregate(collectTags()));
        data.add(mAggregater.getAggregate(converter.toGvDataList(node.getCriteria()), false));
        data.add(mAggregater.getAggregate(converter.toGvDataList(node.getImages())));
        data.add(mAggregater.getAggregate(converter.toGvDataList(node.getComments())));
        data.add(mAggregater.getAggregate(converter.toGvDataList(node.getLocations())));
        data.add(mAggregater.getAggregate(converter.toGvDataList(node.getFacts())));

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
        MdDataList<MdReviewId> ids = new MdDataList<>(node.getMdReviewId());
        for (Review review : VisitorReviewsGetter.flatten(node)) {
            ids.add(review.getMdReviewId());
        }

        GvTagList tags = new GvTagList(GvReviewId.getId(node.getMdReviewId().toString()));
        for (MdReviewId id : ids) {
            for (GvTagList.GvTag tag : getTags(id.toString())) {
                tags.add(tag);
            }
        }

        return tags;
    }
}
