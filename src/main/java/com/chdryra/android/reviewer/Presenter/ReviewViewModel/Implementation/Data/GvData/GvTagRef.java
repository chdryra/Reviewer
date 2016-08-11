/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhTag;


public class GvTagRef extends GvDataRef<DataTag, VhTag> implements DataReference.InvalidationListener {
    public static final GvDataType<GvDataRef> TYPE = GvDataRef.getType(GvTag.TYPE);

    public GvTagRef(ReviewItemReference<DataTag> reference, DataConverter<DataTag, GvTag, ?> converter) {
        super(GvTag.TYPE, reference, converter, VhTag.class, new PlaceHolderFactory<DataTag>() {
            @Override
            public DataTag newPlaceHolder(String placeHolder) {
                return new GvTag(placeHolder);
            }
        });
    }



}
