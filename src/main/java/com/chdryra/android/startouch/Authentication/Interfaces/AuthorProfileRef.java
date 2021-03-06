/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorRef;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthorProfileRef extends DataReference<AuthorProfile> {
    AuthorId getAuthorId();

    AuthorRef getAuthor();

    DataReference<ProfileImage> getImage();
}
