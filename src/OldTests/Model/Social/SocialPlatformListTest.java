/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 January, 2015
 */

package com.chdryra.android.startouch.test.Model.Social;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformListTest extends AndroidTestCase {

    @SmallTest
    public void test() {
        SocialPlatformList list = SocialPlatformList.getList(getContext());
        assertNotNull(list);

        int i = 0;
        for (SocialPlatform platform : list) {
            i++;
            assertNotNull(platform.getName());
            assertNotNull(platform.getFollowers());
        }
        assertTrue(i == 6);
    }
}
