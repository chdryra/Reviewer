package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbColumnDefinition {
    String getName();

    RowValueType getType();

    ValueNullable getNullable();
}
