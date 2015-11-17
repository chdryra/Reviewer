package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Factories.FactoryDataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryRvaGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryGridDataViewer;
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
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryTreeDataGetter;
import com.chdryra.android.reviewer.Models.Social.Factories.FactorySocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.Factories.FactoryTagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.Factories.FactoryReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.TreeMethods.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseApplicationContext extends ApplicationContextBasic {
    public ReleaseApplicationContext(Context context, Author author, File extDir,
                                     String databaseName, int databaseVersion) {
        //TagsManager
        setTagsManager();

        //MdGvConverter
        setDataConverters();

        //FactoryReview
        FactoryReviewPublisher publisherFactory = new FactoryReviewPublisher(author);
        setFactoryReviews(publisherFactory);

        //SocialPlatforms
        setSocialPlatforms(context);

        //BuilderChildListScreen
        setBuilderChildListScreen();

        //FactoryReviewViewAdapter
        setFactoryReviewViewAdapter();

        //DataValidator
        setDataValidator();

        //ReviewerDb
        setReviewerDb(context, databaseName, databaseVersion);

        //ReviewBuilderAdapter
        setReviewBuilderAdapterFactory(context);

        //ReviewsRepository
        setReviewsProvider(publisherFactory);

        //FileIncrementor
        setFileIncrementor(context, author, extDir);
    }

    private void setFileIncrementor(Context context, Author author, File extDir) {
        String dir = context.getString(context.getApplicationInfo().labelRes);

        setFactoryFileIncrementor(new FactoryFileIncrementor(extDir, dir,
                author.getName(), getDataValidator()));
    }

    private void setReviewsProvider(FactoryReviewPublisher publisherFactory) {
        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();
        ReviewsRepository repo = repoFactory.newDatabaseRepository(getReviewerDb());

        FactoryReviewsProvider providerFactory = new FactoryReviewsProvider();
        setReviewsProvider(providerFactory.newProvider(repo, publisherFactory, getReviewsFactory()));
    }

    private void setReviewBuilderAdapterFactory(Context context) {
        FactoryGridUi<? extends GvDataList, ReviewBuilderAdapter> gridUi = new FactoryRvaGridUi();
        FactoryVhBuildReviewData factoryVhBuildReviewData = new FactoryVhBuildReviewData();
        FactoryDataBuilderAdapter factoryDataBuilderAdapter = new FactoryDataBuilderAdapter
                (context);
        FactoryImageChooser factoryImageChooser = new FactoryImageChooser(context);
        FactoryReviewBuilder factoryReviewBuilder = new FactoryReviewBuilder(getDataConverters()
                .getGvConverter(), getTagsManager(), getReviewsFactory(), getDataValidator());
        FactoryReviewBuilderAdapter builderAdapterFactory
                = new FactoryReviewBuilderAdapter(factoryReviewBuilder,
                gridUi,
                factoryVhBuildReviewData,
                getDataValidator(),
                factoryDataBuilderAdapter, getFileIncrementorFactory(), factoryImageChooser);

        setBuilderAdapterFactory(builderAdapterFactory);
    }

    private void setReviewerDb(Context context, String databaseName, int version) {
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
        ReviewLoader loader = loaderFactory.newStaticLoader(getReviewsFactory(), getDataValidator());
        FactoryDbTableRow rowFactory = new FactoryDbTableRow();

        FactoryReviewerDb dbFactory = new FactoryReviewerDb(rowFactory);
        ReviewerDb db = dbFactory.newDatabase(dbHelper, loader, getTagsManager(), getDataValidator());
        setReviewerDb(db);
    }

    private void setDataValidator() {
        setDataValidator(new DataValidator());
    }

    private void setFactoryReviewViewAdapter() {
        GvDataAggregater aggregater = new GvDataAggregater();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();

        setFactoryReviewViewAdapter(new FactoryReviewViewAdapter(getBuilderChildListScreen(),
                viewerFactory, aggregater, getReviewsProvider(),
                getDataConverters().getGvConverter()));
    }

    private void setBuilderChildListScreen() {
        setBuilderChildListScreen(new BuilderChildListScreen());
    }

    private void setSocialPlatforms(Context context) {
        FactorySocialPlatformList factory = new FactorySocialPlatformList();

        setSocialPlatforms(factory.newList(context));
    }

    private void setFactoryReviews(FactoryReviewPublisher publisherFactory) {
        FactoryVisitorReviewNode visitorFactory = new FactoryVisitorReviewNode();
        FactoryTreeDataGetter getterFactory = new FactoryTreeDataGetter();
        FactoryReviewNodeComponent factory = new FactoryReviewNodeComponent(visitorFactory, getterFactory);
        ConverterMd convertersMd = getDataConverters().getMdConverter();

        setFactoryReviews(new FactoryReviews(publisherFactory, factory, convertersMd));
    }

    private void setDataConverters() {
        FactoryDataConverters convertersFactory = new FactoryDataConverters(getTagsManager());

        setDataConverters(convertersFactory.newDataConverters());
    }

    private void setTagsManager() {
        FactoryTagsManager manager = new FactoryTagsManager();
        setTagsManager(manager.newTagsManager());
    }
}
