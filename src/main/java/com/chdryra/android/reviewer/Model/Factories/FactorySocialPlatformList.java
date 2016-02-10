/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformListImpl;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialPlatformList {
    public SocialPlatformList newList(Context context) {
        return new SocialPlatformListImpl(context);
    }
}
