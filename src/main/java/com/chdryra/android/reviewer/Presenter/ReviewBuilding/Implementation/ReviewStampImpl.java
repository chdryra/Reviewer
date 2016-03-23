/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewStamp;

/**
 * Created by: Rizwan Choudrey
 * On: 18/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStampImpl implements ReviewStamp {
    private final DataAuthor mAuthor;
    private final DataDate mDate;
    private int mIndex;

    public ReviewStampImpl(DataAuthor author, DataDate date) {
        mAuthor = author;
        mDate = date;
    }

    @Override
    public DataAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public DataDate getDate() {
        return mDate;
    }

    @Override
    public int getPublishedIndex() {
        return mIndex++;
    }
}