/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationSuite {
    AuthenticationSuite getAuthentication();

    LocationServicesSuite getLocationServices();

    UiSuite getUi();

    RepositorySuite getRepository();

    EditorSuite getEditor();

    SocialSuite getSocial();

    NetworkSuite getNetwork();

    PermissionsManager getPermissions();
}
