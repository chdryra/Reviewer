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
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link GvUrlList.GvUrl}. Shows
 * shortened URL.
 * <p>
 * A VHTextView with an appropriately defined GVDataStringGetter.
 * </p>
 */
class VhUrl extends VhText {
    public VhUrl() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GvUrlList.GvUrl url = (GvUrlList.GvUrl) data;
                return url != null ? url.toShortenedString() : null;
            }
        });
    }
}
