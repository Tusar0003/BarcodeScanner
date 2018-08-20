package com.example.no0ne.barcodescannernew;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by no0ne on 5/2/18.
 */

public class CodeListAdapter extends ArrayAdapter<String> {

    private Activity mContext;
    private List<String> mTitleList, mCodeList;

    public CodeListAdapter(Activity context, List<String> titleList, List<String> codeList) {
        super(context, R.layout.single_code_layout, titleList);
        this.mContext = context;
        this.mTitleList = titleList;
        this.mCodeList = codeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View view = inflater.inflate(R.layout.single_code_layout, null, true);

        TextView titleTextView = view.findViewById(R.id.text_view_title);
        TextView codeTextView = view.findViewById(R.id.text_view_code);

        titleTextView.setText(mTitleList.get(position));
        codeTextView.setText(mCodeList.get(position));

        return view;
    }
}
