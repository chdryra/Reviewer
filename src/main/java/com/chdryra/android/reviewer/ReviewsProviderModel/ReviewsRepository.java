package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.PublishDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.ReviewPublisher;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepository implements ReviewsProvider {
    private ReviewsProvider mProvider;
    private FactoryReview mReviewFactory;
    private Author mAuthor;

    //Constructors
    public ReviewsRepository(ReviewsProvider provider, FactoryReview reviewFactory, Author author) {
        mProvider = provider;
        mReviewFactory = reviewFactory;
        mAuthor = author;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public ItemTagCollection getTags(MdReviewId id) {
        return getTagsManager().getTags(id);
    }

    public Review getReview(GvData datum) {
        GvReviewId id = datum.getReviewId();
        Review review = getReview(MdReviewId.fromString(id.getId()));
        if (review == null && datum.hasElements()) {
            review = createMetaReview((GvDataCollection<? extends GvData>) datum,
                    datum.getStringSummary());
        }

        return review;
    }

    public <T extends GvData> ReviewNode createMetaReview(GvDataCollection<T> data,
                                                          String subject) {
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i)));
        }

        ReviewPublisher publisher = new ReviewPublisher(mAuthor, PublishDate.now());
        return mReviewFactory.createMetaReview(reviews, publisher, subject);
    }

    public ReviewNode createMetaReview(GvData datum, String subject) {
        if (datum.isCollection()) {
            return createMetaReview((GvDataCollection<? extends GvData>) datum, subject);
        }

        return mReviewFactory.createMetaReview(getReview(datum));
    }

    public <T extends GvData> ReviewNode createFlattenedMetaReview(GvDataCollection<T> data,
                                                                   String subject) {
        ReviewNode meta = createMetaReview(data, subject);
        MdIdableCollection<Review> flattened = VisitorReviewsGetter.flatten(meta);

        ReviewPublisher publisher = new ReviewPublisher(mAuthor, PublishDate.now());
        return mReviewFactory.createMetaReview(flattened, publisher, subject);
    }

    //Overridden
    @Override
    public Review getReview(MdReviewId id) {
        return mProvider.getReview(id);
    }

    @Override
    public MdIdableCollection<Review> getReviews() {
        return mProvider.getReviews();
    }

    public TagsManager getTagsManager() {
        return mProvider.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsProviderObserver observer) {
        mProvider.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsProviderObserver observer) {
        mProvider.unregisterObserver(observer);
    }
}
