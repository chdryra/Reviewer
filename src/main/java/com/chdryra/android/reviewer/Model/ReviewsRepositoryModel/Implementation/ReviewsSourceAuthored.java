/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceAuthored extends ReviewsSourceImpl implements ReviewsFeed {
    private DataAuthor mAuthor;

    public ReviewsSourceAuthored(ReviewsRepository repository,
                                 FactoryReviews reviewFactory) {
        super(repository, reviewFactory);
        mAuthor = reviewFactory.getAuthor();
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }
}
