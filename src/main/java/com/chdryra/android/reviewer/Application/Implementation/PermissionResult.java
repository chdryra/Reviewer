/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.PermissionsSuite;

/**
 * Created by: Rizwan Choudrey
 * On: 03/01/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class PermissionResult {
    private final PermissionsSuite.Permission mPermission;
    private final boolean mResult;

    public PermissionResult(PermissionsSuite.Permission permission, boolean result) {
        mPermission = permission;
        mResult = result;
    }

    public boolean isGranted(PermissionsSuite.Permission permission) {
        return mPermission.equals(permission) && mResult;
    }
}
