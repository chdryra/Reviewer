package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
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
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    //Model
    ReviewsFeedMutable getAuthorsFeed();
    SocialPlatformList getSocialPlatformList();
    DataValidator getDataValidator();
    FactoryReviews getReviewsFactory();

    //View
    LaunchableUiLauncher getUiLauncher();
    ConfigDataUi getUiConfig();

    FactoryReviewViewLaunchable getReviewViewLaunchableFactory();
    FactoryReviewViewAdapter getReviewViewAdapterFactory();
    FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory();
    FactoryGvData getGvDataFactory();
}
