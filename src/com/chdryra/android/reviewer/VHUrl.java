/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.GVUrlList.GVUrl;

/**
 * ViewHolder: GVUrl. Shows shortened URL.
 * <p>
 * A VHTextView with an appropriately defined GVDataStringGetter.
 * </p>
 *
 * @see com.chdryra.android.reviewer.GVUrlList.GVUrl
 */
class VHUrl extends VHText {
    public VHUrl() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GVUrl url = (GVUrl) data;
                return url != null ? url.toShortenedString() : null;
            }
        });
    }
}