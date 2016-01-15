package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryTypes;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableComments extends DbTableImpl<RowComment> {
    private static final String TABLE = "Comments";
    private static final String COMMENT_ID = RowComment.COLUMN_COMMENT_ID;
    private static final String REVIEW_ID = RowComment.COLUMN_REVIEW_ID;
    private static final String COMMENT = RowComment.COLUMN_COMMENT;
    private static final String IS_HEADLINE = RowComment.COLUMN_IS_HEADLINE;

    public TableComments(FactoryDbColumnDef columnFactory,
                         DbTable<? extends RowReview> reviewsTable,
                         FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowComment.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(COMMENT_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(REVIEW_ID, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(COMMENT, DbEntryTypes.TEXT));
        addColumn(columnFactory.newNotNullableColumn(IS_HEADLINE, DbEntryTypes.BOOLEAN));

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
