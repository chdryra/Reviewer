/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.view.View;
import android.widget.Toast;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemShareScreen extends GridItemActionNone<GvSocialPlatform> {
    @Override
    public void onGridItemClick(GvSocialPlatform platform, int position, View v) {
        if(platform.isAuthorised()) {
            platform.press();
            v.setActivated(platform.isChosen());
        } else {
            Toast.makeText(getActivity(), platform.getName() + " not currently authorised",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
