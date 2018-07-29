package com.dicoding.kharisazhar.mykamus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.dicoding.kharisazhar.mykamus.adapter.KamusAdapter;
import com.dicoding.kharisazhar.mykamus.db.KamusEnglishIndonesiaHelper;
import com.dicoding.kharisazhar.mykamus.db.KamusIndonesiaEnglishHelper;
import com.dicoding.kharisazhar.mykamus.model.KamusModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndonesiaEnglishFragment extends Fragment {

    private SearchView searchView;

    RecyclerView recyclerView;
    KamusAdapter kamusAdapter;
    KamusIndonesiaEnglishHelper kamusIndonesiaEnglishHelper;


    public IndonesiaEnglishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indonesia_english, container, false);

        recyclerView = view.findViewById(R.id.rv_word);
        searchView = view.findViewById(R.id.search_view);

        searchData();

        return view;
    }

    public void searchData(){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                kamusIndonesiaEnglishHelper = new KamusIndonesiaEnglishHelper(getContext());
                kamusAdapter = new KamusAdapter(getContext());

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(kamusAdapter);

                kamusIndonesiaEnglishHelper.open();

                ArrayList<KamusModel> kamusModels = kamusIndonesiaEnglishHelper.getDataByName(s);
                kamusIndonesiaEnglishHelper.close();
                kamusAdapter.addItem(kamusModels);
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setQuery("", false);
    }

}
