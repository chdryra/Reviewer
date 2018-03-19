/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewOptions {
    private CommandList mBasicCommands;
    private ShareCommand mShareCommand;
    private BookmarkCommand mBookmarkCommand;

    private boolean mInitialising = false;
    private BookmarkCommand.BookmarkReadyCallback mInterimCallback;

    private boolean mIsOffline = false;

    public ReviewOptions(CommandList basicCommands) {
        mBasicCommands = basicCommands;
        mShareCommand = null;
        mBookmarkCommand = null;
        mIsOffline = true;
    }

    public ReviewOptions(CommandList basicCommands, ShareCommand share, BookmarkCommand
            bookmarkCommand) {
        mBasicCommands = basicCommands;
        mShareCommand = share;
        mBookmarkCommand = bookmarkCommand;
        initialiseBookmark();
    }

    public boolean isOffline() {
        return mIsOffline;
    }

    public CommandList getBasicCommands() {
        return mBasicCommands;
    }

    public CommandList getAllCommands() {
        CommandList commands = new CommandList(mBasicCommands);
        commands.add(mShareCommand);
        commands.add(mBookmarkCommand);
        return commands;
    }

    public ShareCommand getShareCommand() {
        return mShareCommand;
    }

    public BookmarkCommand getBookmarkCommand() {
        return mBookmarkCommand;
    }

    public void initialiseBookmark(BookmarkCommand.BookmarkReadyCallback callback) {
        if (isOffline() || mBookmarkCommand.isInitialised()) callback.onBookmarkCommandReady();
        if (mInitialising) mInterimCallback = callback;
    }

    private void initialiseBookmark() {
        mInitialising = true;
        mBookmarkCommand.initialise(new BookmarkCommand.BookmarkReadyCallback() {
            @Override
            public void onBookmarkCommandReady() {
                mInitialising = false;
                if (mInterimCallback != null) {
                    mInterimCallback.onBookmarkCommandReady();
                    mInterimCallback = null;
                }
            }
        });
    }
}
