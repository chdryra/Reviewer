/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces;


import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowReview extends ReviewDataRow<RowReview>, DataDateReview,
        DataSubject, DataRating {
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<String> USER_ID = new ColumnInfo<>("user_id", DbEntryType.TEXT);
    ColumnInfo<Long> PUBLISH_DATE = new ColumnInfo<>("publish_date", DbEntryType.LONG);
    ColumnInfo<String> SUBJECT = new ColumnInfo<>("subject", DbEntryType.TEXT);
    ColumnInfo<Float> RATING = new ColumnInfo<>("rating", DbEntryType.FLOAT);
    ColumnInfo<Integer> RATING_WEIGHT = new ColumnInfo<>("rating_weight", DbEntryType.INTEGER);

    String getAuthorId();

    long getPublishDate();

    @Override
    long getTime();

    @Override
    float getRating();

    @Override
    int getRatingWeight();

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
