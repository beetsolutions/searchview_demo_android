package com.beetsolutions.searchview.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.beetsolutions.searchview.demo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Name> names = new ArrayList<>();
    private NameListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        names.add(new Name("Nadesh"));
        names.add(new Name("Justin"));
        names.add(new Name("Etukeni"));
        names.add(new Name("Betana"));
        names.add(new Name("Susan"));
        names.add(new Name("Balack"));
        names.add(new Name("Victor"));
        names.add(new Name("Lars"));

        adapter = new NameListAdapter(this, new Comparator<Name>() {
            @Override
            public int compare(Name name1, Name name2) {
                if (name1.getName() == null) {
                    return -1;
                }
                if (name2.getName() == null) {
                    return 1;
                }
                if (name1.getName().equals(name2.getName())) {
                    return 0;
                }
                return name1.getName().compareTo(name2.getName());
            }
        });
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapter);

        adapter.add(names);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private static List<Name> filter(List<Name> names, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Name> filteredList = new ArrayList<>();
        for (Name name : names) {
            final String text = name.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredList.add(name);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Name> filteredList = filter(names, newText);
        adapter.replaceAll(filteredList);
        return true;
    }
}
