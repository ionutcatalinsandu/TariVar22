package com.example.torridas.tarivar2.city.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.torridas.tarivar2.R;
import com.example.torridas.tarivar2.city.model.City;
import com.example.torridas.tarivar2.city.adapter.CityAdapter;
import com.example.torridas.tarivar2.database.DBHelper;
import com.example.torridas.tarivar2.util.TariVar22Id;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private DBHelper dataBase;
    private List<City> listaOrase;
    private int ID;
    private CityAdapter adaptorC;
    private ListView zona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dataBase = DBHelper.getInstante(Main2Activity.this);
        listaOrase = new ArrayList<>();

        //getIntent

        Bundle info = getIntent().getExtras();
        if( info != null ){
            ID = info.getInt(TariVar22Id.COUNTRY_ID);
        }

        //makeList
        listaOrase = dataBase.getCittiesByCountry(ID);
        zona = (ListView)findViewById(R.id.listaMea2);
        adaptorC = new CityAdapter(listaOrase, Main2Activity.this );
        zona.setAdapter(adaptorC);
    }
}
