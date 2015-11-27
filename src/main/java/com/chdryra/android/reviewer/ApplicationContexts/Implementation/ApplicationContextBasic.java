package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories
        .FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextBasic implements ApplicationContext {
    private FactoryReviews mFactoryReviews;
    private ReviewsFeedMutable mAuthorsFeed;
    private SocialPlatformList mSocialPlatforms;
    private DataValidator mDataValidator;

    private ConfigDataUi mConfigDataUi;
    private LaunchableUiLauncher mLauncher;

    private FactoryGvData mFactoryGvData;
    private FactoryReviewBuilderAdapter mFactoryBuilderAdapter;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private FactoryReviewViewLaunchable mFactoryReviewViewLaunchable;

    protected ApplicationContextBasic() {

    }

    public void setFactoryReviewViewLaunchable(FactoryReviewViewLaunchable
                                                       factoryReviewViewLaunchable) {
        mFactoryReviewViewLaunchable = factoryReviewViewLaunchable;
    }

    public void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }

    public void setFactoryReviews(FactoryReviews factoryReviews) {
        mFactoryReviews = factoryReviews;
    }

    public void setLauncher(LaunchableUiLauncher launcher) {
        mLauncher = launcher;
    }

    public void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
    }

    public void setAuthorsFeed(ReviewsFeedMutable authorsFeed) {
        mAuthorsFeed = authorsFeed;
    }

    public void setSocialPlatforms(SocialPlatformList socialPlatforms) {
        mSocialPlatforms = socialPlatforms;
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
        return mAuthorsFeed;
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
    public LaunchableUiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }

    @Override
    public FactoryReviewViewLaunchable getReviewViewLaunchableFactory() {
        return mFactoryReviewViewLaunchable;
    }
}
