/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.View;

import android.content.Intent;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
