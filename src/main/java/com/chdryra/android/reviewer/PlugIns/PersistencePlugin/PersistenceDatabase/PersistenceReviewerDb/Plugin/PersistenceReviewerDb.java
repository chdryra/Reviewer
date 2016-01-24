package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Plugin;



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
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.FactoryReviewFromDataHolder;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PersistenceReviewerDb implements PersistencePlugin {
    private FactoryContractor mContractorFactory;
    private DbSpecification<ReviewerDbContract> mSpec;
    private FactoryReviewerDb mDbFactory;
    private FactoryReviewTransactor mReviewTransactor;

    public PersistenceReviewerDb(String name, int version, DatabasePlugin dbPlugin) {
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        mReviewTransactor = new FactoryReviewTransactor(rowFactory);
        mDbFactory = new FactoryReviewerDb(rowFactory);
        mSpec = newDbSpecification(dbPlugin, name, version);
        mContractorFactory = dbPlugin.getContractorFactory();
    }

    @Override
    public ReviewsRepositoryMutable newPersistenceRepository(Context context, ModelContext model) {
        FactoryReviewFromDataHolder reviewFactory = model.getReviewsFactory();
        DataValidator dataValidator = model.getDataValidator();
        TagsManager tagsManager = model.getTagsManager();

        ContractorDb<ReviewerDbContract> contractor = mContractorFactory.newContractor(context,
                mSpec);
        ReviewTransactor transactor = mReviewTransactor.newStaticLoader(reviewFactory,
                dataValidator);
        ReviewerDb db = mDbFactory.newDatabase(contractor, transactor, tagsManager, dataValidator);

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
