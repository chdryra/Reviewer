/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs
        .Implementation;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogOneButtonFragment;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.PublisherAndroid;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogShareEditReview extends DialogOneButtonFragment implements
        LaunchableUi, Command.ExecutionListener {
    private static final int DELETE = RequestCodeGenerator.getCode(DialogShareEditReview.class, "Delete");
    private static final int SHARE = RequestCodeGenerator.getCode(DialogShareEditReview.class, "Share");
    private static final int COPY = RequestCodeGenerator.getCode(DialogShareEditReview.class, "Copy");
    private static final String TAG = TagKeyGenerator.getTag(DialogShareEditReview.class);

    private static final int LAYOUT = R.layout.dialog_share_edit_review;
    private static final int SHARE_BUTTON = R.id.button_share_review;
    private static final int COPY_BUTTON = R.id.button_another_review;
    private static final int DELETE_BUTTON = R.id.button_delete_review;
    private static final String NO_AUTHOR = "Must pass DatumAuthorId in args!";

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void onCommandExecuted(int requestCode) {
        dismiss();
    }

    @Override
    protected View createDialogUi() {
        Activity activity = getActivity();

        View layout = android.view.LayoutInflater.from(activity).inflate(LAYOUT, null);

        Button share = (Button) layout.findViewById(SHARE_BUTTON);
        Button another = (Button) layout.findViewById(COPY_BUTTON);
        Button delete = (Button) layout.findViewById(DELETE_BUTTON);

        ApplicationInstance app = AndroidAppInstance.getInstance(activity);
        DataAuthorId authorId = getAuthorId();
        ReviewId reviewId = authorId.getReviewId();
        CurrentScreen screen = app.getCurrentScreen();

        PublisherAndroid sharer = new PublisherAndroid(activity, new ReviewSummariser(), new ReviewFormatterTwitter());

        FactoryCommands factory = new FactoryCommands();
        final Command deleteCommand = factory.newDeleteCommand(app.newReviewDeleter(reviewId), screen);
        final Command shareCommand = factory.newShareCommand(app, reviewId, sharer);
        final Command copyCommand = factory.newCopyCommand(app.newBuildScreenLauncher(), reviewId, screen);

        final Command.ExecutionListener listener = this;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCommand.execute(SHARE, listener);
            }
        });

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyCommand.execute(COPY, listener);
            }
        });

        if (authorId.toString().equals(app.getUserSession().getAuthorId().toString())) {
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCommand.execute(DELETE, listener);
                }
            });
        } else {
            delete.setVisibility(View.GONE);
        }

        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftButtonAction(ActionType.DONE);
        setDialogTitle(null);
        hideKeyboardOnLaunch();
    }

    private DataAuthorId getAuthorId() {
        DataAuthorId authorId = null;
        if (getArguments() != null) authorId = getArguments().getParcelable(getLaunchTag());
        if(authorId == null) throw new IllegalArgumentException(NO_AUTHOR);

        return authorId;
    }
}

