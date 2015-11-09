package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Validatable;

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

    boolean hasData(DataValidator validator);
}
