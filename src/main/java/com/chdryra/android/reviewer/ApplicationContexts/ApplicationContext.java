package com.chdryra.android.reviewer.ApplicationContexts;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.Screens.BuilderChildListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationContext {
    Author getAuthor();
    ReviewerDb getDataBase();
    TagsManager getTagsManager();
    SocialPlatformList getSocialPlatformList();
    ReviewsRepository getReviewsRepository();
    MdGvConverter getMdGvConverter();
    FactoryReviewViewAdapter getReviewViewAdapterFactory();
    BuilderChildListScreen getChildListScreenFactory();
    FactoryReview getReviewFactory();
}
