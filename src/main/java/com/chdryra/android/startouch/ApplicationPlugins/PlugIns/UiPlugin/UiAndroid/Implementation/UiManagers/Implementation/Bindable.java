/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2018
 * Email: rizwan.choudrey@gmail.com
 */
public interface Bindable<T> {
    void update(T value);

    void onInvalidated();
}
