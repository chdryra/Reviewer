/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthorProfile {
    AuthorReference getAuthor();

    DataReference<DateTime> getDateJoined();

    DataReference<Bitmap> getProfileImage();
}
