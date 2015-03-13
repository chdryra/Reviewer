/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 March, 2015
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 13/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditLocationsBannerButton extends EditScreenBannerButton {
    public EditLocationsBannerButton(String title) {
        super(ConfigGvDataUi.getConfig(GvLocationList.TYPE).getAdderConfig(), title);
    }

    @Override
    public boolean onLongClick(View v) {
        LaunchableUi mapUi = ConfigGvDataUi.getLaunchable(ActivityEditLocationMap.class);
        LauncherUi.launch(mapUi, getListener(), getRequestCode(), null, new Bundle());
        return true;
    }
}

