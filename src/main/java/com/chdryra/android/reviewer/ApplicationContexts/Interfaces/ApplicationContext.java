package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.FactoryFileIncrementor;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    ReviewerDb getReviewerDb();
    TagsManager getTagsManager();
    SocialPlatformList getSocialPlatformList();
    ReviewsProvider getReviewsProvider();
    DataConverters getDataConverters();
    DataValidator getDataValidator();
    FactoryReviewViewAdapter getReviewViewAdapterFactory();
    FactoryReviewBuilderAdapter getReviewBuilderAdapterFactory();
    BuilderChildListScreen getBuilderChildListScreen();
    FactoryReviews getReviewsFactory();
    FactoryFileIncrementor getFileIncrementorFactory();
}
