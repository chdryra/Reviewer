/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BookmarkCommand extends Command implements ReviewCollection.Callback {
    private static final String PLACEHOLDER = Strings.Commands.DASHES;
    private static final String UNBOOKMARK = Strings.Commands.UNBOOKMARK;
    private static final String BOOKMARK = Strings.Commands.BOOKMARK;
    private static final String BOOKMARKS_UNAVAILABLE = Strings.Commands.BOOKMARKS_OFFLINE;

    private final ReviewId mReviewId;
    private final ReviewCollection mBookmarks;

    private boolean mIsBookmarked = false;
    private boolean mLocked = false;
    private boolean mInitialised = false;
    private boolean mErrorChecking = false;
    private BookmarkReadyCallback mCallback;
    private List<BookmarkListener> mObservers;

    public interface BookmarkReadyCallback {
        void onBookmarkCommandReady();
    }

    public interface BookmarkListener {
        void onBookmarked(boolean isBookmarked);
    }

    public BookmarkCommand(ReviewId reviewId, ReviewCollection bookmarks) {
        super(PLACEHOLDER);
        mReviewId = reviewId;
        mBookmarks = bookmarks;
        mObservers = new ArrayList<>();
    }

    public void addListener(BookmarkListener observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
            if (mInitialised) observer.onBookmarked(mIsBookmarked);
        }
    }

    public void removeListener(BookmarkListener observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    public void initialise(BookmarkReadyCallback callback) {
        if (!mInitialised) {
            mCallback = callback;
            lock();
            mBookmarks.hasEntry(mReviewId, this);
        } else {
            callback.onBookmarkCommandReady();
        }
    }

    @Override
    public void execute() {
        if (mLocked || mErrorChecking) {
            onExecutionComplete();
            return;
        }

        lock();
        if (mIsBookmarked) {
            mBookmarks.removeEntry(mReviewId, this);
        } else {
            mBookmarks.addEntry(mReviewId, this);
        }
    }

    @Override
    public void onAddedToCollection(String name, CallbackMessage message) {
        mIsBookmarked = true;
        notifyObservers();
        unlock();
        onExecutionComplete();
    }

    @Override
    public void onRemovedFromCollection(String name, CallbackMessage message) {
        mIsBookmarked = false;
        notifyObservers();
        unlock();
        onExecutionComplete();
    }

    @Override
    public void onCollectionHasEntry(String name, boolean hasEntry, CallbackMessage message) {
        if (message.isOk()) {
            mIsBookmarked = hasEntry;
            mErrorChecking = false;
            setName(mIsBookmarked ? UNBOOKMARK : BOOKMARK);
        } else {
            mErrorChecking = true;
            setName(BOOKMARKS_UNAVAILABLE);
        }
        unlock();
        mInitialised = true;
        notifyObservers();
        mCallback.onBookmarkCommandReady();
    }

    boolean isInitialised() {
        return mInitialised;
    }

    private void notifyObservers() {
        for (BookmarkListener observer : mObservers) {
            observer.onBookmarked(mIsBookmarked);
        }
    }

    private void lock() {
        mLocked = true;
    }

    private void unlock() {
        mLocked = false;
    }
}
