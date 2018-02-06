/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;

import java.net.URL;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataUrl extends DataFact {
    String TYPE_NAME = "link";

    URL getUrl();

    @Override
    String getLabel();

    @Override
    boolean hasData(DataValidator dataValidator);
}
