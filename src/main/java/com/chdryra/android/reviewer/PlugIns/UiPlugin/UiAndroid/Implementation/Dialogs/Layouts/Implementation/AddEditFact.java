/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.webkit.URLUtil;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditFact extends AddEditLayoutBasic<GvFact> {
    public static final int LAYOUT = R.layout.dialog_fact_add_edit;
    public static final int LABEL = R.id.fact_label_edit_text;
    public static final int VALUE = R.id.fact_value_edit_text;

    //Constructors
    public AddEditFact(GvDataAdder adder) {
        super(GvFact.class, new LayoutHolder(LAYOUT, LABEL, VALUE), VALUE, adder);
    }

    public AddEditFact(GvDataEditor editor) {
        super(GvFact.class, new LayoutHolder(LAYOUT, LABEL, VALUE), VALUE, editor);
    }

    private GvFact newUrl(String label, String urlString) {
        String urlGuess = URLUtil.guessUrl(urlString.toLowerCase());
        try {
            return new GvUrl(label, new URL(urlGuess));
        } catch (MalformedURLException e) {
            return new GvFact(label, urlString);
        }
    }

    //Overridden
    @Override
    public GvFact createGvDataFromInputs() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        ArrayList<String> urls = TextUtils.getLinks(value);

        GvFact fact = null;
        if (urls.size() > 0) fact = newUrl(label, urls.get(0));
        if (fact == null) fact = new GvFact(label, value);

        return fact;
    }

    @Override
    public void updateLayout(GvFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }
}
