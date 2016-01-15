package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;



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

    public TableComments(FactoryDbColumnDef columnFactory,
                         DbTable<? extends RowReview> reviewsTable,
                         FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowComment.class, columnFactory);

        addPkColumn(RowComment.COLUMN_COMMENT_ID, RowComment.COLUMN_COMMENT_ID_TYPE);
        addNotNullableColumn(RowComment.COLUMN_REVIEW_ID, RowComment.COLUMN_REVIEW_ID_TYPE);
        addNotNullableColumn(RowComment.COLUMN_COMMENT, RowComment.COLUMN_COMMENT_TYPE);
        addNotNullableColumn(RowComment.COLUMN_IS_HEADLINE, RowComment.COLUMN_IS_HEADLINE_TYPE);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(RowComment.COLUMN_REVIEW_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
