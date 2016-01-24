/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;



import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends ReviewerDbTableImpl<RowAuthor> {
    private static final String TABLE = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(TABLE, RowAuthor.class, columnFactory);

        addPkColumn(RowAuthor.USER_ID);
        addNotNullableColumn(RowAuthor.AUTHOR_NAME);
    }
}
