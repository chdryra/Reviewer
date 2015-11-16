package com.chdryra.android.reviewer.Models.Social.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Models.Social.Implementation.SocialPlatformListImpl;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;

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
