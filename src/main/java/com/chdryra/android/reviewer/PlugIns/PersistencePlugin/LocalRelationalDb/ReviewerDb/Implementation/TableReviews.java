/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends ReviewerDbTableImpl<RowReview> {
    public static final String NAME = "Reviews";

    public TableReviews(FactoryDbColumnDef columnFactory,
                        DbTable<? extends RowAuthor> authorsTable,
                        FactoryForeignKeyConstraint constraintFactory) {
        super(NAME, RowReview.class, columnFactory);

        addPkColumn(RowReview.REVIEW_ID);
        addNullableColumn(RowReview.PARENT_ID);
        addNotNullableColumn(RowReview.USER_ID);
        addNotNullableColumn(RowReview.PUBLISH_DATE);
        addNotNullableColumn(RowReview.SUBJECT);
        addNotNullableColumn(RowReview.RATING);
        addNotNullableColumn(RowReview.RATING_WEIGHT);
        addNotNullableColumn(RowReview.IS_AVERAGE);

        ArrayList<DbColumnDefinition> fkColParent = new ArrayList<>();
        fkColParent.add(getColumn(RowReview.PARENT_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColParent, this));

        ArrayList<DbColumnDefinition> fkColUser = new ArrayList<>();
        fkColUser.add(getColumn(RowReview.USER_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColUser, authorsTable));
    }

}
