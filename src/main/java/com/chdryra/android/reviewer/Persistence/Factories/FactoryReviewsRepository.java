/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.NullReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsRepositoryAuthored;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsRepositoryCached;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    private FactoryReviewsCache mCacheFactory;

    public FactoryReviewsRepository(FactoryReviewsCache cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    public ReviewsSource newReviewsSource(ReviewsRepository repository,
                                          FactoryReviews reviewsFactory) {
        return new ReviewsSourceImpl(repository, reviewsFactory, this);
    }

    public ReviewsRepository newCachedRepo(ReviewsRepository archive, ReviewsCache cache) {
        return new ReviewsRepositoryCached<>(cache, archive, this);
    }

    public ReviewsRepository newAuthoredRepo(DataAuthor author, ReviewsRepository mainRepo) {
        return new ReviewsRepositoryAuthored(author, mainRepo, this);
    }

    public ReviewsRepository newEmptyRepository(TagsManager manager) {
        return new NullReviewsRepository(manager);
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }
}
