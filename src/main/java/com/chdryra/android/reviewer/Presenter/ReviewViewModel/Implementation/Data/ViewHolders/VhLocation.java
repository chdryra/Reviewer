/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .View.GvLocationList.GvLocation}.
 * Shows shortened
 * location name.
 */
public class VhLocation extends VhText {

    //Constructors
    public VhLocation(final boolean showAt) {
        super(new VHDataStringGetter() {
            //Overridden
            @Override
            public String getString(ViewHolderData data) {
                GvLocation location = (GvLocation) data;
                String at = showAt ? "@" : "";
                return location != null ? at + location.getShortenedName() : null;
            }
        });
    }
}
