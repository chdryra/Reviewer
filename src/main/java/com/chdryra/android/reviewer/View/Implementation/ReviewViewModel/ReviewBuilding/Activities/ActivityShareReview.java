package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Activities;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ActivitiesFragments
        .ActivityReviewView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView {
    @Override
    protected ReviewView createReviewView() {
        ApplicationInstance app = ApplicationInstance.getInstance(this);

        String title = getResources().getString(R.string.button_social);
        SocialPlatformList socialPlatforms = app.getSocialPlatformList();
        ReviewBuilderAdapter reviewInProgress = app.getReviewBuilderAdapter();
        if(reviewInProgress == null) throw new RuntimeException("Builder is null!");

        return new BuilderShareScreenView().buildView(title, socialPlatforms, reviewInProgress);
    }
}
