package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceAuthored extends ReviewsSourceImpl implements ReviewsFeed {
    private DataAuthor mAuthor;

    public ReviewsSourceAuthored(ReviewsRepository repository,
                                 FactoryReviews reviewFactory,
                                 TreeFlattener flattener) {
        super(repository, reviewFactory, flattener);
        mAuthor = reviewFactory.getAuthor();
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }
}
