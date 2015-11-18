package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenGridItem extends GridItemActionNone<GvSocialPlatformList.GvSocialPlatform> {
    @Override
    public void onGridItemClick(GvSocialPlatformList.GvSocialPlatform platform,
                                int position, View v) {
        platform.press();
        v.setActivated(platform.isChosen());
    }
}
