/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface NamedAuthor extends Validatable{
    String getName();

    AuthorId getAuthorId();

    @Override
    boolean hasData(DataValidator dataValidator);
}
