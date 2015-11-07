package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.DbContractExecutor;
import com.chdryra.android.reviewer.Database.DbHelper;
import com.chdryra.android.reviewer.Database.DbSpecification;
import com.chdryra.android.reviewer.Database.FactoryTableRow;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Database.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
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
        setFactoryReview(new FactoryReview(getMdGvConverter()));

        //TagsManager
        setTagsManager(new TagsManager());

        //SocialPlatforms
        setSocialPlatforms(new SocialPlatformList(context));

        //BuilderChildListScreen
        setBuilderChildListScreen(new BuilderChildListScreen());

        //FactoryReviewViewAdapter
        setFactoryReviewViewAdapter(new FactoryReviewViewAdapter(getBuilderChildListScreen(),
                new FactoryGridDataViewer(), new GvDataAggregater(),
                getReviewsRepository(), getMdGvConverter()));

        //DataValidator
        setDataValidator(new DataValidator());

        //ReviewerDb
        DbSpecification spec = new DbSpecification(databaseName, new ReviewerDbContract(), databaseVersion);
        DbHelper dbHelper = new DbHelper(context, spec, new DbContractExecutor());
        setReviewerDb(new ReviewerDb(dbHelper, getTagsManager(), getReviewFactory(),
                new FactoryTableRow(getDataValidator())));

        //ReviewsRepository
        ReviewerDbProvider provider = new ReviewerDbProvider(getReviewerDb());
        getReviewerDb().registerObserver(provider);
        setReviewsRepository(new ReviewsRepository(provider, getReviewFactory(), AUTHOR));
    }
}
