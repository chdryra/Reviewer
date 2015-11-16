package com.chdryra.android.reviewer.ApplicationInitialisation;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.FactoryDataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryDataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryImageChooser;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryRvaGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryVhBuildReviewData;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryGridUi;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.Database.DbContractExecutor;
import com.chdryra.android.reviewer.Database.DbHelper;
import com.chdryra.android.reviewer.Database.DbSpecification;
import com.chdryra.android.reviewer.Database.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.ReviewLoaderStatic;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManagerImpl;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsSource;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;
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
        setReviewBuilderAdapter(context);

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
        ReviewerDbRepository provider = new ReviewerDbRepository(getReviewerDb());
        getReviewerDb().registerObserver(provider);
        setReviewsProvider(new ReviewsSource(provider, publisherFactory, getReviewsFactory()));
    }

    private void setReviewBuilderAdapter(Context context) {
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

    private void setReviewerDb(Context context, String databaseName, int databaseVersion) {
        FactoryReviewerDbContract builder = new FactoryReviewerDbContract();
        ReviewerDbContract contract = builder.newContract();
        DbSpecification<ReviewerDbContract> spec
                = new DbSpecification<>(databaseName, contract, databaseVersion);
        DbHelper<ReviewerDbContract> dbHelper
                = new DbHelper<>(context, spec, new DbContractExecutor());
        ReviewerDb.ReviewLoader loader = new ReviewLoaderStatic(getReviewsFactory(), getDataValidator());
        FactoryDbTableRow rowFactory =new FactoryDbTableRow();
        setReviewerDb(new ReviewerDb(dbHelper, loader, rowFactory, getTagsManager(),
                getDataValidator()));
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
        setSocialPlatforms(new SocialPlatformList(context));
    }

    private void setFactoryReviews(FactoryReviewPublisher publisherFactory) {
        FactoryReviewNodeComponent factory = new FactoryReviewNodeComponent();
        ConverterMd convertersMd = getDataConverters().getMdConverter();
        setFactoryReviews(new FactoryReviews(publisherFactory, factory, convertersMd));
    }

    private void setDataConverters() {
        FactoryDataConverters convertersFactory = new FactoryDataConverters(getTagsManager());
        setDataConverters(convertersFactory.newDataConverters());
    }

    private void setTagsManager() {
        setTagsManager(new TagsManagerImpl());
    }
}
