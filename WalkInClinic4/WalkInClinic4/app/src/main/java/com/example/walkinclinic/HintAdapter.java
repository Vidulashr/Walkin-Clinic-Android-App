package com.example.walkinclinic;
import android.widget.ArrayAdapter;

import java.sql.Array;
import java.util.List;
import android.content.Context;

public class HintAdapter extends ArrayAdapter<String> {

    public HintAdapter(Context theContext, List<String> objects, int theLayoutResId) {
        super(theContext, theLayoutResId, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}