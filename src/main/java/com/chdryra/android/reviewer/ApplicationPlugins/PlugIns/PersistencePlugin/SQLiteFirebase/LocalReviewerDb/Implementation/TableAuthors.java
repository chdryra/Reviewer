/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Factories.FactoryDbColumnDef;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TableAuthors extends ReviewerDbTableImpl<RowAuthor> {
    public static final String NAME = "Authors";

    public TableAuthors(FactoryDbColumnDef columnFactory) {
        super(NAME, RowAuthor.class, columnFactory);

        addPkColumn(RowAuthor.AUTHOR_ID);
        addNotNullableColumn(RowAuthor.AUTHOR_NAME);
    }
}
