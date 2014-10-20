/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.GVLocationList.GVLocation;

/**
 * ViewHolder: GVLocation. Shows shortened location name.
 *
 * @see com.chdryra.android.reviewer.GVLocationList.GVLocation
 */
class VHLocation extends VHText {

    public VHLocation(final boolean showAt) {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GVLocation location = (GVLocation) data;
                String at = showAt ? "@" : "";
                return location != null ? at + location.getShortenedName() : null;
            }
        });
    }
}
