/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;


import com.chdryra.android.mygenerallibrary.LocationServices.LocationId;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocId {
    private String provider;
    private String locationId;

    public LocId() {
    }

    public LocId(LocationId id) {
        this.provider = id.getProvider().getProviderName();
        this.locationId = id.getId();
    }

    public String getProvider() {
        return provider;
    }

    public String getLocationId() {
        return locationId;
    }

    public static int size() {
        return 2;
    }
}
