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
        .LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowTag;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTags extends ReviewerDbTableImpl<RowTag> {
    public static final String NAME = "Tags";

    public TableTags(FactoryDbColumnDef columnFactory,
                     DbTable<? extends RowReview> reviewsTable,
                     FactoryForeignKeyConstraint constraintFactory) {
        super(NAME, RowTag.class, columnFactory);

        addPkColumn(RowTag.TAG_ID);
        addNotNullableColumn(RowTag.REVIEW_ID);
        addNotNullableColumn(RowTag.TAG);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(RowTag.REVIEW_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }
}
