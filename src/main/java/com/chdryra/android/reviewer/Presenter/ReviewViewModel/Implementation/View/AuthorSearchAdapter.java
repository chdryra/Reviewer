/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorList;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 13/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorSearchAdapter extends ReviewViewAdapterImpl<GvAuthor>
        implements ReviewViewAdapter.Filterable<GvAuthor> {
    private AuthorsRepository mRepo;
    private DataConverter<NamedAuthor, GvAuthor, GvAuthorList> mConverter;

    public AuthorSearchAdapter(ViewerAuthors wrapper,
                               AuthorsRepository repo,
                               DataConverter<NamedAuthor, GvAuthor, GvAuthorList> converter) {
        super(wrapper);
        mRepo = repo;
        mConverter = converter;
    }

    @Override
    public void filterGrid(String query, final Filterable.Callback callback) {
        mRepo.search(query, new AuthorsRepository.SearchAuthorsCallback() {
            @Override
            public void onAuthors(List<NamedAuthor> suggestions, CallbackMessage message) {
                GvAuthorList data;
                if(!message.isError()) {
                    data = mConverter.convert(suggestions, null);
                } else {
                    data = new GvAuthorList();
                    data.add(new GvAuthor("No authors found", new GvAuthorId("")));
                }
                ((ViewerAuthors)getWrapper()).setData(data);
                callback.onFiltered();
            }
        });
    }
}
