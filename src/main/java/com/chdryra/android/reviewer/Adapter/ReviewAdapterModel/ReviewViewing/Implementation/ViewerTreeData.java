package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewDataGetter;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataAggregater;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewId;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData extends ViewerReviewData {
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryReviewTreeTraverser mTraverserFactory;
    private GvDataAggregater mAggregater;

    public ViewerTreeData(ReviewNode node,
                   ConverterGv converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   FactoryVisitorReviewNode visitorFactory,
                   FactoryReviewTreeTraverser traverserFactory,
                   GvDataAggregater aggregater) {
        super(node, converter, tagsManager, adapterFactory);
        mAggregater = aggregater;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
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
        data.add(mAggregater.aggregateAuthors(converter.toGvAuthorList(nodes, id)));
        data.add(mAggregater.aggregateSubjects(converter.toGvSubjectList(nodes, id)));
        data.add(mAggregater.aggregateDates(converter.toGvDateList(nodes, id)));
        data.add(mAggregater.aggregateTags(collectTags()));
        data.add(mAggregater.aggregateCriteria(node.getCriteria(), GvDataAggregater.CriterionAggregation.SUBJECT));
        data.add(mAggregater.aggregateImages(node.getImages()));
        data.add(mAggregater.aggregateComments(node.getComments()));
        data.add(mAggregater.aggregateLocations(node.getLocations()));
        data.add(mAggregater.aggregateFacts(node.getFacts()));

        return data;
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();
        ReviewViewAdapter adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType().equals(GvReviewOverview.TYPE)) {
                adapter = adapterFactory.newReviewsListAdapter(getReviewNode());
            } else {
                String subject = datum.getStringSummary();
                GvCanonicalCollection<?> data = (GvCanonicalCollection<?>) datum;
                adapter = adapterFactory.newAggregateToReviewsAdapter(data, subject);
            }
        }

        return adapter;
    }

    private GvTagList collectTags() {
        VisitorReviewDataGetter<DataTag> visitor = mVisitorFactory.newTagsCollector(getTagsManager());
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(getReviewNode());
        traverser.traverse();
        return getConverter().toGvTagList(visitor.getData());
    }
}
