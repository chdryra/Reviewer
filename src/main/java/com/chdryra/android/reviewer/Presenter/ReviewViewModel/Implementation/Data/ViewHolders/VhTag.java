/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvText;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .TagsManager.ReviewTag} ({@link GvText}). Shows tag
 * string.
 */
public class VhTag extends VhText {
    //Constructors
    public VhTag(final boolean hashTag) {
        super(new VHDataStringGetter() {
            //Overridden
            @Override
            public String getString(ViewHolderData data) {
                GvTag tag = (GvTag) data;
                String hash = hashTag ? "#" : "";
                return tag != null ? hash + tag.getString() : null;
            }
        });
    }
}