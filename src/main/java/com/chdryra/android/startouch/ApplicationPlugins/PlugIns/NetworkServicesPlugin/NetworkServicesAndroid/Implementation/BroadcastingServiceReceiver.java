/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation;

import android.content.BroadcastReceiver;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BroadcastingServiceReceiver<L> extends BroadcastReceiver implements
        Iterable<L> {
    private final ArrayList<L> mListeners;

    public BroadcastingServiceReceiver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener(L listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(L listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public Iterator<L> iterator() {
        return mListeners.iterator();
    }
}
