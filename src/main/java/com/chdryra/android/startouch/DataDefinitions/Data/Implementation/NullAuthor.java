/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.NamedAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NullAuthor {
    public static final NamedAuthor AUTHOR = new DefaultNamedAuthor("NULL", new AuthorIdParcelable("NULL"));
}