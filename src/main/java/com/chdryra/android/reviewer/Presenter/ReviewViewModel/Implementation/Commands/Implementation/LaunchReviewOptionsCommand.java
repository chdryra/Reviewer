/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation;



import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LaunchReviewOptionsCommand extends Command implements OptionSelectListener {
    public static final String AUTHOR_ID
            = TagKeyGenerator.getKey(LaunchReviewOptionsCommand.class, "AuthorId");

    private final LaunchOptionsCommand mOptionsCommand;
    private final FactoryCommands mCommandsFactory;
    private final UserSession mSession;

    private DataAuthorId mAuthorId;
    private CommandsList mCommands;

    public LaunchReviewOptionsCommand(LaunchOptionsCommand optionsCommand,
                                      FactoryCommands commandsFactory,
                                      UserSession Session) {
        super(Strings.Commands.REVIEW_OPTIONS);
        mOptionsCommand = optionsCommand;
        mCommandsFactory = commandsFactory;
        mSession = Session;
        mCommands = new CommandsList();
    }

    public LaunchReviewOptionsCommand(LaunchOptionsCommand optionsCommand,
                                      FactoryCommands commandsFactory,
                                      UserSession session,
                                      DataAuthorId authorId) {
        this(optionsCommand, commandsFactory, session);
        mAuthorId = authorId;
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        execute();
    }

    @Override
    public void execute() {
        if(mAuthorId == null) return;

        boolean hasDelete = mAuthorId.toString().equals(mSession.getAuthorId().toString());
        ReviewId reviewId = mAuthorId.getReviewId();

        mCommands = new CommandsList();
        mCommands.add(mCommandsFactory.newShareCommand(reviewId));
        mCommands.add(mCommandsFactory.newCopyCommand(reviewId));
        if(hasDelete) mCommands.add(mCommandsFactory.newDeleteCommand(reviewId));

        if(mAuthorId == null || mCommands.size() == 0) return;
        final int code = RequestCodeGenerator.getCode(LaunchReviewOptionsCommand.class,
                TextUtils.commaDelimited(mCommands.getCommandNames()));
        mOptionsCommand.execute(mCommands, null, code, new ExecutionListener() {
            @Override
            public void onCommandExecuted(int requestCode) {
                if(code == requestCode) onExecutionComplete();
            }
        });
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mOptionsCommand.onOptionSelected(requestCode, option);
    }
}
