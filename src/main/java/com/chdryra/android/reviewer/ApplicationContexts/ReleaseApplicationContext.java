package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;
import android.os.Environment;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.ConverterMd;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.FactoryDataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.BuilderReviewerDbContract;
import com.chdryra.android.reviewer.Database.DbContractExecutor;
import com.chdryra.android.reviewer.Database.DbHelper;
import com.chdryra.android.reviewer.Database.DbSpecification;
import com.chdryra.android.reviewer.Database.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.ReviewLoaderStatic;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManagerImpl;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.Models.UserModel.UserId;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseApplicationContext extends ApplicationContextBasic {
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private static final Author AUTHOR = new Author("Rizwan Choudrey", UserId
            .generateId());
    private static final String DATABASE_NAME = "Reviewer.db";
    private static final int DATABASE_VER = 1;

    public ReleaseApplicationContext(Context context) {
        this(context, DATABASE_NAME, DATABASE_VER);
    }

    protected ReleaseApplicationContext(Context context, String databaseName, int databaseVersion) {
        //TagsManager
        setTagsManager(new TagsManagerImpl());

        //MdGvConverter
        FactoryDataConverters convertersFactory = new FactoryDataConverters(getTagsManager());
        setDataConverters(convertersFactory.newDataConverters());

        //FactoryReview
        FactoryReviewNodeComponent factory = new FactoryReviewNodeComponent();
        ConverterMd convertersMd = getDataConverters().getMdConverter();
        setFactoryReview(new FactoryReview(factory, convertersMd));

        //SocialPlatforms
        setSocialPlatforms(new SocialPlatformList(context));

        //BuilderChildListScreen
        setBuilderChildListScreen(new BuilderChildListScreen());

        //FactoryReviewViewAdapter
        GvDataAggregater aggregater = new GvDataAggregater();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();
        setFactoryReviewViewAdapter(new FactoryReviewViewAdapter(getBuilderChildListScreen(),
                viewerFactory, aggregater, getReviewsProvider(),
                getDataConverters().getGvConverter()));

        //DataValidator
        setDataValidator(new DataValidator());

        //ReviewerDb
        BuilderReviewerDbContract builder = new BuilderReviewerDbContract();
        ReviewerDbContract contract = builder.newContract();
        DbSpecification<ReviewerDbContract> spec
                = new DbSpecification<>(databaseName, contract, databaseVersion);
        DbHelper<ReviewerDbContract> dbHelper
                = new DbHelper<>(context, spec, new DbContractExecutor());
        ReviewerDb.ReviewLoader loader = new ReviewLoaderStatic(getReviewFactory(), getDataValidator());
        FactoryDbTableRow rowFactory =new FactoryDbTableRow();
        setReviewerDb(new ReviewerDb(dbHelper, loader, rowFactory, getTagsManager(),
                getDataValidator()));

        //ReviewsRepository
        ReviewerDbRepository provider = new ReviewerDbRepository(getReviewerDb());
        getReviewerDb().registerObserver(provider);
        setReviewsProvider(new ReviewsProvider(provider, getReviewFactory(), AUTHOR));

        //FileIncrementor
        String dir = context.getString(context.getApplicationInfo().labelRes);
        setFactoryFileIncrementor(new FactoryFileIncrementor(FILE_DIR_EXT, dir,
                AUTHOR.getName(), getDataValidator()));
    }
}
