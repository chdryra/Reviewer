/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorName;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorList;


import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterAuthorSearch extends ReviewViewAdapterImpl<GvAuthorName>
        implements ReviewViewAdapter.Filterable<GvAuthorName> {
    private AuthorsRepo mRepo;
    private DataConverter<AuthorName, GvAuthorName, GvAuthorList> mConverter;

    public AdapterAuthorSearch(ViewerAuthors wrapper,
                               AuthorsRepo repo,
                               DataConverter<AuthorName, GvAuthorName, GvAuthorList> converter) {
        super(wrapper);
        mRepo = repo;
        mConverter = converter;
    }

    @Override
    public void filterGrid(String query, final Filterable.Callback callback) {
        mRepo.search(query, new AuthorsRepo.SearchAuthorsCallback() {
            @Override
            public void onAuthors(List<AuthorName> suggestions, CallbackMessage message) {
                GvAuthorList data;
                if(message.isOk()) {
                    data = mConverter.convert(suggestions, null);
                } else {
                    data = new GvAuthorList();
                    data.add(new GvAuthorName("No authors found", new GvAuthorId("")));
                }
                ((ViewerAuthors)getWrapper()).setData(data);
                callback.onFiltered();
            }
        });
    }
}
