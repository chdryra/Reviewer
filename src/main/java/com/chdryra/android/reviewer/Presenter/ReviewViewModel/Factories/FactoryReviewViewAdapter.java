/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishScreenAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AuthorSearchAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeFlat;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeSourceCallback;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerFeed;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private final FactoryReviews mReviewsFactory;
    private final FactoryGridDataViewer mViewerFactory;
    private final GvDataAggregator mAggregator;
    private final ConverterGv mConverter;
    private final AuthorsRepository mAuthorsRepository;
    private final ReviewsSource mReviewSource;
    private FactoryReviewView mReviewViewFactory;

    public FactoryReviewViewAdapter(FactoryReviews reviewsFactory,
                                    FactoryReference referenceFactory,
                                    GvDataAggregator aggregator,
                                    ReviewsSource reviewsSource,
                                    AuthorsRepository authorsRepository,
                                    ConverterGv converter) {
        mAuthorsRepository = authorsRepository;
        mReviewsFactory = reviewsFactory;
        mAggregator = aggregator;
        mReviewSource = reviewsSource;
        mConverter = converter;
        mViewerFactory = new FactoryGridDataViewer(this, referenceFactory, mAuthorsRepository);
    }


    public <T extends GvData> ReviewViewAdapter<T> newNullAdapter(GvDataType<T> dataType) {
        return new ReviewViewAdapterImpl<T>();
    }

    //List reviews generating this datum
    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(T datum) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.asMetaReview(datum, datum.getStringSummary(), node);
        boolean isAuthor = datum.getGvDataType().equals(GvAuthorId.Reference.TYPE);
        AuthorId toFollow = isAuthor ? ((GvAuthorId.Reference) datum).getDataValue() : null;
        return newReviewsListAdapter(node, toFollow);
    }

    //List reviews generating this data
    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(GvDataCollection<T> data) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        return newReviewsListAdapter(node, null);
    }

    //List reviews for this author
    public ReviewViewAdapter<?> newReviewsListAdapter(AuthorId id) {
        return newReviewsListAdapter(mReviewSource.asMetaReview(id), id);
    }

    //List all reviews in the tree
    public ReviewViewAdapter<?> newFlattenedReviewsListAdapter(ReviewNode toFlatten) {
        return newReviewsListAdapter(new ReviewTreeFlat(toFlatten, mReviewsFactory), null);
    }

    //Summary of reviews for this author
    public ReviewViewAdapter<?> newTreeSummaryAdapter(AuthorId summaryOwner,
                                                      Set<AuthorId> reviewAuthors) {
        RepositoryCollection<AuthorId> collection = new RepositoryCollection<>();
        for (AuthorId author : reviewAuthors) {
            collection.add(author, mReviewSource.getRepositoryForAuthor(author));
        }

        ReviewNode node = mReviewsFactory.createFeed(summaryOwner, collection, mAuthorsRepository);
        GridDataWrapper<?> viewer = mViewerFactory.newTreeSummaryViewer(node, mConverter);

        return newNodeAdapter(node, viewer);
    }

    //Summary of reviews for this review tree
    public ReviewViewAdapter<?> newTreeSummaryAdapter(ReviewNode node) {
        GridDataWrapper<?> viewer = mViewerFactory.newDataSummaryViewer(node, mConverter);
        return newNodeAdapter(node, viewer);
    }

    //Summary of reviews generating this data
    public <T extends GvData> ReviewViewAdapter<?> newTreeSummaryAdapter(GvDataCollection<T> data) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, data.getStringSummary(), node);
        GridDataWrapper<?> viewer = mViewerFactory.newDataSummaryViewer(node, mConverter);
        return newNodeAdapter(node, viewer);
    }

    //View specific data for this review as if it was a single review
    @Nullable
    public <T extends GvData> ReviewViewAdapter<?> newReviewDataAdapter(ReviewNode node,
                                                                        GvDataType<T> dataType) {
        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node, mConverter.newConverterImages(), mViewerFactory
                    .newReviewCommentsViewer(node, mConverter));
        } else {
            return newNodeAdapter(node, mViewerFactory.newReviewDataViewer(node, dataType,
                    mConverter));
        }
    }

    //View specific data for this review as if it was a meta review
    public <T extends GvData> ReviewViewAdapter<?> newTreeDataAdapter(ReviewNode node,
                                                                      GvDataType<T> dataType) {

        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node, mConverter.newConverterImages(), mViewerFactory
                    .newTreeCommentsViewer(node, mConverter));
        } else {
            return newNodeAdapter(node, mViewerFactory.newTreeDataViewer(node, dataType,
                    mConverter));
        }
    }

    //View for search screen
    public ReviewViewAdapter.Filterable<GvAuthor> newFollowSearchAdapter(AuthorId sessionUser) {
        return new AuthorSearchAdapter(new ViewerAuthors(sessionUser, new GvAuthorList(), this),
                mAuthorsRepository, mConverter.newConverterAuthors());
    }

    //Old aggregate stuff
    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter
    (GvCanonicalCollection<T> data) {
        return newReviewsListAdapter(newFlattenedMetaReview(data, data.getStringSummary()), null);
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
    (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewTreeSourceCallback node = newFlattenedMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node, mConverter.newConverterImages(),
                    (GvCanonicalCollection<GvComment>) data, mViewerFactory, mAggregator);
        }

        GridDataWrapper<GvCanonical> viewer;
        boolean aggregateToData = type.equals(GvCriterion.TYPE) ||
                type.equals(GvFact.TYPE) || type.equals(GvImage.TYPE);
        if (aggregateToData) {
            viewer = mViewerFactory.newAggregateToDataViewer(data, mAggregator);
        } else {
            viewer = mViewerFactory.newAggregateToReviewsViewer(data);
        }

        return newAggregatedMetaReviewAdapter(data, subject, viewer);
    }

    public <T extends GvData> ReviewViewAdapter<T> newDataToReviewsAdapter(GvDataCollection<T> data,
                                                                           String subject) {
        GridDataWrapper<T> viewer = mViewerFactory.newDataToReviewsViewer(data);
        return newMetaReviewAdapter(data, subject, viewer);
    }

    void setReviewViewFactory(FactoryReviewView reviewViewFactory) {
        mReviewViewFactory = reviewViewFactory;
    }

    ReviewViewAdapter<GvNode> newChildListAdapter(ReviewNode node) {
        return newNodeAdapter(node,
                new ViewerChildList(node, mConverter.newConverterNodes(mAuthorsRepository), this));
    }

    ReviewViewAdapter<GvNode> newFeedAdapter(ReviewNode node) {
        return newNodeAdapter(node,
                new ViewerFeed(node, mConverter.newConverterNodes(mAuthorsRepository), this));
    }

    ReviewViewAdapter<GvSocialPlatform> newPublishAdapter(GvSocialPlatformList platforms,
                                                          ReviewViewAdapter<?> reviewAdapter) {
        return new PublishScreenAdapter(platforms, reviewAdapter);
    }

    //adapter shows child nodes
    private ReviewViewAdapter<?> newReviewsListAdapter(ReviewNode node, @Nullable AuthorId
            followAuthorId) {
        //Doing it this way ensures proper actions
        return mReviewViewFactory.newReviewsListView(node, followAuthorId).getAdapter();
    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                         String subject,
                                                                         GridDataWrapper<T>
                                                                                 viewer) {
        ReviewTreeSourceCallback node = newAsyncNode();
        mReviewSource.getMetaReview(data, subject, node);
        return newNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewViewAdapter<?> newAggregatedMetaReviewAdapter
            (GvCanonicalCollection<T> data, String subject, GridDataWrapper<GvCanonical> viewer) {

        ReviewTreeSourceCallback node = newFlattenedMetaReview(data, subject);
        return data.size() == 1 ? newReviewsListAdapter(node, null) : newNodeAdapter(node, viewer);
    }

    private <T extends GvData> ReviewTreeSourceCallback newFlattenedMetaReview
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> gvDataType = data.getGvDataType();
        GvDataListImpl<T> allData = new GvDataListImpl<>(gvDataType, data.getGvReviewId());
        //TODO make type safe
        for (GvCanonical<T> canonical : data) {
            allData.addAll(canonical.toList());
        }

        ReviewTreeSourceCallback asyncNode = newAsyncNode();
        mReviewSource.getMetaReview(allData, subject, asyncNode);

        return asyncNode;
    }

    @Nullable
    private <T extends GvData> ReviewViewAdapter<T>
    newNodeAdapter(ReviewNode node, @Nullable GridDataWrapper<T> viewer) {
        return viewer != null ?
                new AdapterReviewNode<>(node, mConverter.newConverterImages(), viewer) : null;
    }

    @NonNull
    private ReviewTreeSourceCallback newAsyncNode() {
        Review fetching = mReviewsFactory.createUserReview(Strings.FETCHING, 0f,
                new ArrayList<DataCriterion>(), new ArrayList<DataComment>(),
                new ArrayList<DataImage>(), new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), true);

        ReviewReference reference = mReviewsFactory.asReference(fetching, mReviewSource.getTagsManager());
        ReviewNodeComponent node = mReviewsFactory.createLeafNode(reference);

        return new ReviewTreeSourceCallback(node);
    }
}
