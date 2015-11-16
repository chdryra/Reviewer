package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableFacts extends DbTableImpl<RowFact> {
    private static final String TABLE = "Facts";
    
    public TableFacts(DbTable<? extends RowReview> reviewsTable, FactoryDbColumnDef columnFactory,
                      FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowFact.class);
        DbColumnDef factId = columnFactory.newPkColumnDef(RowFact.COLUMN_FACT_ID,
                SQL.StorageType.TEXT);
        DbColumnDef reviewId = columnFactory.newColumnDef(RowFact.COLUMN_REVIEW_ID,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef label = columnFactory.newColumnDef(RowFact.COLUMN_LABEL,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef value = columnFactory.newColumnDef(RowFact.COLUMN_VALUE,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef isUrl = columnFactory.newColumnDef(RowFact.COLUMN_IS_URL,
                SQL.StorageType.INTEGER, SQL.Nullable.FALSE);

        addPrimaryKey(factId);
        addColumn(reviewId);
        addColumn(label);
        addColumn(value);
        addColumn(isUrl);

        ArrayList<DbColumnDef> fkCols = new ArrayList<>();
        fkCols.add(reviewId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
