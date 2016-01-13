package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractedTableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDb {
    private FactoryReviewerDbTableRow mRowfactory;

    public FactoryReviewerDb(FactoryReviewerDbTableRow rowfactory) {
        mRowfactory = rowfactory;
    }

    public ReviewerDb newDatabase(ContractedTableTransactor<ReviewerDbContract> transactor,
                                                      ReviewLoader reviewLoader,
                                                      TagsManager tagsManager,
                                                      DataValidator dataValidator) {
        return new ReviewerDbImpl(transactor, reviewLoader, mRowfactory, tagsManager, dataValidator);
    }
}
