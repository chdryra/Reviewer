
/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;


import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Interfaces.ProfileReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.AuthorReferenceDefault;

import com.chdryra.android.startouch.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullAuthorProfile implements ProfileReference {

    public NullAuthorProfile() {
    }

    @Override
    public AuthorReference getAuthor() {
        return new AuthorReferenceDefault();
    }

    @Override
    public DataReference<ProfileImage> getProfileImage() {
        return new NullDataReference<>();
    }

    @Override
    public void dereference(Callback callback) {
        callback.onProfile(new AuthorProfile(), CallbackMessage.error("null profile"));
    }
}
