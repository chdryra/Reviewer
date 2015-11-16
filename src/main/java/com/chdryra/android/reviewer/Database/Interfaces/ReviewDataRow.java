/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReview;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewDataRow extends DataReview, DbTableRow {
    @Override
    String getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
