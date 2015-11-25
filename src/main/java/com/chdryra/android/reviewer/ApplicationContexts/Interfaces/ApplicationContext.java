package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    ReviewsFeedMutable getAuthorsFeed();
    SocialPlatformList getSocialPlatformList();
    DataValidator getDataValidator();
    FactoryReviewViewAdapter getReviewViewAdapterFactory();
    FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory();
    BuilderChildListView getBuilderChildListView();
    FactoryReviews getReviewsFactory();
    FactoryLauncherUi getLauncherFactory();
    FactoryLaunchableUi getLaunchableFactory();
    ConfigDataUi getUiConfig();
    FactoryReviewViewParams getParamsFactory();
    FactoryGvData getGvDataFactory();
}
