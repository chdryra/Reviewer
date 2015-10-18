package com.chdryra.android.reviewer.View.ActivitiesFragments;

import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ShareScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 19/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityShareReview extends ActivityReviewView {
    @Override
    protected ReviewView createView() {
        return ShareScreen.newScreen(this);
    }
}
