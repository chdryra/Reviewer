package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Factories.FactoryReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextBasic implements ApplicationContext {
    private FactoryReviews mFactoryReviews;
    private ReviewsFeedMutable mAuthordFeed;
    private SocialPlatformList mSocialPlatforms;
    private BuilderChildListView mBuilderChildListView;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private DataValidator mDataValidator;
    private FactoryReviewBuilderAdapter mFactoryBuilderAdapter;
    private ConfigDataUi mConfigDataUi;
    private FactoryLaunchableUi mFactoryLaunchable;
    private FactoryLauncherUi mFactoryLauncher;
    private FactoryReviewViewParams mParamsFactory;
    private FactoryGvData mFactoryGvData;

    protected ApplicationContextBasic() {

    }

    public void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }

    public void setParamsFactory(FactoryReviewViewParams paramsFactory) {
        mParamsFactory = paramsFactory;
    }

    public void setFactoryReviews(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setFactoryLaunchable(FactoryLaunchableUi factoryLaunchable) {
        mFactoryLaunchable = factoryLaunchable;
    }

    public void setFactoryLauncher(FactoryLauncherUi factoryLauncher) {
        mFactoryLauncher = factoryLauncher;
    }

    public void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
    }

    public void setAuthorsFeed(ReviewsFeedMutable authorsFeed) {
        mAuthordFeed = authorsFeed;
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
    public ReviewsFeedMutable getAuthorsFeed() {
        return mAuthordFeed;
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialPlatforms;
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
    public FactoryLaunchableUi getLaunchableFactory() {
        return mFactoryLaunchable;
    }

    @Override
    public FactoryLauncherUi getLauncherFactory() {
        return mFactoryLauncher;
    }

    @Override
    public FactoryReviewViewParams getParamsFactory() {
        return mParamsFactory;
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }
}