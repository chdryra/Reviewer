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
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .TagsManager.ReviewTag} ({@link GvText}). Shows tag
 * string.
 */
class VhTag extends VhText {
    public VhTag(final boolean hashTag) {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                GvTagList.GvTag tag = (GvTagList.GvTag) data;
                String hash = hashTag ? "#" : "";
                return tag != null ? hash + tag.get() : null;
            }
        });
    }
}