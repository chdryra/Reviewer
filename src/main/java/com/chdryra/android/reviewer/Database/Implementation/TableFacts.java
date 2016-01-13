package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.RowFact;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;

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
                      RowValueTypeDefinitions types,
                      DbTable<? extends RowReview> reviewsTable,
                      FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowFact.class);

        RowValueType text = types.getTextType();
        RowValueType bool = types.getBooleanType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(FACT_ID, text));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, text));
        addColumn(columnFactory.newNotNullableColumn(LABEL, text));
        addColumn(columnFactory.newNotNullableColumn(VALUE, text));
        addColumn(columnFactory.newNotNullableColumn(IS_URL, bool));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
