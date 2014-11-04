/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: review locations. Each grid cell shows a location name.
 * <p/>
 * <p>
 * Base class functionality details:
 * <ul>
 * <li>Banner button: launches {@link ActivityReviewLocationMap} showing current location</li>
 * <li>Grid cell click: launches {@link ActivityReviewLocationMap} showing clicked location</li>
 * </ul>
 * </p>
 */
public class FragmentReviewLocations extends FragmentReviewGridAddEdit<GVLocation> {
    public FragmentReviewLocations() {
        super(GVType.LOCATIONS);
        setActivityResultCode(Action.ADD, ActivityResultCode.DONE);
    }
}