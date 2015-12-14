package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Database.Implementation.ReviewerDbPersistence;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistence;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerPersistence {
    public ReviewerPersistence newDatabasePersistence(ReviewerDb db) {
        return new ReviewerDbPersistence(db);
    }
}
