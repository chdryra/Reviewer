package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
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
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Models.UserModel.Author;
import com.chdryra.android.reviewer.Models.UserModel.UserId;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewerDbProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseApplicationContext extends ApplicationContextBasic {
    private static final Author AUTHOR = new Author("Rizwan Choudrey", UserId
            .generateId());
    private static final String DATABASE_NAME = "Reviewer.db";
    private static final int DATABASE_VER = 1;

    public ReleaseApplicationContext(Context context) {
        this(context, DATABASE_NAME, DATABASE_VER);
    }

    protected ReleaseApplicationContext(Context context, String databaseName, int databaseVersion) {
        //MdGvConverter
        setMdGvConverter(new MdGvConverter());

        //FactoryReview
        FactoryReviewNodeComponent factory = new FactoryReviewNodeComponent();
        setFactoryReview(new FactoryReview(factory, getMdGvConverter()));

        //TagsManager
        setTagsManager(new TagsManager());

        //SocialPlatforms
        setSocialPlatforms(new SocialPlatformList(context));

        //BuilderChildListScreen
        setBuilderChildListScreen(new BuilderChildListScreen());

        //FactoryReviewViewAdapter
        GvDataAggregater aggregater = new GvDataAggregater();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();
        setFactoryReviewViewAdapter(new FactoryReviewViewAdapter(getBuilderChildListScreen(),
                viewerFactory, aggregater, getReviewsRepository(), getMdGvConverter()));

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
        setReviewerDb(new ReviewerDb(dbHelper, loader, rowFactory, getTagsManager()));

        //ReviewsRepository
        ReviewerDbProvider provider = new ReviewerDbProvider(getReviewerDb());
        getReviewerDb().registerObserver(provider);
        setReviewsRepository(new ReviewsRepository(provider, getReviewFactory(), AUTHOR));
    }
}
