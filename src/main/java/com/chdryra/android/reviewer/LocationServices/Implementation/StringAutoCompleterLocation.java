/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.LocationServices.Implementation;

import com.chdryra.android.mygenerallibrary.StringFilterAdapter;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AutoCompleter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhdLocatedPlace;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class StringAutoCompleterLocation implements StringFilterAdapter.StringFilter {
    private AutoCompleter<VhdLocatedPlace> mAutoCompleter;

    public StringAutoCompleterLocation(AutoCompleter<VhdLocatedPlace> autoCompleter) {
        mAutoCompleter = autoCompleter;
    }

    private String formatAddress(String address) {
        String[] addressComponents = address.split(",");
        StringBuilder sb = new StringBuilder();
        sb.append(addressComponents[0]);
        if (addressComponents.length > 1) {
            sb.append(",");
            sb.append(addressComponents[1]);
        }

        return sb.toString();
    }

    @Override
    public ArrayList<String> filter(String query) {
        ViewHolderDataList<VhdLocatedPlace> places = mAutoCompleter.filter(query);

        ArrayList<String> shortened = new ArrayList<String>();
        for (VhdLocatedPlace place : places) {
            shortened.add(formatAddress(place.getPlace().getDescription()));
        }

        return shortened;
    }
}
