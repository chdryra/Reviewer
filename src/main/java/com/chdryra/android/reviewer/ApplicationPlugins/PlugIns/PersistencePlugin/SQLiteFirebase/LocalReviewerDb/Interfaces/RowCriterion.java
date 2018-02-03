/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;


/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowCriterion extends ReviewDataRow<RowCriterion>, DataCriterion {
    ColumnInfo<String> CRITERION_ID = new ColumnInfo<>("criterion_id", DbEntryType.TEXT);
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<String> SUBJECT = new ColumnInfo<>("subject", DbEntryType.TEXT);
    ColumnInfo<Float> RATING = new ColumnInfo<>("rating", DbEntryType.FLOAT);

    @Override
    float getRating();

    @Override
    String getSubject();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
