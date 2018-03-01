/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.View.GridDataViewer;

/**
 * Created by: Rizwan Choudrey
 * On: 07/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridDataWrapper<T extends GvData> extends GridDataViewer<T> {
    DataReference<GvDataList<T>> getGridDataReference();

    DataReference<DataSize> getGridDataSize();

    void attachAdapter(ReviewViewAdapterBasic<T> adapter);

    void detachAdapter();
}
