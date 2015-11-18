package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    ReviewerDb getReviewerDb();
    SocialPlatformList getSocialPlatformList();
    ReviewsProvider getReviewsProvider();
    DataConverters getDataConverters();
    DataValidator getDataValidator();
    FactoryReviewViewAdapter getReviewViewAdapterFactory();
    FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory();
    BuilderChildListView getBuilderChildListView();
    FactoryReviews getReviewsFactory();
    FactoryLaunchable getLaunchableFactory();
    ConfigDataUi getUiConfig();
}
