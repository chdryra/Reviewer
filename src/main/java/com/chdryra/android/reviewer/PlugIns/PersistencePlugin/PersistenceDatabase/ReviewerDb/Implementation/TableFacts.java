package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableFacts extends DbTableImpl<RowFact> {
    private static final String TABLE = "Facts";
    private static final String FACT_ID = RowFact.COLUMN_FACT_ID;
    private static final String REVIEW_ID = RowFact.COLUMN_REVIEW_ID;
    private static final String LABEL = RowFact.COLUMN_LABEL;
    private static final String VALUE = RowFact.COLUMN_VALUE;
    private static final String IS_URL = RowFact.COLUMN_IS_URL;

    public TableFacts(FactoryDbColumnDef columnFactory,
                      DbTable<? extends RowReview> reviewsTable,
                      FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowFact.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(FACT_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(LABEL, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(VALUE, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(IS_URL, DbEntryTypes.BOOLEAN));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
