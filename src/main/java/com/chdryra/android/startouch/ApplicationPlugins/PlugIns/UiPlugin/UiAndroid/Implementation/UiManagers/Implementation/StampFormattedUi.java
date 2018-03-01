/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorNameDefault;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StampFormattedUi extends SimpleViewUi<TextView, AuthorName> {
    private final DataDate mDate;
    private AuthorId mAuthorId;

    public StampFormattedUi(TextView view, DataDate date, @Nullable final Command onClick) {
        super(view, onClick);
        mDate = date;
    }

    @Override
    AuthorName getValue() {
        AuthorId authorId = mAuthorId != null ? mAuthorId : new DatumAuthorId();
        return new AuthorNameDefault(getView().getText().toString().trim(), authorId);
    }

    @Override
    public void update(AuthorName value) {
        mAuthorId = value.getAuthorId();
        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date(mDate
                .getTime()));
        String stamp = value.getName() + " / " + date;
        getView().setText(stamp);
    }
}
