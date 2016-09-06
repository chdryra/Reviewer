/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.NullSocialProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.SocialProfileImpl;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialProfile {
    public SocialProfile newSocialProfile(AuthorId id, RefAuthorList following) {
        return new SocialProfileImpl(id, following);
    }

    public SocialProfile newNullSocialProfile() {
        return new NullSocialProfile();
    }
}
