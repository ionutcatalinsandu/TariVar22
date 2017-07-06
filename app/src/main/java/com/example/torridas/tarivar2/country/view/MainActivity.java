package com.example.torridas.tarivar2.country.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.torridas.tarivar2.R;
import com.example.torridas.tarivar2.city.model.City;
import com.example.torridas.tarivar2.country.model.Country;
import com.example.torridas.tarivar2.country.adapter.CountryAdapter;
import com.example.torridas.tarivar2.database.DBHelper;
import com.example.torridas.tarivar2.util.TariVar22Id;
import com.example.torridas.tarivar2.city.view.Main2Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //views
    private Toolbar bara;
    private Typeface font1;
    private TextView t1;
    private ListView zonaLista;
    // database
    private DBHelper bazaDeDate;
    //vars
    private List<Country> listaTari;
    private CountryAdapter adaptorT;
    private Intent intentie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //action bar
        bara = (Toolbar)findViewById(R.id.toolbar);
        t1 = (TextView)findViewById(R.id.titlu);
        setSupportActionBar(bara);
        font1 = Typeface.createFromAsset(getAssets(),"Autumn Leaves.ttf");
        t1.setTypeface(font1);
        //vars
        listaTari = new ArrayList<>();

        //database

        bazaDeDate = DBHelper.getInstante(MainActivity.this);

        // views

        // test
        puneOrase();
        puneNisteTari();


        zonaLista = (ListView)findViewById(R.id.listaMea);
        listaTari = bazaDeDate.getAllCountries();
        adaptorT = new CountryAdapter(listaTari, MainActivity.this );
        zonaLista.setAdapter(adaptorT);

        zonaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentie =  new Intent(MainActivity.this, Main2Activity.class );
                intentie.putExtra(TariVar22Id.COUNTRY_ID, listaTari.get(position).getCountryID());
                startActivity(intentie);
            }
        });

    }

    ///test
    private void puneNisteTari(){
        for( int i = 0; i < 5; i++ ){
            bazaDeDate.addCountry(new Country(0, "Nume random" + i));
        }
    }
    //Test2 Pune cateva tari si cateva orase;
    private void puneOrase(){
        for( int i = 0; i < 5; i++ ){
            bazaDeDate.addCity(new City("Orasul numararul " + i, 0, 1));
        }
    }

    public void deLaCapat(View view){
        bazaDeDate.deleteAllCountries();
        bazaDeDate.deleteAllCities();
        listaTari = bazaDeDate.getAllCountries();
        adaptorT= new CountryAdapter(listaTari, MainActivity.this );
        zonaLista.setAdapter(adaptorT);
    }
}
