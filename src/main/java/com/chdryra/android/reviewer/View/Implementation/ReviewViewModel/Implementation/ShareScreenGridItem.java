package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenGridItem extends GridItemActionNone<GvSocialPlatform> {
    @Override
    public void onGridItemClick(GvSocialPlatform platform,
                                int position, View v) {
        platform.press();
        v.setActivated(platform.isChosen());
    }
}
