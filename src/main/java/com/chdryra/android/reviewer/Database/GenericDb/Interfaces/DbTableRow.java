package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.Validatable;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbTableRow extends Validatable{
    //abstract
    String getRowId();

    String getRowIdColumnName();

    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
