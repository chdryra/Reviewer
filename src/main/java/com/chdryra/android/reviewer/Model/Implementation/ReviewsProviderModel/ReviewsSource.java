package com.chdryra.android.reviewer.Model.Implementation.ReviewsProviderModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewPublisher;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.Interfaces.VisitorReviewDataGetter;

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
    public ItemTagCollection getTags(String reviewId) {
        return getTagsManager().getTags(reviewId);
    }

    @Override
    public Review getReview(VerboseDataReview datum) {
        String id = datum.getReviewId();
        Review review = getReview(id);
        if (review == null && datum.hasElements()) {
            review = createMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    datum.getStringSummary());
        }

        return review;
    }

    @Override
    public Review createMetaReview(VerboseIdableCollection data, String subject) {
        IdableCollection<Review> reviews = new MdDataList<>(null);
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i).getReviewId()));
        }

        return mReviewFactory.createMetaReview(reviews, subject);
    }

    @Override
    public Review asMetaReview(VerboseDataReview datum, String subject) {
        if (datum.isVerboseCollection()) {
            return createMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subject);
        }

        return mReviewFactory.createMetaReview(getReview(datum));
    }

    @Override
    public Review createFlattenedMetaReview(VerboseIdableCollection data, String subject) {
        Review meta = createMetaReview(data, subject);
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(meta.getTreeRepresentation());
        VisitorReviewDataGetter<Review> getter = mVisitorFactory.newReviewsCollector();
        traverser.addVisitor(getter);
        traverser.traverse();
        IdableList<Review> flattened = getter.getData();

        return mReviewFactory.createMetaReview(flattened, subject);
    }

    //Overridden
    @Override
    public Review getReview(String reviewId) {
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
