/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FeedArgs {
    public static final String AuthorId = TagKeyGenerator.getKey(FeedArgs.class, "AuthorId");
}
