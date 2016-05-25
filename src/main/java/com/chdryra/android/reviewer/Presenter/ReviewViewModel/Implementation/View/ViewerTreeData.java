/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.NodesTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewDataGetter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData extends ViewerReviewData {
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;
    private GvDataAggregator mAggregater;

    public ViewerTreeData(ReviewNode node,
                   ConverterGv converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   FactoryVisitorReviewNode visitorFactory,
                   FactoryNodeTraverser traverserFactory,
                   GvDataAggregator aggregater) {
        super(node, converter, tagsManager, adapterFactory);
        mAggregater = aggregater;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        ReviewId id = node.getReviewId();
        IdableList<ReviewNode> nodes = node.getChildren();

        ConverterGv converter = getConverter();
        GvList data = new GvList(new GvReviewId(id));
        data.add(converter.toGvReviewList(nodes));
        data.add(mAggregater.aggregateAuthors(converter.toGvAuthorList(nodes, id)));
        data.add(mAggregater.aggregateSubjects(converter.toGvSubjectList(nodes, id)));
        data.add(mAggregater.aggregateDates(converter.toGvDateList(nodes, id)));
        data.add(mAggregater.aggregateTags(collectTags()));
        data.add(mAggregater.aggregateCriteria(node.getCriteria(), GvDataAggregator.CriterionAggregation.SUBJECT));
        data.add(mAggregater.aggregateImages(node.getImages()));
        data.add(mAggregater.aggregateComments(node.getComments()));
        data.add(mAggregater.aggregateLocations(node.getLocations()));
        data.add(mAggregater.aggregateFacts(node.getFacts()));

        return data;
    }

    @Override
    public GvAuthor getUniqueAuthor() {
        return new GvAuthor();
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvData datum) {
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();
        ReviewViewAdapter<?> adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType().equals(GvReview.TYPE)) {
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
        NodesTraverser traverser = mTraverserFactory.newTreeTraverser(getReviewNode());
        traverser.addVisitor(visitor);
        traverser.traverse();
        return getConverter().toGvTagList(visitor.getData(), getReviewNode().getReviewId());
    }
}
