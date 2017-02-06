
/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.AuthorReferenceDefault;

import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.NullDataReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullAuthorProfile implements AuthorProfile{

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
    public void getProfileSnapshot(ProfileCallback callback) {
        callback.onProfile(new AuthorProfileSnapshot(), CallbackMessage.error("null profile"));
    }
}
