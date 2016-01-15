package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryType;



import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends DbTableImpl<RowReview> {
    private static final String TABLE = "Reviews";
    private static final String REVIEW_ID = RowReview.COLUMN_REVIEW_ID;
    private static final String PARENT_ID = RowReview.COLUMN_PARENT_ID;
    private static final String USER_ID = RowReview.COLUMN_USER_ID;
    private static final String PUBLISH_DATE = RowReview.COLUMN_PUBLISH_DATE;
    private static final String SUBJECT = RowReview.COLUMN_SUBJECT;
    private static final String RATING = RowReview.COLUMN_RATING;
    private static final String RATING_WEIGHT = RowReview.COLUMN_RATING_WEIGHT;
    private static final String RATING_IS_AVERAGE = RowReview.COLUMN_RATING_IS_AVERAGE;

    public TableReviews(FactoryDbColumnDef columnFactory,
                        DbTable<? extends RowAuthor> authorsTable,
                        FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowReview.class);

        addPrimaryKeyColumn(columnFactory.newPkColumn(REVIEW_ID, DbEntryType.TEXT));
        addColumn(columnFactory.newNullableColumn(PARENT_ID, DbEntryType.TEXT));
        addColumn(columnFactory.newNotNullableColumn(USER_ID, DbEntryType.TEXT));
        addColumn(columnFactory.newNotNullableColumn(PUBLISH_DATE, DbEntryType.LONG));
        addColumn(columnFactory.newNotNullableColumn(SUBJECT, DbEntryType.TEXT));
        addColumn(columnFactory.newNotNullableColumn(RATING, DbEntryType.FLOAT));
        addColumn(columnFactory.newNotNullableColumn(RATING_WEIGHT, DbEntryType.INTEGER));
        addColumn(columnFactory.newNotNullableColumn(RATING_IS_AVERAGE, DbEntryType.BOOLEAN));

        ArrayList<DbColumnDefinition> fkColParent = new ArrayList<>();
        fkColParent.add(getColumn(PARENT_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColParent, this));

        ArrayList<DbColumnDefinition> fkColUser = new ArrayList<>();
        fkColUser.add(getColumn(USER_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColUser, authorsTable));
    }

}
