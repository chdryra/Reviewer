/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface PackingLauncher<T> extends UiLauncherWrapper {
    void launch(@Nullable T item);

    @Nullable
    T unpack(Bundle args);

    @Override
    void setUiLauncher(UiLauncher uiLauncher);
}
