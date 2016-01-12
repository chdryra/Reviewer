package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbTableImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableComments extends DbTableImpl<RowComment> {
    private static final String TABLE = "Comments";
    
    public TableComments(DbTable<? extends RowReview> reviewsTable,
                         FactoryDbColumnDef columnFactory,
                         FactoryForeignKeyConstraint constraintFactory,
                         StorageTypeDefinitions typeFactory) {
        super(TABLE, RowComment.class);
        DbColumnDef commentId = columnFactory.newPkColumnDef(RowComment.COLUMN_COMMENT_ID,
                typeFactory.getTextType());
        DbColumnDef reviewId = columnFactory.newColumnDef(RowComment.COLUMN_REVIEW_ID,
                typeFactory.getTextType(), ValueNullable.FALSE);
        DbColumnDef comment = columnFactory.newColumnDef(RowComment.COLUMN_COMMENT,
                typeFactory.getTextType(), ValueNullable.FALSE);
        DbColumnDef isHeadline = columnFactory.newColumnDef(RowComment.COLUMN_IS_HEADLINE,
                typeFactory.getBooleanType(), ValueNullable.FALSE);

        addPrimaryKey(commentId);
        addColumn(reviewId);
        addColumn(comment);
        addColumn(isHeadline);

        ArrayList<DbColumnDef> fkCols = new ArrayList<>();
        fkCols.add(reviewId);
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
