/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link GvLocationList.GvLocation}.
 * Shows shortened
 * location name.
 */
class VHLocation extends VHText {

    public VHLocation(final boolean showAt) {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GvLocationList.GvLocation location = (GvLocationList.GvLocation) data;
                String at = showAt ? "@" : "";
                return location != null ? at + location.getShortenedName() : null;
            }
        });
    }
}
