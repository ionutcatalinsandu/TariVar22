package com.example.torridas.tarivar2.country.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.torridas.tarivar2.R;
import com.example.torridas.tarivar2.country.model.Country;

import java.util.List;

/**
 * Created by Torridas on 06-Jul-17.
 */

public class CountryAdapter extends BaseAdapter {

    private List<Country> lista;
    private Context myContext;
    private Country tara;

    public CountryAdapter(List<Country> lista, Context myContext) {
        this.lista = lista;
        this.myContext = myContext;
    }


    @Override
    public int getCount() {
        if ( lista == null)
            return 0;
        else
            return lista.size();
    }

    @Override
    public Object getItem(int position) {
        if( lista == null )
            return null;
        else
            return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewholder;
        final int currentposition = position;

        if( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int loc_id = R.layout.tara_item;
            view = inflater.inflate(loc_id, parent, false);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
        }
        else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();
        }
         tara =  (Country)(getItem(currentposition));
        viewholder.nume.setText(tara.getCountryID()+" "+tara.getName());
        viewholder.steag.setImageResource(R.drawable.marea);
        return view;
    }

    class ViewHolder {
        private ImageView steag;
        private TextView nume;


        public ViewHolder(View view) {
            steag = (ImageView)view.findViewById(R.id.steag);
            nume = (TextView)view.findViewById(R.id.id);
        }
    }
}