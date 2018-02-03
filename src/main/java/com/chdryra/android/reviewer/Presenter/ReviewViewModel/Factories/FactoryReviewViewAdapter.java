/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Algorithms.DataSorting.RatingBucketer;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Factories.FactoryDataBucketer;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataCollection;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishScreenAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonical;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCanonicalCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataAggregator;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBucket;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterCommentsAggregate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AdapterNodeFollowable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.AdapterReviewNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .AuthorSearchAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapper;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewTreeFlat;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ViewerAuthors;

import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewViewAdapter {
    private final FactoryReviews mReviewsFactory;
    private final FactoryReviewsRepo mReposFactory;
    private final FactoryGridDataViewer mViewerFactory;
    private final FactoryDataBucketer mBucketerFactory;
    private final GvDataAggregator mAggregator;
    private final AuthorsRepo mAuthorsRepo;
    private final NodeRepo mReviewSource;
    private final ConverterGv mConverter;

    public FactoryReviewViewAdapter(FactoryReviews reviewsFactory,
                                    FactoryReferences referenceFactory,
                                    FactoryReviewsRepo reposFactory,
                                    FactoryDataBucketer bucketerFactory,
                                    GvDataAggregator aggregator,
                                    AuthorsRepo authorsRepo,
                                    NodeRepo reviewSource,
                                    ConverterGv converter) {
        mReviewsFactory = reviewsFactory;
        mReposFactory = reposFactory;
        mViewerFactory = new FactoryGridDataViewer(this, referenceFactory, authorsRepo,
                converter);
        mBucketerFactory = bucketerFactory;
        mAggregator = aggregator;
        mConverter = converter;
        mAuthorsRepo = authorsRepo;
        mReviewSource = reviewSource;
    }

    //List reviews generating this datum
    public <T extends GvDataRef> ReviewViewAdapter<?> newReviewsListAdapter(T datum,
                                                                            @Nullable String title,
                                                                            @Nullable AuthorId
                                                                                    toFollow) {
        HasReviewId dataValue = datum.getDataValue();
        if (title == null) title = dataValue != null ? dataValue.toString() : datum.toString();
        return newReviewsListAdapter(mReviewSource.asMetaReview(datum, title), toFollow);
    }

    //List reviews generating this data
    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(IdableCollection<T> data) {
        return newReviewsListAdapter(data, data.toString());
    }

    public <T extends GvData> ReviewViewAdapter<?> newReviewsListAdapter(IdableCollection<T>
                                                                                 data, String
            title) {
        return newReviewsListAdapter(mReviewSource.getMetaReview(data, title), null);
    }

    //List all reviews in the tree
    public ReviewViewAdapter<?> newFlattenedReviewsListAdapter(ReviewNode toFlatten) {
        return newReviewsListAdapter(new ReviewTreeFlat(toFlatten, mReviewsFactory), null);
    }

    //Summary of reviews for these authors
    public ReviewViewAdapter<?> newFeedSummaryAdapter(AuthorId summaryOwner,
                                                      Set<AuthorId> reviewAuthors,
                                                      String title) {
        RepoCollection<AuthorId> collection = mReposFactory.newRepoCollection();
        for (AuthorId author : reviewAuthors) {
            collection.add(author, mReviewSource.getReviewsByAuthor(author));
        }

        ReviewNode node = mReviewsFactory.createTree(collection, mAuthorsRepo.getReference
                (summaryOwner), title);

        return newNodeAdapter(node, mViewerFactory.newTreeSummaryViewer(node));
    }

    //Summary of review data sizes for this review tree
    public ReviewViewAdapter<?> newSummaryAdapter(ReviewNode node) {
        GridDataWrapper<?> viewer = node.getChildren().size() > 0 ?
                mViewerFactory.newTreeSummaryViewer(node) :
                mViewerFactory.newNodeSummaryViewer(node);
        return newNodeAdapter(node, viewer);
    }

    //View specific data for this review as a review (can't be expanded to a review list)
    public <T extends GvData> ReviewViewAdapter<?> newReviewDataAdapter(ReviewNode node,
                                                                        GvDataType<T> dataType) {
        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node,
                    getProfileImage(node),
                    mConverter.newConverterImages(), mViewerFactory
                    .newReviewCommentsViewer(node));
        } else {
            return newNodeAdapter(node,
                    mViewerFactory.newReviewDataViewer(node, dataType));
        }
    }

    //View specific data for this review as a meta-review (can be expanded to a review list)
    public ReviewViewAdapter<?> newTreeDataAdapter(ReviewNode node, GvDataType<?> dataType) {
        if (dataType == GvComment.TYPE) {
            return new AdapterComments(node,
                    getProfileImage(node),
                    mConverter.newConverterImages(), mViewerFactory
                    .newTreeCommentsViewer(node));
        } else if (dataType == GvAuthorId.TYPE) {
            return newNodeAdapter(node, mViewerFactory.newTreeAuthorsViewer(node));
        } else {
            return newNodeAdapter(node, mViewerFactory.newTreeDataViewer(node, dataType));
        }
    }

    //Old aggregate stuff
    public <T extends GvData> ReviewViewAdapter<?> newFlattenedReviewsListAdapter
    (GvCanonicalCollection<T> data) {
        return newReviewsListAdapter(newFlattenedMetaReview(data, data.toString()), null);
    }

    public <T extends GvData> ReviewViewAdapter<?> newAggregateToReviewsAdapter
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> type = data.getGvDataType();

        if (type.equals(GvComment.TYPE)) {
            ReviewNode node = newFlattenedMetaReview(data, subject);
            //TODO make type safe
            return new AdapterCommentsAggregate(node,
                    getProfileImage(node),
                    mConverter.newConverterImages(),
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

    ReviewViewAdapter<?> newTreeAdapter(ReviewNode node) {
        return newNodeAdapter(node, mViewerFactory.newTreeSummaryViewer(node));
    }

    //View for search screen
    ReviewViewAdapter.Filterable<GvAuthor> newFollowSearchAdapter() {
        return new AuthorSearchAdapter(new ViewerAuthors(new GvAuthorList()),
                mAuthorsRepo, mConverter.newConverterAuthors());
    }

    //View for distribution screen
    ReviewViewAdapter<?>
    newBucketAdapter(ReviewNode node,
                     ViewHolderFactory<VhBucket<Float, DataRating>> vhFactory) {
        RatingBucketer bucketer = mBucketerFactory.newRatingsBucketer();
        return newNodeAdapter(node, mViewerFactory.newBucketViewer(node, vhFactory, bucketer));
    }

    ReviewViewAdapter<GvNode> newFeedAdapter(ReviewNode node) {
        return newNodeAdapter(node, mViewerFactory.newFeedViewer(node));
    }

    ReviewViewAdapter<GvSocialPlatform> newPublishAdapter(GvSocialPlatformList platforms,
                                                          ReviewViewAdapter<?> reviewAdapter) {
        return new PublishScreenAdapter(platforms, reviewAdapter);
    }

    //adapter shows child nodes
    ReviewViewAdapter<GvNode> newReviewsListAdapter(ReviewNode node,
                                                    @Nullable AuthorId followAuthorId) {
        return new AdapterNodeFollowable(node,
                getProfileImage(node),
                mConverter.newConverterImages(),
                mViewerFactory.newChildViewer(node), followAuthorId);
    }

    private <T extends GvData> ReviewViewAdapter<T> newMetaReviewAdapter(GvDataCollection<T> data,
                                                                         String subject,
                                                                         GridDataWrapper<T>
                                                                                 viewer) {
        return newNodeAdapter(mReviewSource.getMetaReview(data, subject), viewer);
    }

    private <T extends GvData> ReviewNode newFlattenedMetaReview
            (GvCanonicalCollection<T> data, String subject) {
        GvDataType<T> gvDataType = data.getGvDataType();
        GvDataListImpl<T> allData = new GvDataListImpl<>(gvDataType, data.getGvReviewId());
        //TODO make type safe
        for (GvCanonical<T> canonical : data) {
            allData.addAll(canonical.toList());
        }

        return mReviewSource.getMetaReview(allData, subject);
    }

    @Nullable
    private <T extends GvData> ReviewViewAdapter<T>
    newNodeAdapter(ReviewNode node, @Nullable GridDataWrapper<T> viewer) {
        return viewer != null ?
                new AdapterReviewNode<>(node,
                        getProfileImage(node),
                        mConverter.newConverterImages(), viewer) : null;
    }

    private DataReference<ProfileImage> getProfileImage(ReviewNode node) {
        return mAuthorsRepo.getProfile(node.getAuthorId()).getProfileImage();
    }

    private <T extends GvData> ReviewViewAdapter<?> newAggregatedMetaReviewAdapter
            (GvCanonicalCollection<T> data, String subject, GridDataWrapper<GvCanonical> viewer) {

        ReviewNode node = newFlattenedMetaReview(data, subject);
        return data.size() == 1 ? newReviewsListAdapter(node, null) : newNodeAdapter(node, viewer);
    }
}
