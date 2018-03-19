/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;

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

    public static int size() {
        return 2;
    }

    public String getSubject() {
        return subject;
    }

    public double getRating() {
        return rating;
    }
}
