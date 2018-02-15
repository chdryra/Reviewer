/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorId;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchProfileCommand extends Command {
    private static final String PROFILE = "Profile";
    private final LaunchableConfig mProfile;
    private final AuthorId mAuthorId;

    public LaunchProfileCommand(LaunchableConfig profile, @Nullable AuthorId authorId) {
        super(PROFILE);
        mProfile = profile;
        mAuthorId = authorId;
    }

    public void execute(@Nullable AuthorId authorId) {
        Bundle args = new Bundle();
        if(authorId != null) {
            ParcelablePacker<GvAuthorId> packer = new ParcelablePacker<>();
            packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, new GvAuthorId(authorId.toString()), args);
        }

        mProfile.launch(new UiLauncherArgs(mProfile.getDefaultRequestCode()).setBundle(args));
        onExecutionComplete();
    }

    @Override
    public void execute() {
        execute(mAuthorId);
    }
}
