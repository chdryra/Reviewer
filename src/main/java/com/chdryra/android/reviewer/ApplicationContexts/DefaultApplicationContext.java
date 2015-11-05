package com.chdryra.android.reviewer.ApplicationContexts;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryGridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewerDbProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataAggregation.FactoryGvDataAggregate;
import com.chdryra.android.reviewer.View.Screens.FactoryChildListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultApplicationContext implements ApplicationContext {
    private static final Author AUTHOR = new Author("Rizwan Choudrey", UserId
            .generateId());

    private FactoryReview mFactoryReview;
    private TagsManager mTagsManager;
    private ReviewerDb mReviewerDb;
    private ReviewsRepository mReviewsRepository;
    private MdGvConverter mMdGvConverter;
    private SocialPlatformList mSocialPlatforms;
    private FactoryChildListScreen mFactoryChildListScreen;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;

    public DefaultApplicationContext(Context context) {
        mFactoryReview = new FactoryReview();
        mTagsManager = new TagsManager();
        mReviewerDb = ReviewerDb.getTestDatabase(context, mTagsManager);
        ReviewerDbProvider provider = new ReviewerDbProvider(mReviewerDb);
        mReviewerDb.registerObserver(provider);
        mReviewsRepository = new ReviewsRepository(provider, mFactoryReview, AUTHOR);
        mMdGvConverter = new MdGvConverter();
        mSocialPlatforms = new SocialPlatformList(context);
        mFactoryChildListScreen = new FactoryChildListScreen();
        FactoryGridDataViewer viewerFactory = new FactoryGridDataViewer();
        FactoryGvDataAggregate aggregateFactory = new FactoryGvDataAggregate();
        mFactoryReviewViewAdapter = new FactoryReviewViewAdapter(mFactoryChildListScreen,
                viewerFactory, aggregateFactory, mReviewsRepository, mMdGvConverter);
    }

    @Override
    public Author getAuthor() {
        return AUTHOR;
    }

    @Override
    public ReviewerDb getDataBase() {
        return mReviewerDb;
    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialPlatforms;
    }

    @Override
    public ReviewsRepository getReviewsRepository() {
        return mReviewsRepository;
    }

    @Override
    public MdGvConverter getMdGvConverter() {
        return mMdGvConverter;
    }

    @Override
    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mFactoryReviewViewAdapter;
    }

    @Override
    public FactoryChildListScreen getChildListScreenFactory() {
        return mFactoryChildListScreen;
    }

    @Override
    public FactoryReview getReviewFactory() {
        return mFactoryReview;
    }
}
