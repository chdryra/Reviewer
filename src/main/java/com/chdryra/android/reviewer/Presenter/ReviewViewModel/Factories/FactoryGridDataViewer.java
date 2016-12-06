/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Algorithms.DataSorting.DataBucketer;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateToData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerAggregateToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerBuckets;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerDataToReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerFeed;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerReviewData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ViewerReviewSummary;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerTreeData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerTreeSummary;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGridDataViewer {
    private final FactoryReviewViewAdapter mAdapterFactory;
    private final FactoryReference mReferenceFactory;
    private final AuthorsRepository mAuthorsRepository;
    private final ConverterGv mConverter;

    FactoryGridDataViewer(FactoryReviewViewAdapter adapterFactory,
                          FactoryReference referenceFactory,
                          AuthorsRepository authorsRepository,
                          ConverterGv converter) {
        mAdapterFactory = adapterFactory;
        mReferenceFactory = referenceFactory;
        mAuthorsRepository = authorsRepository;
        mConverter = converter;
    }

    <BucketingValue, Data extends HasReviewId>
    GridDataWrapper<GvBucket> newBucketViewer(ReviewNode node,
                                              ViewHolderFactory<VhBucket<BucketingValue, Data>> vhFactory,
                                              DataBucketer<BucketingValue, Data> bucketer) {
        return new ViewerBuckets<>(node, mAdapterFactory, vhFactory, bucketer);
    }

    GridDataWrapper<GvNode> newFeedViewer(ReviewNode node) {
        return new ViewerFeed(node, mConverter.newConverterNodes(mAuthorsRepository), mAdapterFactory);
    }

    GridDataWrapper<GvNode> newChildViewer(ReviewNode node) {
        return new ViewerChildList(node, mConverter.newConverterNodes(mAuthorsRepository), mAdapterFactory);
    }

    GridDataWrapper<?> newTreeSummaryViewer(ReviewNode node) {
        return new ViewerTreeSummary(node, mAdapterFactory, mConverter);
    }

    GridDataWrapper<?> newNodeSummaryViewer(ReviewNode node) {
        return new ViewerReviewSummary(node, mAdapterFactory, mConverter);
    }

    ViewerReviewData.CommentList newReviewCommentsViewer(ReviewNode node) {
        ReviewStamp stamp = ReviewStamp.newStamp(node.getAuthorId(), node.getPublishDate());
        return new ViewerReviewData.CommentList(node.getComments(),
                mConverter.newConverterComments().getReferencesConverter(), stamp, mReferenceFactory);
    }

    ViewerTreeData.TreeCommentList newTreeCommentsViewer(ReviewNode node) {
        return new ViewerTreeData.TreeCommentList(node.getComments(),
                mConverter.newConverterComments().getReferencesConverter(), mAdapterFactory, mReferenceFactory);
    }

    ViewerTreeData.TreeAuthorList newTreeAuthorsViewer(ReviewNode node) {
        return new ViewerTreeData.TreeAuthorList(node.getAuthorIds(),
                mConverter.newConverterAuthorsIds(mAuthorsRepository).getReferencesConverter(), mAdapterFactory);
    }

    @Nullable
    GridDataWrapper<?> newReviewDataViewer(ReviewNode node,
                                           GvDataType<?> dataType) {
        ReviewStamp stamp = ReviewStamp.newStamp(node.getAuthorId(), node.getPublishDate());
        GridDataWrapper<?> viewer = null;
        if (dataType.equals(GvTag.TYPE)) {
            viewer = new ViewerReviewData.DataList<>(node.getTags(),
                    mConverter.newConverterTags().getReferencesConverter(), stamp);
        } else if (dataType.equals(GvCriterion.TYPE)) {
            viewer = new ViewerReviewData.DataList<>(node.getCriteria(),
                    mConverter.newConverterCriteria().getReferencesConverter(), stamp);
        } else if (dataType.equals(GvImage.TYPE)) {
            viewer = new ViewerReviewData.DataList<>(node.getImages(),
                    mConverter.newConverterImages().getReferencesConverter(), stamp);
        } else if (dataType.equals(GvComment.TYPE)) {
            viewer = newReviewCommentsViewer(node);
        } else if (dataType.equals(GvLocation.TYPE)) {
            viewer = new ViewerReviewData.DataList<>(node.getLocations(),
                    mConverter.newConverterLocations().getReferencesConverter(), stamp);
        } else if (dataType.equals(GvFact.TYPE)) {
            viewer = new ViewerReviewData.DataList<>(node.getFacts(),
                    mConverter.newConverterFacts().getReferencesConverter(), stamp);
        }

        return viewer;
    }

    @Nullable
    GridDataWrapper<?> newTreeDataViewer(ReviewNode node,
                                         GvDataType<?> dataType) {
        GridDataWrapper<?> viewer = null;
        if (dataType.equals(GvTag.TYPE)) {
            viewer = new ViewerTreeData<>(node.getTags(), mConverter.newConverterTags()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvCriterion.TYPE)) {
            viewer = new ViewerTreeData<>(node.getCriteria(), mConverter.newConverterCriteria()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvImage.TYPE)) {
            viewer = new ViewerTreeData<>(node.getImages(), mConverter.newConverterImages()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvComment.TYPE)) {
            viewer = newTreeCommentsViewer(node);
        } else if (dataType.equals(GvLocation.TYPE)) {
            viewer = new ViewerTreeData<>(node.getLocations(), mConverter.newConverterLocations
                    ().getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvFact.TYPE)) {
            viewer = new ViewerTreeData<>(node.getFacts(), mConverter.newConverterFacts()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvAuthorId.TYPE)) {
        viewer = newTreeAuthorsViewer(node);
        } else if (dataType.equals(GvSubject.TYPE)) {
            viewer = new ViewerTreeData<>(node.getSubjects(), mConverter.newConverterSubjects()
                    .getReferencesConverter(), mAdapterFactory);
        } else if (dataType.equals(GvDate.TYPE)) {
            viewer = new ViewerTreeData<>(node.getDates(), mConverter.newConverterDateReviews()
                    .getReferencesConverter(), mAdapterFactory);
        }

        return viewer;
    }

    //Old aggregate stuff
//
//    public <T extends GvData> GridDataWrapper<T> newDataToDataViewer(ReviewNode parent,
//                                                                    GvDataType<T> dataType) {
//        return new ViewerMetaDataToData<>(parent, dataType, mAdapterFactory);
//    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToDataViewer
            (GvCanonicalCollection<T> data, GvDataAggregator aggregateFactory) {
        GridDataWrapper<GvCanonical> viewer;
        if (data.getGvDataType().equals(GvCriterion.TYPE)) {
            //TODO make type safe
            viewer = new ViewerAggregateCriteria((GvCanonicalCollection<GvCriterion>) data,
                    this, mAdapterFactory, aggregateFactory);
        } else {
            viewer = new ViewerAggregateToData<>(data, this, mAdapterFactory);
        }

        return viewer;
    }

    public <T extends GvData> GridDataWrapper<T> newDataToReviewsViewer(GvDataCollection<T> data) {
        return new ViewerDataToReviews<>(data, mAdapterFactory);
    }

    public <T extends GvData> GridDataWrapper<GvCanonical> newAggregateToReviewsViewer
            (GvCanonicalCollection<T> data) {
        return new ViewerAggregateToReviews<>(data, mAdapterFactory);
    }
}
