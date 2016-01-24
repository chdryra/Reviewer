/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Interfaces.Social;

import java.util.Iterator;

/**
 * Singleton that holds the list of social platforms on which reviews can be shared. Placeholders
 * for finding latest number of followers on each platform.
 */
public interface SocialPlatformList extends Iterable<SocialPlatform> {

    void update();

    int size();

    @Override
    Iterator<SocialPlatform> iterator();
}
