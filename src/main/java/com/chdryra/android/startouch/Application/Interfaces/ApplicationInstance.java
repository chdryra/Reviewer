/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Interfaces;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.startouch.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationInstance extends ApplicationSuite {
    String APP_NAME = Strings.APP_NAME;
    String APP_SITE = Strings.APP_SITE;
    String APP_SITE_SHORT = Strings.APP_SITE_SHORT;

    void logout();

    void setReturnResult(ActivityResultCode result);
}

