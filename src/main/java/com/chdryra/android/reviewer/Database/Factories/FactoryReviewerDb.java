package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbHelper;
import com.chdryra.android.reviewer.Database.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDb {
    public ReviewerDb newDatabase(DbHelper<ReviewerDbContract> dbHelper,
                                  ReviewLoader reviewLoader,
                                  FactoryDbTableRow rowFactory,
                                  TagsManager tagsManager,
                                  DataValidator dataValidator) {
        return new ReviewerDbImpl(dbHelper, reviewLoader, rowFactory, tagsManager, dataValidator);
    }
}
