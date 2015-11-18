package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextBasic implements ApplicationContext {
    private FactoryReviews mFactoryReviews;
    private ReviewerDb mReviewerDb;
    private ReviewsProvider mReviewsProvider;
    private SocialPlatformList mSocialPlatforms;
    private BuilderChildListView mBuilderChildListView;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private DataConverters mDataConverters;
    private DataValidator mDataValidator;
    private FactoryReviewBuilderAdapter mFactoryBuilderAdapter;
    private ConfigDataUi mConfigDataUi;
    private FactoryLaunchable mFactoryLaunchable;

    protected ApplicationContextBasic() {

    }

    public void setFactoryReviews(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setFactoryLaunchable(FactoryLaunchable factoryLaunchable) {
        mFactoryLaunchable = factoryLaunchable;
    }

    public void setDataConverters(DataConverters converters) {
        mDataConverters = converters;
    }

    public void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
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

    public void setBuilderChildListView(BuilderChildListView builderChildListView) {
        mBuilderChildListView = builderChildListView;
    }

    public void setFactoryReviewViewAdapter(FactoryReviewViewAdapter factoryReviewViewAdapter) {
        mFactoryReviewViewAdapter = factoryReviewViewAdapter;
    }

    public void setDataValidator(DataValidator dataValidator) {
        mDataValidator = dataValidator;
    }

    public void setConfigDataUi(ConfigDataUi configDataUi) {
        mConfigDataUi = configDataUi;
    }

    @Override
    public ReviewerDb getReviewerDb() {
        return mReviewerDb;
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
    public BuilderChildListView getBuilderChildListView() {
        return mBuilderChildListView;
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
    public FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory() {
        return mFactoryBuilderAdapter;
    }

    @Override
    public ConfigDataUi getUiConfig() {
        return mConfigDataUi;
    }

    @Override
    public FactoryLaunchable getLaunchableFactory() {
        return mFactoryLaunchable;
    }
}
