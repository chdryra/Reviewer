package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryContractor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.ReviewerDbContract;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceReviewerDb implements PersistencePlugin {
    private FactoryContractor mContractorFactory;
    private DbSpecification<ReviewerDbContract> mSpec;
    private FactoryReviewerDb mDbFactory;
    private FactoryReviewLoader mLoaderFactory;

    public PersistenceReviewerDb(String name, int version, DatabasePlugin dbPlugin) {
        mLoaderFactory  = new FactoryReviewLoader();
        mDbFactory = new FactoryReviewerDb(new FactoryReviewerDbTableRow());
        mSpec = newDbSpecification(dbPlugin, name, version);
        mContractorFactory = dbPlugin.getContractorFactory();
    }

    @Override
    public ReviewsRepositoryMutable newPersistenceRepository(Context context, ModelContext model) {
        FactoryReviewFromDataHolder reviewFactory = model.getReviewsFactory();
        DataValidator dataValidator = model.getDataValidator();
        TagsManager tagsManager = model.getTagsManager();

        ContractorDb<ReviewerDbContract> contractor = mContractorFactory.newContractor(context, mSpec);
        ReviewLoader loader = mLoaderFactory.newStaticLoader(reviewFactory, dataValidator);
        ReviewerDb db = mDbFactory.newDatabase(contractor, loader, tagsManager, dataValidator);

        return new ReviewerDbRepository(db);
    }

    private DbSpecification<ReviewerDbContract> newDbSpecification(DatabasePlugin plugin,
                                                                   String name, int version) {
        FactoryDbColumnDef columnFactory = new FactoryDbColumnDef();
        FactoryForeignKeyConstraint constraintFactory = new FactoryForeignKeyConstraint();
        FactoryReviewerDbContract factory = new FactoryReviewerDbContract(columnFactory, constraintFactory);
        ReviewerDbContract contract = factory.newContract();

        String dbName = name + "." + plugin.getDbNameExtension();
        return new FactoryDbSpecification().newSpecification(contract, dbName, version);
    }
}
