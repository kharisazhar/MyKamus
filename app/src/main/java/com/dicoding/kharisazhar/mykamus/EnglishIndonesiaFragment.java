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
import com.dicoding.kharisazhar.mykamus.model.KamusModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishIndonesiaFragment extends Fragment {

    private SearchView searchView;

    RecyclerView recyclerView;
    KamusAdapter kamusAdapter;
    KamusEnglishIndonesiaHelper kamusEnglishIndonesiaHelper;


    public EnglishIndonesiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_english_indonesia, container, false);

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

                kamusEnglishIndonesiaHelper = new KamusEnglishIndonesiaHelper(getContext());
                kamusAdapter = new KamusAdapter(getContext());

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(kamusAdapter);

                kamusEnglishIndonesiaHelper.open();

                ArrayList<KamusModel> kamusModels = kamusEnglishIndonesiaHelper.getDataByName(s);
                kamusEnglishIndonesiaHelper.close();
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
