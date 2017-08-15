/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;


import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewOptionsSelector extends OptionsSelectAndExecute {
    private final FactoryCommands mFactory;
    private final UserSession mSession;
    private final OptionsType mOptionsType;
    private DataAuthorId mAuthorId;
    private ReviewOptions mOptions;
    private boolean mInitialised;

    public enum OptionsType {ALL, BASIC}

    public ReviewOptionsSelector(OptionsSelector optionsCommand,
                                 FactoryCommands factory,
                                 UserSession Session,
                                 OptionsType optionsType) {
        super(Strings.Commands.REVIEW_OPTIONS, optionsCommand);
        mFactory = factory;
        mSession = Session;
        mOptionsType = optionsType;
    }

    public ReviewOptionsSelector(OptionsSelector optionsCommand,
                                 FactoryCommands factory,
                                 UserSession session,
                                 OptionsType optionsType,
                                 DataAuthorId authorId) {
        this(optionsCommand, factory, session, optionsType);
        mAuthorId = authorId;
        setOptions(false);
    }

    public void execute(DataAuthorId authorId) {
        mAuthorId = authorId;
        if(mInitialised) {
            execute();
        } else {
            setOptions(true);
        }
    }

    @Override
    public void execute() {
        if (mAuthorId == null) {
            onExecutionComplete();
        } else{
            ReviewOptionsSelector.super.execute();
        }
    }

    private void setOptions(final boolean executeOnSet) {
        mOptions = mFactory.newReviewOptions(mAuthorId, mSession);
        if(mOptions.hasBookmark()) {
            mOptions.isBookmarkInitialised(new BookmarkCommand.BookmarkCommandReadyCallback() {
                @Override
                public void onBookmarkCommandReady() {
                    if(!mInitialised) {
                        setCommands(mOptionsType == OptionsType.BASIC ? mOptions.getBasicCommands() : mOptions.getAllCommands());
                        mInitialised = true;
                    }
                    if(executeOnSet) execute();
                }
            });
        }
    }
}
