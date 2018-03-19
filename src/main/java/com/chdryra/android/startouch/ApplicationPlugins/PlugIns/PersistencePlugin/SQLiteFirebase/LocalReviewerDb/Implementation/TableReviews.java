/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviews extends ReviewerDbTableImpl<RowReview> {
    public static final String NAME = "Reviews";

    public TableReviews(FactoryDbColumnDef columnFactory,
                        DbTable<? extends RowAuthorName> authorsTable,
                        FactoryForeignKeyConstraint constraintFactory) {
        super(NAME, RowReview.class, columnFactory);

        addPkColumn(RowReview.REVIEW_ID);
        addNotNullableColumn(RowReview.AUTHOR_ID);
        addNotNullableColumn(RowReview.PUBLISH_DATE);
        addNotNullableColumn(RowReview.SUBJECT);
        addNotNullableColumn(RowReview.RATING);
        addNotNullableColumn(RowReview.RATING_WEIGHT);

        ArrayList<DbColumnDefinition> fkColUser = new ArrayList<>();
        fkColUser.add(getColumn(RowReview.AUTHOR_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkColUser, authorsTable));
    }

}
