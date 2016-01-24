package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerDbTableImpl<T extends DbTableRow> extends DbTableImpl<T> {
    private FactoryDbColumnDef mColumnFactory;

    public ReviewerDbTableImpl(String tableName, Class<T> rowClass, FactoryDbColumnDef columnFactory) {
        super(tableName, rowClass);
        mColumnFactory = columnFactory;
    }

    protected <Type> void addPkColumn(ColumnInfo<Type> info) {
        addPrimaryKeyColumn(mColumnFactory.newPkColumn(info.getName(), info.getType()));
    }

    protected <Type> void addNullableColumn(ColumnInfo<Type> info) {
        addColumn(mColumnFactory.newNullableColumn(info.getName(), info.getType()));
    }

    protected <Type> void addNotNullableColumn(ColumnInfo<Type> info) {
        addColumn(mColumnFactory.newNotNullableColumn(info.getName(), info.getType()));
    }
}
