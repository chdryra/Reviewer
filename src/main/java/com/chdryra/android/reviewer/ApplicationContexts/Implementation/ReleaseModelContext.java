package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;
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
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseModelContext extends ModelContextBasic {

    public ReleaseModelContext(Context context, DataAuthor author, String databaseName,
                               int databaseVersion) {
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        setReviewsFactory(publisherFactory);

        setSocialPlatforms(context);

        setTagsManager();

        setDataValidator();

        setAuthorsFeed(context, databaseName, databaseVersion, publisherFactory,
                getReviewsFactory(), getTagsManager(), getDataValidator());

        setReviewsProvider(getAuthorsFeed());
    }

    private void setAuthorsFeed(Context context, String databaseName, int databaseVersion,
                                FactoryReviewPublisher publisherFactory,
                                FactoryReviews reviewsFactory,
                                TagsManager tagsManager,
                                DataValidator validator) {
        ReviewerDb db = makeReviewerDb(context, databaseName, databaseVersion, reviewsFactory,
                validator, tagsManager);
        FactoryVisitorReviewNode factoryVisitor = new FactoryVisitorReviewNode();
        setVisitorsFactory(factoryVisitor);
        FactoryReviewTreeTraverser factoryTraverser = new FactoryReviewTreeTraverser();
        setTreeTraversersFactory(factoryTraverser);
        setAuthorFeed(publisherFactory, factoryVisitor, factoryTraverser, reviewsFactory, db);
    }

    private void setDataValidator() {
        setDataValidator(new DataValidator());
    }

    private void setTagsManager() {
        FactoryTagsManager factory = new FactoryTagsManager();
        setTagsManager(factory.newTagsManager());
    }

    private void setReviewsFactory(FactoryReviewPublisher publisherFactory) {
        FactoryMdConverter convertersFactory = new FactoryMdConverter();
        ConverterMd mdConverter = convertersFactory.newMdConverter();
        FactoryReviewNode factory = new FactoryReviewNode();
        setFactoryReviews(new FactoryReviews(publisherFactory, factory, mdConverter));
    }

    private ReviewerDb makeReviewerDb(Context context, String databaseName, int version,
                                      FactoryReviews reviewsFactory,
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

        FactoryReviewLoader loaderFactory = new FactoryReviewLoader();
        ReviewLoader loader = loaderFactory.newStaticLoader(reviewsFactory, validator);
        FactoryDbTableRow rowFactory = new FactoryDbTableRow();

        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        return dbFactory.newDatabase(dbHelper, loader, tagsManager, validator);
    }

    private void setAuthorFeed(FactoryReviewPublisher publisherFactory,
                                             FactoryVisitorReviewNode visitorFactory,
                                             FactoryReviewTreeTraverser traverserFactory,
                                             FactoryReviews reviewsFactory,
                                             ReviewerDb db) {
        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();
        FactoryReviewsFeed feedFactory = new FactoryReviewsFeed();

        ReviewsFeedMutable reviewsFeed
                = feedFactory.newMutableFeed(repoFactory.newDatabaseRepository(db),
                publisherFactory, reviewsFactory, visitorFactory, traverserFactory);

        setAuthorsFeed(reviewsFeed);
    }

    private void setSocialPlatforms(Context context) {
        FactorySocialPlatformList factory = new FactorySocialPlatformList();

        setSocialPlatforms(factory.newList(context));
    }
}
