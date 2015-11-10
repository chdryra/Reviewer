package com.chdryra.android.reviewer.View.ActivitiesFragments;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Screens.BuilderShareScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView {
    @Override
    protected ReviewView createReviewView() {
        Administrator admin = Administrator.getInstance(this);
        String title = getResources().getString(R.string.button_social);
        SocialPlatformList socialPlatforms = admin.getSocialPlatformList();
        ReviewBuilderAdapter builder = admin.getReviewBuilderAdapter();
        BuilderShareScreen shareScreen = new BuilderShareScreen();
        return shareScreen.createView(title, socialPlatforms, builder);
    }
}
