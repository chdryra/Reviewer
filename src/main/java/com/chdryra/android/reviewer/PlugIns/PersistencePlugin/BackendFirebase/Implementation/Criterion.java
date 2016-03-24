/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Criterion {
    private String mSubject;
    private float mRating;

    public Criterion() {
    }

    public Criterion(DataCriterion criterion) {
        mSubject = criterion.getSubject();
        mRating = criterion.getRating();
    }

    public String getSubject() {
        return mSubject;
    }

    public double getRating() {
        return mRating;
    }

    public boolean isValid() {
        return mSubject != null && mSubject.length() > 0;
    }
}
