/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableLocations extends ReviewerDbTableImpl<RowLocation> {
    public static final String NAME = "Locations";

    public TableLocations(FactoryDbColumnDef columnFactory,
                          DbTable<? extends RowReview> reviewsTable,
                          FactoryForeignKeyConstraint constraintFactory) {
        super(NAME, RowLocation.class, columnFactory);

        addPkColumn(RowLocation.LOCATION_ID);
        addNotNullableColumn(RowLocation.REVIEW_ID);
        addNotNullableColumn(RowLocation.LATITUDE);
        addNotNullableColumn(RowLocation.LONGITUDE);
        addNotNullableColumn(RowLocation.NAME);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(getColumn(RowLocation.REVIEW_ID.getName()));
        addForeignKeyConstraint(constraintFactory.newConstraint(fkCols, reviewsTable));
    }

}
