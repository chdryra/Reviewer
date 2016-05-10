/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterSignUp {
    private ApplicationInstance mApp;

    public PresenterSignUp(ApplicationInstance app) {
        mApp = app;
        UsersManager usersManager = mApp.getUsersManager();
    }

}
