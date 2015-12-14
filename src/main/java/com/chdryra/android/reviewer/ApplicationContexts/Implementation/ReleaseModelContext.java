package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Database.Factories.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewLoader;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.Database.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbContractExecutor;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbHelper;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Model.Factories.FactorySocialPlatformList;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryTreeFlattener;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Implementation.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {

    public ReleaseModelContext(Context context, DataAuthor author, String databaseName,
                               int databaseVersion) {
        setReviewsFactory(author);

        setTagsManager(new FactoryTagsManager().newTagsManager());

        setDataValidator(new DataValidator());

        setVisitorsFactory(new FactoryVisitorReviewNode());

        setTreeTraversersFactory(new FactoryReviewTreeTraverser());

        setSocialPlatforms(context);

        TreeFlattener flattener = getTreeFlattener(getVisitorsFactory(), getTreeTraversersFactory());
        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();
        ReviewsRepositoryMutable persistence = getPersistentRepository(context, databaseName,
                databaseVersion,
                getReviewsFactory(),
                getTagsManager(),
                getDataValidator(),
                repoFactory);

        setAuthorsFeed(persistence, getReviewsFactory(), flattener);

        setReviewsSource(repoFactory.newReviewsSource(persistence, getReviewsFactory(), flattener));
    }

    private ReviewsRepositoryMutable getPersistentRepository(Context context,
                                                             String databaseName,
                                                             int databaseVersion,
                                                             FactoryReviews reviewFactory,
                                                             TagsManager tagsManager,
                                                             DataValidator dataValidator,
                                                             FactoryReviewsRepository repoFactory) {
        ReviewerDb db = newDatabase(context, databaseName, databaseVersion, reviewFactory,
                tagsManager, dataValidator);
        return repoFactory.newDatabaseRepository(db);
    }

    private TreeFlattener getTreeFlattener(FactoryVisitorReviewNode visitorFactory, FactoryReviewTreeTraverser traverserFactory) {
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

    private ReviewerDb newReviewerDb(Context context, String databaseName, int version,
                                     ReviewLoader loader,
                                     DataValidator validator,
                                     TagsManager tagsManager) {
        FactoryDbColumnDef columnFactory = new FactoryDbColumnDef();
        FactoryForeignKeyConstraint constraintFactory = new FactoryForeignKeyConstraint();
        FactoryReviewerDbContract factoryReviewerDbContract = new FactoryReviewerDbContract
                (columnFactory, constraintFactory);
        ReviewerDbContract contract = factoryReviewerDbContract.newContract();
        DbSpecification<ReviewerDbContract> spec
                = new FactoryDbSpecification().newSpecification(databaseName, contract, version);
        FactoryDbContractExecutor executorFactory = new FactoryDbContractExecutor();
        DbHelper<ReviewerDbContract> dbHelper
                = new DbHelper<>(context, spec, executorFactory.newExecutor());

        FactoryDbTableRow rowFactory = new FactoryDbTableRow();

        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        return dbFactory.newDatabase(dbHelper, loader, tagsManager, validator);
    }

    private void setSocialPlatforms(Context context) {
        FactorySocialPlatformList factory = new FactorySocialPlatformList();

        setSocialPlatforms(factory.newList(context));
    }
}
