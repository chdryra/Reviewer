/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullAuthor {
    public static final DataAuthor AUTHOR = new DatumAuthor("APP", new DatumUserId("APP"));
}
