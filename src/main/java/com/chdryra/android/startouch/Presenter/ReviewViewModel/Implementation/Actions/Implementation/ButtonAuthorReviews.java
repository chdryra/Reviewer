/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;


import android.text.format.DateFormat;
import android.view.View;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.LaunchProfileCommand;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonAuthorReviews<T extends GvData> extends ButtonActionNone<T>
        implements DataReference.DereferenceCallback<AuthorName> {
    private final LaunchProfileCommand mLaunchProfile;
    private final AuthorId mAuthorId;
    private final String mDate;

    public ButtonAuthorReviews(LaunchProfileCommand launchProfile, ReviewStamp stamp, AuthorsRepo
            repo) {
        super(stamp.toReadableDate());
        mLaunchProfile = launchProfile;
        mAuthorId = stamp.getAuthorId();
        mDate = DateFormat.format("dd-MM-yy", stamp.getDate().getTime()).toString();
        repo.getAuthor(mAuthorId).dereference(this);
    }

    @Override
    public void onClick(View v) {
        mLaunchProfile.execute(mAuthorId);
        notifyListeners();
    }

    @Override
    public void onDereferenced(DataValue<AuthorName> value) {
        if (value.hasValue()) setTitle(value.getData().getName() + "\n" + mDate);
    }
}
