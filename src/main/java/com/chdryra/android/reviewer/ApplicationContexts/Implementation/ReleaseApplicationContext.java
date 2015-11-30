package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Factories.FactoryDataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuildersGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
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
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Model.Factories.FactorySocialPlatformList;
import com.chdryra.android.reviewer.Model.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.Author;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Utils.FactoryFileIncrementor;
import com.chdryra.android.reviewer.View.Factories.FactoryConfigDataUi;
import com.chdryra.android.reviewer.View.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseApplicationContext extends ApplicationContextBasic {
    public ReleaseApplicationContext(Context context, Author author,
                                     File extDir, String imageDir,
                                     String databaseName, int databaseVersion) {
        //UI config
        ConfigDataUi config = setUiConfig();

        //FactoryLaunchable
        LaunchableUiLauncher launcher = new LaunchableUiLauncher(new FactoryLauncherUi());
        setLauncher(launcher);

        //DataValidator
        DataValidator dataValidator = new DataValidator();
        setDataValidator(dataValidator);

        //DataConverters
        FactoryTagsManager factory = new FactoryTagsManager();
        TagsManager tagsManager = factory.newTagsManager();
        DataConverters converters = getDataConverters(tagsManager);

        //FactoryReview
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        FactoryReviews reviewsFactory = setFactoryReviews(publisherFactory, converters.getMdConverter());

        //SocialPlatforms
        setSocialPlatforms(context);

        //ReviewsRepository
        ReviewerDb db = makeReviewerDb(context, databaseName, databaseVersion, reviewsFactory,
                dataValidator, tagsManager);
        FactoryVisitorReviewNode factoryVisitor = new FactoryVisitorReviewNode();
        ReviewsFeed provider = setAuthorFeed(publisherFactory, factoryVisitor, reviewsFactory, db);

        //FactoryReviewViewLaunchable
        FactoryReviewViewLaunchable launchableFactory
                = new FactoryReviewViewLaunchable(config, new FactoryReviewViewParams(), launcher);
        setFactoryReviewViewLaunchable(launchableFactory);

        //FactoryGvData
        FactoryGvData dataFactory = new FactoryGvData();
        setFactoryGvData(dataFactory);

        //FactoryReviewViewAdapter
        setFactoryReviewViewAdapter(dataFactory, launchableFactory, provider,
                converters.getGvConverter(), factoryVisitor);

        //FactoryReviewBuilderAdapter
        setReviewBuilderAdapterFactory(context, converters.getGvConverter(), tagsManager,
                reviewsFactory, dataFactory, dataValidator, extDir, imageDir, author.getName());
    }

    private ConfigDataUi setUiConfig() {
        FactoryConfigDataUi configBuilder = new FactoryConfigDataUi();
        ConfigDataUi defaultConfig = configBuilder.getDefaultConfig();
        setConfigDataUi(defaultConfig);
        return defaultConfig;
    }

    private ReviewsFeedMutable setAuthorFeed(FactoryReviewPublisher publisherFactory,
                                             FactoryVisitorReviewNode visitorFactory,
                                             FactoryReviews reviewsFactory,
                                             ReviewerDb db) {
        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository(visitorFactory);
        FactoryReviewsFeed feedFactory = new FactoryReviewsFeed();

        ReviewsFeedMutable reviewsFeed
                = feedFactory.newMutableFeed(repoFactory.newDatabaseRepository(db),
                publisherFactory,
                reviewsFactory,
                visitorFactory);

        setAuthorsFeed(reviewsFeed);

        return reviewsFeed;
    }

    private void setReviewBuilderAdapterFactory(Context context,
                                                ConverterGv converter,
                                                TagsManager tagsManager,
                                                FactoryReviews reviewsFactory,
                                                FactoryGvData dataFactory,
                                                DataValidator validator,
                                                File extDir, String dir, String authorName) {
        FactoryGridUi<? extends GvDataList> gridUi = new FactoryDataBuildersGridUi();
        FactoryVhBuildReviewData factoryVhBuildReviewData = new FactoryVhBuildReviewData();
        FactoryDataBuilderAdapter factoryDataBuilderAdapter = new FactoryDataBuilderAdapter
                (context);
        FactoryImageChooser factoryImageChooser = new FactoryImageChooser(context);
        FactoryDataBuilder dataBuilderFactory = new FactoryDataBuilder(converter);
        FactoryReviewBuilder factoryReviewBuilder = new FactoryReviewBuilder(converter,
                tagsManager, reviewsFactory, dataBuilderFactory, dataFactory, validator);
        FactoryFileIncrementor incrementorFactory = new FactoryFileIncrementor(extDir, dir,
                authorName, validator);
        FactoryReviewBuilderAdapter builderAdapterFactory
                = new FactoryReviewBuilderAdapter(factoryReviewBuilder,
                gridUi,
                factoryVhBuildReviewData,
                validator,
                factoryDataBuilderAdapter, incrementorFactory, factoryImageChooser);

        setFactoryBuilderAdapter(builderAdapterFactory);
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

    private void setFactoryReviewViewAdapter(FactoryGvData dataFactory,
                                             FactoryReviewViewLaunchable launchableFactory,
                                             ReviewsFeed provider,
                                             ConverterGv converter,
                                             FactoryVisitorReviewNode visitorFactory) {
        GvDataAggregater aggregater = new GvDataAggregater(dataFactory);
        FactoryReviewViewAdapter factory = new FactoryReviewViewAdapter(launchableFactory,
                visitorFactory, aggregater, provider, converter);
        setFactoryReviewViewAdapter(factory);
    }

    private void setSocialPlatforms(Context context) {
        FactorySocialPlatformList factory = new FactorySocialPlatformList();

        setSocialPlatforms(factory.newList(context));
    }

    private FactoryReviews setFactoryReviews(FactoryReviewPublisher publisherFactory,
                                             ConverterMd converter) {
        FactoryVisitorReviewNode visitorFactory = new FactoryVisitorReviewNode();
        FactoryReviewNodeComponent factory = new FactoryReviewNodeComponent(visitorFactory);

        FactoryReviews reviewsFactory = new FactoryReviews(publisherFactory, factory, converter);
        setFactoryReviews(reviewsFactory);
        return reviewsFactory;
    }

    private DataConverters getDataConverters(TagsManager tagsManager) {
        FactoryDataConverters convertersFactory = new FactoryDataConverters(tagsManager);
        return convertersFactory.newDataConverters();
    }
}
