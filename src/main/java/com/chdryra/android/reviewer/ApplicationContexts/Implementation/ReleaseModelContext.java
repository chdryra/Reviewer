package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewLoader;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.ContractedTableTransactor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.PersistencePlugin;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Model.Factories.FactorySocialPlatformList;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryTreeFlattener;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {

    public ReleaseModelContext(Context context,
                               DataAuthor author,
                               String repositoryName,
                               int repositoryVersion,
                               PersistencePlugin persistencePlugin) {
        setReviewsFactory(author);

        setTagsManager(new FactoryTagsManager().newTagsManager());

        setDataValidator(new DataValidator());

        setVisitorsFactory(new FactoryVisitorReviewNode());

        setTreeTraversersFactory(new FactoryNodeTraverser());

        setSocialPlatforms(new FactorySocialPlatformList().newList(context));

        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();

        ReviewsRepositoryMutable persistence = getPersistentRepository(context,
                repositoryName,
                repositoryVersion,
                persistencePlugin,
                getReviewsFactory(),
                getTagsManager(),
                getDataValidator(),
                repoFactory
        );

        TreeFlattener flattener = getTreeFlattener(getVisitorsFactory(), getNodeTraversersFactory
                ());
        setAuthorsFeed(persistence, getReviewsFactory(), flattener);

        setReviewsSource(repoFactory.newReviewsSource(persistence, getReviewsFactory(), flattener));
    }

    private ReviewsRepositoryMutable getPersistentRepository(Context context,
                                                             String repositoryName,
                                                             int repositoryVersion,
                                                             PersistencePlugin persistence,
                                                             FactoryReviews reviewFactory,
                                                             TagsManager tagsManager,
                                                             DataValidator dataValidator,
                                                             FactoryReviewsRepository repoFactory) {
        ReviewerDb db = newDatabase(context, repositoryName, repositoryVersion, persistence,
                reviewFactory, tagsManager, dataValidator);
        return repoFactory.newDatabaseRepository(db);
    }

    private TreeFlattener getTreeFlattener(FactoryVisitorReviewNode visitorFactory,
                                           FactoryNodeTraverser traverserFactory) {
        FactoryTreeFlattener flattenerFactory = new FactoryTreeFlattener(visitorFactory,
                traverserFactory);
        return flattenerFactory.newFlattener();
    }

    private ReviewerDb newDatabase(Context context,
                                   String persistenceName,
                                   int persistenceVersion,
                                   PersistencePlugin persistence,
                                   FactoryReviews reviewFactory,
                                   TagsManager tagsManager,
                                   DataValidator dataValidator) {
        RowValueTypeDefinitions typeDefinitions = persistence.getTypeDefinitions();
        ReviewerDbContract contract = getReviewerDbContract(typeDefinitions);
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        ContractedTableTransactor<ReviewerDbContract> dbTransactor
                = getTableTransactor(context, persistenceName, persistenceVersion, persistence,
                contract, rowFactory);

        FactoryReviewLoader loaderFactory = new FactoryReviewLoader();
        ReviewLoader loader = loaderFactory.newStaticLoader(reviewFactory, dataValidator);
        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        return dbFactory.newDatabase(dbTransactor, loader, tagsManager, dataValidator);
    }

    private ReviewerDbContract getReviewerDbContract(RowValueTypeDefinitions typeDefinitions) {
        FactoryDbColumnDef columnFactory = new FactoryDbColumnDef();
        FactoryForeignKeyConstraint constraintFactory = new FactoryForeignKeyConstraint();
        FactoryReviewerDbContract factory = new FactoryReviewerDbContract(columnFactory, constraintFactory);
        return factory.newContract(typeDefinitions);
    }

    private void setAuthorsFeed(ReviewsRepositoryMutable sourceAndDestination,
                                FactoryReviews reviewsFactory,
                                TreeFlattener flattener) {
        FactoryReviewsFeed feedFactory = new FactoryReviewsFeed(reviewsFactory, flattener);
        ReviewsFeedMutable reviewsFeed = feedFactory.newMutableFeed(sourceAndDestination);

        setAuthorsFeed(reviewsFeed);
    }

    private void setReviewsFactory(DataAuthor author) {
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        setFactoryReviews(new FactoryReviews(publisherFactory, new FactoryReviewNode(), converter));
    }

    private ContractedTableTransactor<ReviewerDbContract> getTableTransactor(Context context,
                                                                             String databaseName,
                                                                             int version,
                                                                             PersistencePlugin persistence,
                                                                             ReviewerDbContract contract,
                                                                             FactoryDbTableRow rowFactory) {
        FactoryDbSpecification factoryDbSpecification = new FactoryDbSpecification();
        DbSpecification<ReviewerDbContract> spec = factoryDbSpecification.newSpecification(contract, databaseName, version);
        return persistence.newTableTransactor(context, spec, rowFactory);
    }
}
