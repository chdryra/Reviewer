/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

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
                GvLocation location = (GvLocation) data;
                String name = location != null ? location.getShortenedName() : null;
                return name != null ? "@" + name : "";
            }
        });
    }
}
