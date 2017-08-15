/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
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
    private BookmarkCommand.BookmarkCommandReadyCallback mInterimCallback;

    public ReviewOptions(CommandList basicCommands) {
        mBasicCommands = basicCommands;
        mBookmarkCommand = null;
    }

    public ReviewOptions(CommandList basicCommands, ShareCommand share, BookmarkCommand bookmarkCommand) {
        mBasicCommands = basicCommands;
        mShareCommand = share;
        mBookmarkCommand = bookmarkCommand;
        mInitialising = true;
        mBookmarkCommand.initialise(new BookmarkCommand.BookmarkCommandReadyCallback() {
            @Override
            public void onBookmarkCommandReady() {
                mInitialising = false;
                if(mInterimCallback != null) {
                    mInterimCallback.onBookmarkCommandReady();
                    mInterimCallback = null;
                }
            }
        });
    }

    public boolean hasBookmark() {
        return mBookmarkCommand != null;
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

    public void isBookmarkInitialised(BookmarkCommand.BookmarkCommandReadyCallback callback) {
        if (!hasBookmark() || mBookmarkCommand.isInitialised()) callback.onBookmarkCommandReady();
        if(mInitialising) mInterimCallback = callback;
    }
}
