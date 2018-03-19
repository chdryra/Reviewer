/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .View.GvLocationList.GvLocation}.
 * Shows shortened
 * location name.
 */
public class VhLocation extends VhText {
    public VhLocation() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                return StringParser.parse((GvLocation) data);
            }
        });
    }
}
