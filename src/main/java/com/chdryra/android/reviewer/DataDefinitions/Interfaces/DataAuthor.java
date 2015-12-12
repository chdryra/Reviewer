package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAuthor extends Validatable{
    String getName();

    UserId getUserId();

    @Override
    boolean hasData(DataValidator dataValidator);
}
