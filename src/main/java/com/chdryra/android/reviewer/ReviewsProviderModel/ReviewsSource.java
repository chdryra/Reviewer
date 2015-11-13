package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding
        .FactoryReviewPublisher;
import com.chdryra.android.reviewer.Interfaces.Data.VerboseDataReview;
import com.chdryra.android.reviewer.Interfaces.Data.IdableCollection;
import com.chdryra.android.reviewer.Interfaces.Data.VerboseIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewsGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSource implements ReviewsProvider {
    private ReviewsRepository mRepository;
    private FactoryReview mReviewFactory;
    private FactoryReviewPublisher mPublisherFactory;

    //Constructors
    public ReviewsSource(ReviewsRepository repository,
                         FactoryReviewPublisher publisherFactory,
                         FactoryReview reviewFactory) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
        mPublisherFactory = publisherFactory;
    }

    @Override
    public Author getAuthor() {
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
        IdableCollection<Review> reviews = new MdIdableCollection<>();
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i)));
        }

        return mReviewFactory.createMetaReview(reviews, mPublisherFactory.newPublisher(), subject);
    }

    @Override
    public Review asMetaReview(VerboseDataReview datum, String subject) {
        if (datum.isVerboseCollection()) {
            return createMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subject);
        }

        return mReviewFactory.createMetaReview(mPublisherFactory.newPublisher(), getReview(datum));
    }

    @Override
    public Review createFlattenedMetaReview(VerboseIdableCollection data, String subject) {
        Review meta = createMetaReview(data, subject);
        IdableCollection<Review> flattened = VisitorReviewsGetter.flatten(meta.getTreeRepresentation());

        return mReviewFactory.createMetaReview(flattened, mPublisherFactory.newPublisher(), subject);
    }

    //Overridden
    @Override
    public Review getReview(String reviewId) {
        return mRepository.getReview(reviewId);
    }

    @Override
    public IdableCollection<Review> getReviews() {
        return mRepository.getReviews();
    }

    public TagsManager getTagsManager() {
        return mRepository.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsProviderObserver observer) {
        mRepository.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {
        mRepository.unregisterObserver(observer);
    }
}
