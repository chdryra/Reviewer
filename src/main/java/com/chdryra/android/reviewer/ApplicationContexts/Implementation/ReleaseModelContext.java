package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Database.AndroidSqLite.DatabaseProviderSqlLite;
import com.chdryra.android.reviewer.Database.AndroidSqLite.FactorySqLiteDatabaseInstance;
import com.chdryra.android.reviewer.Database.AndroidSqLite.StorageTypeDefinitionsSqlLite;
import com.chdryra.android.reviewer.Database.AndroidSqLite.SqlLiteContractExecutorImpl;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewLoader;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DatabaseProvider;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
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
                               int repositoryVersion) {
        setReviewsFactory(author);

        setTagsManager(new FactoryTagsManager().newTagsManager());

        setDataValidator(new DataValidator());

        setVisitorsFactory(new FactoryVisitorReviewNode());

        setTreeTraversersFactory(new FactoryNodeTraverser());

        setSocialPlatforms(new FactorySocialPlatformList().newList(context));

        TreeFlattener flattener = getTreeFlattener(getVisitorsFactory(), getNodeTraversersFactory());
        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();

        ReviewsRepositoryMutable persistence = getPersistentRepository(context,
                repositoryName,
                repositoryVersion,
                getReviewsFactory(),
                getTagsManager(),
                getDataValidator(),
                repoFactory);

        setAuthorsFeed(persistence, getReviewsFactory(), flattener);

        setReviewsSource(repoFactory.newReviewsSource(persistence, getReviewsFactory(), flattener));
    }

    private ReviewsRepositoryMutable getPersistentRepository(Context context,
                                                             String repositoryName,
                                                             int repositoryVersion,
                                                             FactoryReviews reviewFactory,
                                                             TagsManager tagsManager,
                                                             DataValidator dataValidator,
                                                             FactoryReviewsRepository repoFactory) {
        ReviewerDb db = newDatabase(context, repositoryName, repositoryVersion, reviewFactory,
                tagsManager, dataValidator);
        return repoFactory.newDatabaseRepository(db);
    }

    private TreeFlattener getTreeFlattener(FactoryVisitorReviewNode visitorFactory, FactoryNodeTraverser traverserFactory) {
        FactoryTreeFlattener flattenerFactory = new FactoryTreeFlattener(visitorFactory, traverserFactory);
        return flattenerFactory.newFlattener();
    }

    private ReviewerDb newDatabase(Context context,
                                   String databaseName,
                                   int databaseVersion,
                                   FactoryReviews reviewFactory,
                                   TagsManager tagsManager,
                                   DataValidator dataValidator) {
        FactoryReviewLoader loaderFactory = new FactoryReviewLoader();
        ReviewLoader loader = loaderFactory.newStaticLoader(reviewFactory, dataValidator);
        return newReviewerDb(context, databaseName, databaseVersion, loader,
                dataValidator, tagsManager);
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
        FactoryMdConverter convertersFactory = new FactoryMdConverter();
        ConverterMd mdConverter = convertersFactory.newMdConverter();
        FactoryReviewNode factory = new FactoryReviewNode();
        setFactoryReviews(new FactoryReviews(publisherFactory, factory, mdConverter));
    }

    private ReviewerDb newReviewerDb(Context context,
                                     String databaseName,
                                     int version,
                                     ReviewLoader loader,
                                     DataValidator validator,
                                     TagsManager tagsManager) {
        FactoryDbColumnDef columnFactory = new FactoryDbColumnDef();
        FactoryForeignKeyConstraint constraintFactory = new FactoryForeignKeyConstraint();
        StorageTypeDefinitions typeFactory = getStorageTypeFactory();
        FactoryReviewerDbContract factoryReviewerDbContract = new FactoryReviewerDbContract
                (columnFactory, constraintFactory, typeFactory);
        ReviewerDbContract contract = factoryReviewerDbContract.newContract();
        DbSpecification<ReviewerDbContract> spec
                = new FactoryDbSpecification().newSpecification(databaseName, contract, version);
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();

        DatabaseProvider<ReviewerDbContract> dbProvider = newDatabaseProvider(context, spec,
                rowFactory);


        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        return dbFactory.newDatabase(dbProvider, loader, tagsManager, validator);
    }

    @NonNull
    private StorageTypeDefinitions getStorageTypeFactory() {
        return new StorageTypeDefinitionsSqlLite();
    }

    @NonNull
    private DatabaseProvider<ReviewerDbContract> newDatabaseProvider(Context context,
                                                                     DbSpecification<ReviewerDbContract> spec,
                                                                     FactoryReviewerDbTableRow rowFactory) {
        FactorySqLiteDatabaseInstance dbInstanceFactory = new FactorySqLiteDatabaseInstance(rowFactory);
        return new DatabaseProviderSqlLite<>(context, spec, new SqlLiteContractExecutorImpl(), dbInstanceFactory);
    }

}
