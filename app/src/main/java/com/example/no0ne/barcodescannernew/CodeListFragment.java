package com.example.no0ne.barcodescannernew;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.no0ne.barcodescannernew.database.DatabaseHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CodeListFragment extends DialogFragment {


    public CodeListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_code_list, container, false);

        ListView listView = view.findViewById(R.id.list_view_code);
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> codeList = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        for (int i=0;i<databaseHelper.getTitle().size();i++) {
            titleList.add(databaseHelper.getTitle().get(i));
        }

        for (int i=0;i<databaseHelper.getCodeList().size();i++) {
            codeList.add(databaseHelper.getCodeList().get(i));
        }

        CodeListAdapter adapter = new CodeListAdapter(getActivity(), titleList, codeList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}
