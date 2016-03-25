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
    private String subject;
    private float rating;

    public Criterion() {
    }

    public Criterion(DataCriterion criterion) {
        subject = criterion.getSubject();
        rating = criterion.getRating();
    }

    public String getSubject() {
        return subject;
    }

    public double getRating() {
        return rating;
    }
}
