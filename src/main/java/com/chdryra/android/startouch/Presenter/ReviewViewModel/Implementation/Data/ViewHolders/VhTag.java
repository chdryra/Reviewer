/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.corelibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvText;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .TagsManager.ReviewTag} ({@link GvText}). Shows tag
 * string.
 */
public class VhTag extends VhText {
    public VhTag() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                return StringParser.parse((GvTag) data);
            }
        });
    }
}