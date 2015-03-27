/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 March, 2015
 */

package com.chdryra.android.reviewer.test;

import android.os.Bundle;

import com.chdryra.android.reviewer.LocatedPlace;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 27/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LocatedPlaceTest extends TestCase {

    public void testParcelable() {
        LatLng latlng = RandomLatLng.nextLatLng();
        String description = RandomString.nextSentence();
        String id = RandomString.nextWord();

        LocatedPlace placeUser = new LocatedPlace(latlng, description);
        LocatedPlace placeGoogle = new LocatedPlace(latlng, description,
                new LocatedPlace.LocationId(LocatedPlace.LocationProvider.GOOGLE, id));

        String keyUser = "user";
        String keyGoogle = "google";
        Bundle args = new Bundle();
        args.putParcelable(keyUser, placeUser);
        args.putParcelable(keyGoogle, placeGoogle);
        LocatedPlace argsUser = args.getParcelable(keyUser);
        LocatedPlace argsGoogle = args.getParcelable(keyGoogle);

        assertEquals(placeUser, argsUser);
        assertEquals(placeGoogle, argsGoogle);

    }
}
