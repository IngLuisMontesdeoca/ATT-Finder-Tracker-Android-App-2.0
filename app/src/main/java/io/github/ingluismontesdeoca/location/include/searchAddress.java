package io.github.ingluismontesdeoca.location.include;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.github.ingluismontesdeoca.location.R;

public class searchAddress extends AppCompatActivity {

    ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        this.searchResults = (ListView) findViewById(R.id.search_results);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String _query = intent.getStringExtra(SearchManager.QUERY);
            this.searchAddress(_query);
        }
    }

    private void searchAddress(String address){
        try{
            //Datos Fake
            ArrayList<String> searchResult = new ArrayList<>();
            searchResult.add("Camorones 25, Tabacalera, D.F.");
            searchResult.add("Santo Tomas de los platanos,Polanco D.F.");
            searchResult.add("San Pedro de los aguaros");
            searchResult.add("Exhacienda santa ines, Edo de Mexico");
            searchResult.add("Exhacienda santa ines, Edo de Mexico");
            searchResult.add("Exhacienda santa ines, Edo de Mexico");
            searchResult.add("Exhacienda santa ines, Edo de Mexico");
            searchResult.add("Exhacienda santa ines, Edo de Mexico");

            //Setear adaptador
            this.searchResults.setAdapter(new ArrayAdapter<String>(this,R.layout.search_address_listitem,searchResult));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
