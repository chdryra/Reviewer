/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BroadcastingService<S extends IntentService,
        R extends BroadcastingServiceReceiver<Listener>, Listener> {
    private Context mContext;
    private Class<S> mServiceClass;
    private R mReceiver;

    public BroadcastingService(Context context, Class<S> serviceClass, R receiver) {
        mContext = context;
        mServiceClass = serviceClass;
        mReceiver = receiver;
    }

    public Intent newService() {
        return new Intent(mContext, mServiceClass);
    }

    public void startService(Intent mService) {
        mContext.startService(mService);
    }

    public void registerReceiverAction(String action) {
        IntentFilter actionFilter = new IntentFilter(action);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, actionFilter);
    }

    public void registerListener(Listener listener) {
        mReceiver.registerListener(listener);
    }

    public void unregisterListener(Listener listener) {
        mReceiver.unregisterListener(listener);
    }
}
