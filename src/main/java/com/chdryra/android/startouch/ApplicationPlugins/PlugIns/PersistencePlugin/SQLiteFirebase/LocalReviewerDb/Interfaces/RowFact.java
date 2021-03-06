/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Interfaces;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowFact extends ReviewDataRow<RowFact>, DataFact {
    ColumnInfo<String> FACT_ID = new ColumnInfo<>("fact_id", DbEntryType.TEXT);
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<String> LABEL = new ColumnInfo<>("label", DbEntryType.TEXT);
    ColumnInfo<String> VALUE = new ColumnInfo<>("value", DbEntryType.TEXT);
    ColumnInfo<Boolean> IS_URL = new ColumnInfo<>("is_url", DbEntryType.BOOLEAN);

    @Override
    String getLabel();

    @Override
    String getValue();

    @Override
    boolean isUrl();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
