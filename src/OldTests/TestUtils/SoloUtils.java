/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 April, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import android.app.Activity;
import android.graphics.Point;

import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 30/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SoloUtils {

    //Static methods
    public static void pretouchScreen(Activity activity, Solo solo) {
        //To avoid issues caused by spurious menu touch events in starting activity for test.
        Point displaySize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
        solo.clickLongOnScreen(displaySize.x, displaySize.y);
    }
}
