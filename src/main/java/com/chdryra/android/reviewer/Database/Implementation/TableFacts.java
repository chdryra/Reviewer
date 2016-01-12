package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueTypeDefinitions;
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
                      FactoryForeignKeyConstraint constraintFactory, RowValueTypeDefinitions typeFactory) {
        super(TABLE, RowFact.class);
        DbColumnDef factId = columnFactory.newPkColumnDef(RowFact.COLUMN_FACT_ID,
                typeFactory.getStringType());
        DbColumnDef reviewId = columnFactory.newColumnDef(RowFact.COLUMN_REVIEW_ID,
                typeFactory.getStringType(), ValueNullable.FALSE);
        DbColumnDef label = columnFactory.newColumnDef(RowFact.COLUMN_LABEL,
                typeFactory.getStringType(), ValueNullable.FALSE);
        DbColumnDef value = columnFactory.newColumnDef(RowFact.COLUMN_VALUE,
                typeFactory.getStringType(), ValueNullable.FALSE);
        DbColumnDef isUrl = columnFactory.newColumnDef(RowFact.COLUMN_IS_URL,
                typeFactory.getBooleanType(), ValueNullable.FALSE);

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
