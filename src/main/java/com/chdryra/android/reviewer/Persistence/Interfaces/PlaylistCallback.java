/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 12/07/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PlaylistCallback {
    void onAddedToPlaylistCallback(CallbackMessage message);

    void onRemovedFromPlaylistCallback(CallbackMessage message);

    void onPlaylistHasReviewCallback(boolean hasReview, CallbackMessage message);
}
