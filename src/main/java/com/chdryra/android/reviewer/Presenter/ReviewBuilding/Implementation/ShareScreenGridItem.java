package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;


import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemActionNone;

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
