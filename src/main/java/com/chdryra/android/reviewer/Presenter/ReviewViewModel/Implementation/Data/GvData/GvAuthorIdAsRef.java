/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 21/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorIdAsRef extends GvDataRef<GvAuthorIdAsRef, DataAuthor, VhAuthor> {
    public static final GvDataType<GvAuthorIdAsRef> TYPE
            = new GvDataType<>(GvAuthorIdAsRef.class, GvAuthor.TYPE);

    public GvAuthorIdAsRef(DataAuthorId authorId,
                           AuthorsRepository repo,
                           DataConverter<DataAuthor, ? extends GvDataParcelable, ?> converter) {
        super(TYPE, reference, converter, VhAuthor.class, factory);
    }


}
