/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPlatformsUploader {
    void registerListener(SocialPlatformsUploaderListener listener);

    void unregisterListener(SocialPlatformsUploaderListener listener);

    void upload(String reviewId, ArrayList<String> platformNames);
}
