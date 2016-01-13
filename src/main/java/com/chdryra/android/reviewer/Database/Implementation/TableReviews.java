package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueTypeDefinitions;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends DbTableImpl<RowReview> {
    private static final String TABLE = "Reviews";
    public static final String REVIEW_ID = RowReview.COLUMN_REVIEW_ID;
    public static final String PARENT_ID = RowReview.COLUMN_PARENT_ID;
    public static final String USER_ID = RowReview.COLUMN_USER_ID;
    public static final String PUBLISH_DATE = RowReview
            .COLUMN_PUBLISH_DATE;
    public static final String SUBJECT = RowReview.COLUMN_SUBJECT;
    public static final String RATING = RowReview.COLUMN_RATING;
    public static final String RATING_WEIGHT = RowReview.COLUMN_RATING_WEIGHT;
    public static final String RATING_IS_AVERAGE = RowReview.COLUMN_RATING_IS_AVERAGE;

    public TableReviews(FactoryDbColumnDef columnFactory,
                        RowValueTypeDefinitions types,
                        DbTable<? extends RowAuthor> authorsTable,
                        FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowReview.class);

        RowValueType text = types.getTextType();
        RowValueType lng = types.getLongType();
        RowValueType flt = types.getFloatType();
        RowValueType integer = types.getIntegerType();
        RowValueType bool = types.getBooleanType();

        addPrimaryKeyColumn(columnFactory.newPkColumn(REVIEW_ID, text));
        addColumn(columnFactory.newNullableColumn(PARENT_ID, text));
        addColumn(columnFactory.newNotNullableColumn(USER_ID, text));
        addColumn(columnFactory.newNotNullableColumn(PUBLISH_DATE, lng));
        addColumn(columnFactory.newNotNullableColumn(SUBJECT, text));
        addColumn(columnFactory.newNotNullableColumn(RATING, flt));
        addColumn(columnFactory.newNotNullableColumn(RATING_WEIGHT, integer));
        addColumn(columnFactory.newNotNullableColumn(RATING_IS_AVERAGE, bool));

        ArrayList<DbColumnDefinition> fkColParent = new ArrayList<>();
        fkColParent.add(getColumn(PARENT_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColParent, this));

        ArrayList<DbColumnDefinition> fkColUser = new ArrayList<>();
        fkColUser.add(getColumn(USER_ID));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColUser, authorsTable));
    }

}
