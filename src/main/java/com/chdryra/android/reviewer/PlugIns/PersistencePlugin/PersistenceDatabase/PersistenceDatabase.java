package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractedTableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceDatabase implements PersistencePlugin {
    private String mName;
    private int mVersion;
    private DatabasePlugin mPlugin;

    public PersistenceDatabase(String name, int version, DatabasePlugin plugin) {
        mName = name;
        mVersion = version;
        mPlugin = plugin;
    }

    @Override
    public ReviewsRepositoryMutable newPersistenceRepository(Context context, ModelContext modelContext) {
        ReviewerDb db = newDatabase(context, modelContext.getReviewsFactory(),
                modelContext.getTagsManager(), modelContext.getDataValidator());
        return new ReviewerDbRepository(db);
    }

    private ReviewerDb newDatabase(Context context,
                                   FactoryReviews reviewFactory,
                                   TagsManager tagsManager,
                                   DataValidator dataValidator) {
        RowValueTypeDefinitions typeDefinitions = mPlugin.getTypeDefinitions();
        ReviewerDbContract contract = getReviewerDbContract(typeDefinitions);
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        String name = mName + "." + mPlugin.getDatabaseExtension();

        ContractedTableTransactor<ReviewerDbContract> dbTransactor
                = getTableTransactor(context, name, mVersion, mPlugin,
                contract, rowFactory);

        FactoryReviewLoader loaderFactory = new FactoryReviewLoader();
        ReviewLoader loader = loaderFactory.newStaticLoader(reviewFactory, dataValidator);
        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        return dbFactory.newDatabase(dbTransactor, loader, tagsManager, dataValidator);
    }

    private ContractedTableTransactor<ReviewerDbContract> getTableTransactor(Context context,
                                                                             String databaseName,
                                                                             int version,
                                                                             DatabasePlugin persistence,
                                                                             ReviewerDbContract contract,
                                                                             FactoryDbTableRow rowFactory) {
        FactoryDbSpecification factoryDbSpecification = new FactoryDbSpecification();
        DbSpecification<ReviewerDbContract> spec = factoryDbSpecification.newSpecification(contract, databaseName, version);
        return persistence.newTableTransactor(context, spec, rowFactory);
    }

    private ReviewerDbContract getReviewerDbContract(RowValueTypeDefinitions typeDefinitions) {
        FactoryDbColumnDef columnFactory = new FactoryDbColumnDef();
        FactoryForeignKeyConstraint constraintFactory = new FactoryForeignKeyConstraint();
        FactoryReviewerDbContract factory = new FactoryReviewerDbContract(columnFactory, constraintFactory);
        return factory.newContract(typeDefinitions);
    }
}
