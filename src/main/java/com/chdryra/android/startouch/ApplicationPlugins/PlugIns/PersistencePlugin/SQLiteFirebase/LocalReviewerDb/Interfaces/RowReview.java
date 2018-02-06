/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces;


import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Implementation.DbEntryType;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowReview extends ReviewDataRow<RowReview>, DataDate,
        DataSubject, DataRating {
    ColumnInfo<String> REVIEW_ID = new ColumnInfo<>("review_id", DbEntryType.TEXT);
    ColumnInfo<String> AUTHOR_ID = new ColumnInfo<>("author_id", DbEntryType.TEXT);
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
