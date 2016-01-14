package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ForeignKeyConstraint<T extends DbTableRow> {
    ArrayList<DbColumnDefinition> getFkColumns();

    DbTable<T> getForeignTable();
}
