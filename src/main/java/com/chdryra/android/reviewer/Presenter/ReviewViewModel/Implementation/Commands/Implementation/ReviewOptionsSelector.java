/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewOptionsSelector extends OptionsSelectAndExecute {
    private final FactoryCommands mFactory;
    private final UserSession mSession;

    private DataAuthorId mAuthorId;

    public ReviewOptionsSelector(OptionsSelector optionsCommand,
                                 FactoryCommands factory,
                                 UserSession Session) {
        super(Strings.Commands.REVIEW_OPTIONS, optionsCommand);
        mFactory = factory;
        mSession = Session;
    }

    public ReviewOptionsSelector(OptionsSelector optionsCommand,
                                 FactoryCommands factory,
                                 UserSession session,
                                 DataAuthorId authorId) {
        this(optionsCommand, factory, session);
        mAuthorId = authorId;
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        execute();
    }

    @Override
    public void execute() {
        if (mAuthorId == null) {
            onExecutionComplete();
        } else {
            setCommands(getCommands(mAuthorId));
            super.execute();
        }
    }

    @NonNull
    private CommandsList getCommands(DataAuthorId authorId) {
        mAuthorId = authorId;
        ReviewId reviewId = mAuthorId.getReviewId();
        boolean hasDelete = mAuthorId.toString().equals(mSession.getAuthorId().toString());

        CommandsList commands = new CommandsList();
        commands.add(mFactory.newShareCommand(reviewId));
        commands.add(mFactory.newCopyCommand(reviewId));
        if (hasDelete) commands.add(mFactory.newDeleteCommand(reviewId));

        return commands;
    }
}
