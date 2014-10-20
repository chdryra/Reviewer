/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.VHDDualString;
import com.chdryra.android.mygenerallibrary.VHDualString;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.GVSocialPlatformList.GVSocialPlatform;

/**
 * ViewHolder: GVSocialPlatform. Shows platform name above, number followers below.
 *
 * @see com.chdryra.android.reviewer.GVSocialPlatformList.GVSocialPlatform
 */
class VHSocialPlatform extends VHDualString {
    private static final int LAYOUT = R.layout.grid_cell_text_dual;
    private static final int UPPER  = R.id.upper_text;
    private static final int LOWER  = R.id.lower_text;

    VHSocialPlatform() {
        super(LAYOUT, UPPER, LOWER);
    }

    @Override
    public void updateView(ViewHolderData data) {
        GVSocialPlatform platform = (GVSocialPlatform) data;
        if (platform != null) {
            super.updateView(new VHDDualString(platform.getName(),
                    String.valueOf(platform.getFollowers())));
        }
    }
}