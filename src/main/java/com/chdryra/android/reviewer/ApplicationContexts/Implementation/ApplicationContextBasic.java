package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviews;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextBasic implements ApplicationContext {
    private FactoryReviews mFactoryReviews;
    private TagsManager mTagsManager;
    private ReviewerDb mReviewerDb;
    private ReviewsProvider mReviewsProvider;
    private SocialPlatformList mSocialPlatforms;
    private BuilderChildListScreen mBuilderChildListScreen;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private DataConverters mDataConverters;
    private DataValidator mDataValidator;
    private FactoryFileIncrementor mFactoryFileIncrementor;
    private FactoryReviewBuilderAdapter mBuilderAdapterFactory;

    protected ApplicationContextBasic() {

    }

    public void setFactoryReviews(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setDataConverters(DataConverters converters) {
        mDataConverters = converters;
    }

    public void setBuilderAdapterFactory(FactoryReviewBuilderAdapter builderAdapterFactory) {
        mBuilderAdapterFactory = builderAdapterFactory;
    }

    public void setTagsManager(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public void setReviewerDb(ReviewerDb reviewerDb) {
        mReviewerDb = reviewerDb;
    }

    public void setReviewsProvider(ReviewsProvider reviewsProvider) {
        mReviewsProvider = reviewsProvider;
    }

    public void setSocialPlatforms(SocialPlatformList socialPlatforms) {
        mSocialPlatforms = socialPlatforms;
    }

    public void setBuilderChildListScreen(BuilderChildListScreen builderChildListScreen) {
        mBuilderChildListScreen = builderChildListScreen;
    }

    public void setFactoryReviewViewAdapter(FactoryReviewViewAdapter factoryReviewViewAdapter) {
        mFactoryReviewViewAdapter = factoryReviewViewAdapter;
    }

    public void setDataValidator(DataValidator dataValidator) {
        mDataValidator = dataValidator;
    }

    public void setFactoryFileIncrementor(FactoryFileIncrementor factoryFileIncrementor) {
        mFactoryFileIncrementor = factoryFileIncrementor;
    }

    @Override
    public ReviewerDb getReviewerDb() {
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
    public ReviewsProvider getReviewsProvider() {
        return mReviewsProvider;
    }

    @Override
    public DataConverters getDataConverters() {
        return mDataConverters;
    }

    @Override
    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mFactoryReviewViewAdapter;
    }

    @Override
    public BuilderChildListScreen getBuilderChildListScreen() {
        return mBuilderChildListScreen;
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mFactoryReviews;
    }

    @Override
    public DataValidator getDataValidator() {
        return mDataValidator;
    }

    @Override
    public FactoryFileIncrementor getFileIncrementorFactory() {
        return mFactoryFileIncrementor;
    }

    @Override
    public FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory() {
        return mBuilderAdapterFactory;
    }
}
