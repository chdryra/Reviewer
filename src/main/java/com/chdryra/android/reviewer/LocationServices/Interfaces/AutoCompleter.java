/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Interfaces;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapterFiltered;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AutoCompleter extends ViewHolderAdapterFiltered.QueryFilter {
    void disconnectFromProvider();
}
