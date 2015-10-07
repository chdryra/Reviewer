package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.Model.UserData.Author;
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
    private Author mAuthor;

    //Constructors
    public ReviewsRepository(ReviewsProvider provider, Author author) {
        mProvider = provider;
        mAuthor = author;
    }

    public TagsManager.ReviewTagCollection getTags(ReviewId id) {
        return getTagsManager().getTags(id);
    }

    public Review getReview(GvData datum) {
        GvReviewId id = datum.getReviewId();
        Review review = getReview(ReviewId.fromString(id.getId()));
        if (review == null && datum.hasElements()) {
            review = createMetaReview((GvDataCollection<? extends GvData>) datum,
                    datum.getStringSummary());
        }

        return review;
    }

    public <T extends GvData> ReviewNode createMetaReview(GvDataCollection<T> data,
                                                          String subject) {
        IdableList<Review> reviews = new IdableList<>();
        for (int i = 0; i < data.size(); ++i) {
            reviews.add(getReview(data.getItem(i)));
        }

        ReviewPublisher publisher = new ReviewPublisher(mAuthor, PublishDate.now());
        return FactoryReview.createMetaReview(reviews, publisher, subject);
    }

    public <T extends GvData> ReviewNode createMetaReview(GvData datum,
                                                          String subject) {
        if (datum.isCollection()) {
            return createMetaReview((GvDataCollection<? extends GvData>) datum, subject);
        }

        return FactoryReview.createMetaReview(getReview(datum));
    }

    public <T extends GvData> ReviewNode createFlattenedMetaReview(GvDataCollection<T> data,
                                                                   String subject) {
        ReviewNode meta = createMetaReview(data, subject);
        IdableList<Review> flattened = VisitorReviewsGetter.flatten(meta);

        ReviewPublisher publisher = new ReviewPublisher(mAuthor, PublishDate.now());
        return FactoryReview.createMetaReview(flattened, publisher, subject);
    }

    //Overridden
    @Override
    public Review getReview(ReviewId id) {
        return mProvider.getReview(id);
    }

    @Override
    public IdableList<Review> getReviews() {
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
