/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;


import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataEditListener<T extends GvData> {
    void onDelete(T data, int requestCode);

    void onEdit(T oldDatum, T newDatum, int requestCode);
}
