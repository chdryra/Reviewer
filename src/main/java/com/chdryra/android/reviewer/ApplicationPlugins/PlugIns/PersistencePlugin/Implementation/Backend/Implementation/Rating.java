/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Rating {
    public static final String PATH = "rating";

    private float rating;
    private int ratingWeight;

    public Rating() {
    }

    public Rating(DataRating rating) {
        this.rating = rating.getRating();
        ratingWeight = rating.getRatingWeight();
    }

    public double getRating() {
        return rating;
    }

    public long getRatingWeight() {
        return ratingWeight;
    }

    public static int size() {
        return 2;
    }
}
