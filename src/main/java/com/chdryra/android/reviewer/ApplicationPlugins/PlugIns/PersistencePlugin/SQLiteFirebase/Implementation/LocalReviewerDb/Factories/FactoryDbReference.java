/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewInfo;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbReference {
    private FactoryReviews mReviewsFactory;
    private FactoryBinders mBindersFactory;

    public FactoryDbReference(FactoryReviews reviewsFactory, FactoryBinders bindersFactory) {
        mReviewsFactory = reviewsFactory;
        mBindersFactory = bindersFactory;
    }

    public ReviewReference newReference(RowReview review, ReviewerDbRepository repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review, review, new DatumAuthorId(id, review.getAuthorId()), review);
        return new ReviewerDbReference(info, repo, mReviewsFactory, mBindersFactory.newReferenceBindersManager());
    }

    public ReviewReference newReference(Review review, ReviewerDbRepository repo) {
        ReviewId id = review.getReviewId();
        ReviewInfo info = new ReviewInfo(id, review.getSubject(), review.getRating(), review.getAuthorId(), review.getPublishDate());
        return new ReviewerDbReference(info, repo, mReviewsFactory, mBindersFactory.newReferenceBindersManager());
    }
}
