package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableItems;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableItems;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.TreeMethods.VisitorReviewDataGetter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSource implements ReviewsFeed {
    private ReviewsRepository mRepository;
    private FactoryReviews mReviewFactory;
    private FactoryReviewPublisher mPublisherFactory;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryReviewTreeTraverser mTraverserFactory;

    //Constructors
    public ReviewsSource(ReviewsRepository repository,
                         FactoryReviewPublisher publisherFactory,
                         FactoryReviews reviewFactory,
                         FactoryVisitorReviewNode visitorFactory,
                         FactoryReviewTreeTraverser traverserFactory) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
        mPublisherFactory = publisherFactory;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    @Override
    public DataAuthor getAuthor() {
        return mPublisherFactory.getAuthor();
    }

    @Override
    public ItemTagCollection getTags(ReviewId reviewId) {
        return getTagsManager().getTags(reviewId.toString());
    }

    @Override
    public Review getReview(VerboseDataReview datum) {
        ReviewId id = datum.getReviewId();
        Review review = getReview(id);
        if (review == null && datum.hasElements()) {
            review = getMetaReview((VerboseIdableItems<? extends VerboseDataReview>) datum,
                    datum.getStringSummary());
        }

        return review;
    }

    @Override
    public ReviewNode getMetaReview(VerboseIdableItems data, String subject) {
        IdableItems<Review> reviews = new MdDataList<>(null);
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i).getReviewId()));
        }

        return mReviewFactory.createMetaReview(reviews, subject);
    }

    @Override
    public ReviewNode asMetaReview(VerboseDataReview datum, String subject) {
        if (datum.isVerboseCollection()) {
            return getMetaReview((VerboseIdableItems<? extends VerboseDataReview>) datum,
                    subject);
        }

        return mReviewFactory.createMetaReview(getReview(datum));
    }

    @Override
    public ReviewNode getFlattenedMetaReview(VerboseIdableItems data, String subject) {
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(getMetaReview(data, subject));
        VisitorReviewDataGetter<Review> getter = mVisitorFactory.newReviewsCollector();
        traverser.addVisitor(getter);
        traverser.traverse();
        IdableItems<Review> flattened = getter.getData();

        return mReviewFactory.createMetaReview(flattened, subject);
    }

    //Overridden
    @Override
    public Review getReview(ReviewId reviewId) {
        return mRepository.getReview(reviewId);
    }

    @Override
    public Iterable<Review> getReviews() {
        return mRepository.getReviews();
    }

    @Override
    public TagsManager getTagsManager() {
        return mRepository.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mRepository.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mRepository.unregisterObserver(observer);
    }
}
