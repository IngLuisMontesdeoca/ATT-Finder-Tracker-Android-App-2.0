package io.github.ingluismontesdeoca.location.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.ingluismontesdeoca.location.R;
import io.github.ingluismontesdeoca.location.modelo.AddressResult;

/**
 * Created by Aministrador on 06/10/2016.
 */
public class addressResultAdapter extends ArrayAdapter<AddressResult> {
    private ArrayList<AddressResult> datos;
    public addressResultAdapter(Context context, ArrayList<AddressResult> datos) {
        super(context, R.layout.drawer_list_item, datos);
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.drawer_list_item,null);

        TextView _address = (TextView) item.findViewById(R.id.txtAddress);
        _address.setText(this.datos.get(position).getAddress());
        return item;
    }
}
