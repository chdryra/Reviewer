/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

class DialogShower {
    public static void show(DialogFragment dialog, Fragment targetFragment, int requestCode,
                            String tag, Bundle args) {
        dialog.setTargetFragment(targetFragment, requestCode);
        dialog.setArguments(args);
        dialog.show(targetFragment.getFragmentManager(), tag);
    }
}
