package com.pfac.marcm.pizzafactory.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.List;

/**
 * Created by marcm on 29/10/2017.
 */

public class PizzaListAdapter extends ArrayAdapter<Pizza> {
    private Context context;
    private int resource;
    private List<Pizza> pizzaList;

    public PizzaListAdapter(@NonNull Context context, int resource, @NonNull List<Pizza> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.pizzaList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(context, resource, null);

        TextView pizzaName = view.findViewById(R.id.pizzaName);
        TextView pizzaIngredients = view.findViewById(R.id.pizzaIngredients);
        TextView pizzaWeight = view.findViewById(R.id.pizzaWeight);
        TextView pizzaPrice = view.findViewById(R.id.pizzaPrice);

        Pizza currentPizza = pizzaList.get(position);
        pizzaName.setText(currentPizza.getPizzaName());
        pizzaIngredients.setText(currentPizza.getIngredients());
        pizzaWeight.setText(currentPizza.getWeight());
        pizzaPrice.setText(String.valueOf(currentPizza.getPrice()));

        return view;
    }
}
