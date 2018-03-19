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
        .LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableFacts extends ReviewerDbTableImpl<RowFact> {
    public static final String NAME = "Facts";

    public TableFacts(FactoryDbColumnDef columnFactory,
                      DbTable<? extends RowReview> reviewsTable,
                      FactoryForeignKeyConstraint constraintFactory) {
        super(NAME, RowFact.class, columnFactory);

        addPkColumn(RowFact.FACT_ID);
        addNotNullableColumn(RowFact.REVIEW_ID);
        addNotNullableColumn(RowFact.LABEL);
        addNotNullableColumn(RowFact.VALUE);
        addNotNullableColumn(RowFact.IS_URL);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(RowFact.REVIEW_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
