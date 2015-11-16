package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends DbTableImpl<RowReview> {
    private static final String TABLE = "Reviews";
    
    public TableReviews(DbTable<? extends RowAuthor> authorsTable,
                        FactoryDbColumnDef columnFactory,
                        FactoryForeignKeyConstraint constraintFactory) {
        super(TABLE, RowReview.class);
        DbColumnDef reviewId = columnFactory.newPkColumnDef(RowReview.COLUMN_REVIEW_ID,
                SQL.StorageType.TEXT);
        DbColumnDef parentId = columnFactory.newColumnDef(RowReview.COLUMN_PARENT_ID,
                SQL.StorageType.TEXT, SQL.Nullable.TRUE);
        DbColumnDef authorId = columnFactory.newColumnDef(RowReview.COLUMN_AUTHOR_ID,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef publishDate = columnFactory.newColumnDef(RowReview.COLUMN_PUBLISH_DATE,
                SQL.StorageType.INTEGER, SQL.Nullable.FALSE);
        DbColumnDef subject = columnFactory.newColumnDef(RowReview.COLUMN_SUBJECT,
                SQL.StorageType.TEXT, SQL.Nullable.FALSE);
        DbColumnDef rating = columnFactory.newColumnDef(RowReview.COLUMN_RATING,
                SQL.StorageType.REAL, SQL.Nullable.FALSE);
        DbColumnDef ratingWeight = columnFactory.newColumnDef(RowReview.COLUMN_RATING_WEIGHT,
                SQL.StorageType.REAL, SQL.Nullable.FALSE);
        DbColumnDef isAverage = columnFactory.newColumnDef(RowReview.COLUMN_RATING_IS_AVERAGE,
                SQL.StorageType.INTEGER, SQL.Nullable.FALSE);

        addPrimaryKey(reviewId);
        addColumn(parentId);
        addColumn(authorId);
        addColumn(publishDate);
        addColumn(subject);
        addColumn(rating);
        addColumn(ratingWeight);
        addColumn(isAverage);

        ArrayList<DbColumnDef> fkColParent = new ArrayList<>();
        fkColParent.add(authorId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColParent, this));

        ArrayList<DbColumnDef> fkColAuthor = new ArrayList<>();
        fkColAuthor.add(authorId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColAuthor, authorsTable));
    }

}
