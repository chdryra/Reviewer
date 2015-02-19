/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 28 January, 2015
 */

package com.chdryra.android.reviewer;

import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 28/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GridItemSocial extends ViewReviewAction.GridItemAction {
    @Override
    public void onGridItemClick(GvDataList.GvData item, View v) {
        GvSocialPlatformList.GvSocialPlatform platform =
                (GvSocialPlatformList.GvSocialPlatform) item;

        platform.press();
        v.setActivated(platform.isChosen());
    }
}
