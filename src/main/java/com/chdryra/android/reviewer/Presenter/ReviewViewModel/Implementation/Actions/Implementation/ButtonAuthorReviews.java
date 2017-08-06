/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;



import android.view.View;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 25/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ButtonAuthorReviews<T extends GvData> extends ButtonActionNone<T>
implements DataReference.DereferenceCallback<NamedAuthor>{
    private final ReviewLauncher mLauncher;
    private final AuthorId mAuthorId;
    private final String mDate;

    public ButtonAuthorReviews(ReviewLauncher launcher, ReviewStamp stamp, AuthorsRepository repo) {
        super(stamp.toReadableDate());
        mLauncher = launcher;
        mAuthorId = stamp.getAuthorId();
        mDate = stamp.toReadableDate();
        repo.getReference(mAuthorId).dereference(this);
    }

    @Override
    public void onClick(View v) {
        mLauncher.launchReviewsList(mAuthorId);
        notifyListeners();
    }

    @Override
    public void onDereferenced(DataValue<NamedAuthor> value) {
        if(value.hasValue()) setTitle(value.getData().getName() + " " + mDate);
    }
}
