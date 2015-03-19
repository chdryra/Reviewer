/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer;

import android.webkit.URLUtil;
import android.widget.EditText;

import com.chdryra.android.mygenerallibrary.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutFact extends GvDataEditLayout<GvFactList.GvFact> {
    public static final int   LAYOUT = R.layout.dialog_fact;
    public static final int   LABEL  = R.id.fact_label_edit_text;
    public static final int   VALUE  = R.id.fact_value_edit_text;
    public static final int[] VIEWS  = new int[]{LABEL, VALUE};

    public LayoutFact(GvDataAdder adder) {
        super(GvFactList.GvFact.class, LAYOUT, VIEWS, VALUE, adder);
    }

    public LayoutFact(GvDataEditor editor) {
        super(GvFactList.GvFact.class, LAYOUT, VIEWS, VALUE, editor);
    }

    @Override
    public GvFactList.GvFact createGvData() {
        String label = ((EditText) getView(LABEL)).getText().toString().trim();
        String value = ((EditText) getView(VALUE)).getText().toString().trim();
        ArrayList<String> urls = TextUtils.getLinks(value);

        GvFactList.GvFact fact = null;
        if (urls.size() > 0) fact = newUrl(label, urls.get(0));
        if (fact == null) fact = new GvFactList.GvFact(label, value);

        return fact;
    }

    @Override
    public void updateLayout(GvFactList.GvFact fact) {
        ((EditText) getView(LABEL)).setText(fact.getLabel());
        ((EditText) getView(VALUE)).setText(fact.getValue());
        getView(LABEL).requestFocus();
    }

    private GvUrlList.GvUrl newUrl(String label, String urlString) {
        String urlGuess = URLUtil.guessUrl(urlString);
        try {
            return new GvUrlList.GvUrl(label, new URL(urlGuess));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
