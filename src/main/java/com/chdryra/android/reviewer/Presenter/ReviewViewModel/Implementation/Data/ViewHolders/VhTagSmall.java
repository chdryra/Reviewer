/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.mygenerallibrary.Viewholder.VHDataStringGetter;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvText;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .TagsManager.ReviewTag} ({@link GvText}). Shows tag
 * string.
 */
public class VhTagSmall extends VhTextSmall {
    public VhTagSmall() {
        super(new VHDataStringGetter() {
            @Override
            public String getString(ViewHolderData data) {
                return StringParser.parse((GvTag) data);
            }
        });
    }
}