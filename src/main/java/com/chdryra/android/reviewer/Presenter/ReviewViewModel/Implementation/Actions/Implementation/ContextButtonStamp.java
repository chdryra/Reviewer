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
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.ContextualButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.ReviewLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ContextButtonStamp<T extends GvData> extends ReviewViewActionBasic<T>
        implements ContextualButtonAction<T>, DataReference.DereferenceCallback<NamedAuthor> {
    private final ReviewLauncher mLauncher;
    private final AuthorId mAuthorId;
    private final String mDate;
    private String mName;

    public ContextButtonStamp(ReviewLauncher launcher, ReviewStamp stamp, AuthorsRepository repo) {
        mLauncher = launcher;
        mAuthorId = stamp.getAuthorId();
        mDate = stamp.toReadableDate();
        repo.getName(mAuthorId).dereference(this);
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void onClick(View v) {
        mLauncher.launchReviews(mAuthorId);
    }

    @Override
    public String getButtonTitle() {
        return mName != null ? mName + " " + mDate : mDate;
    }

    @Override
    public void onDereferenced(DataValue<NamedAuthor> value) {
        if(value.hasValue()) {
            mName = value.getData().getName();
            if(isAttached()) getReviewView().updateContextButton();
        }
    }
}
