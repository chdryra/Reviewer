/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 March, 2015
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.remoteapifetchers.GpGeometry;
import com.chdryra.android.remoteapifetchers.GpName;
import com.chdryra.android.remoteapifetchers.GpPlaceId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GooglePlace extends LocatedPlace {
    public GooglePlace(GpGeometry geo, GpName name, GpPlaceId id) {
        super(geo.getLatLng(), name.getString(), id.getString());
    }
}
