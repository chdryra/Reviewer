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
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces.RowAuthorName;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends ReviewerDbTableImpl<RowAuthorName> {
    public static final String NAME = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(NAME, RowAuthorName.class, columnFactory);

        addPkColumn(RowAuthorName.AUTHOR_ID);
        addNotNullableColumn(RowAuthorName.AUTHOR_NAME);
    }
}
