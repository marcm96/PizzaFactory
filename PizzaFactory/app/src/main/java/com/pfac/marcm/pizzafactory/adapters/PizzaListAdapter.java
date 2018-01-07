package com.pfac.marcm.pizzafactory.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.List;
import java.util.Objects;

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
        @SuppressLint("ViewHolder") View view = View.inflate(context, resource, null);

        TextView pizzaName = view.findViewById(R.id.pizzaName);
        TextView pizzaIngredients = view.findViewById(R.id.pizzaIngredients);
        TextView pizzaWeight = view.findViewById(R.id.pizzaWeight);
        TextView pizzaPrice = view.findViewById(R.id.pizzaPrice);
        Button deleteItemButton = view.findViewById(R.id.removePizza);

        String role;
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        role = sharedPreferences.getString("role", null);

        if (Objects.equals(role, "guest")){
            deleteItemButton.setVisibility(View.GONE);
        } else {
            deleteItemButton.setVisibility(View.VISIBLE);
        }

        Pizza currentPizza = pizzaList.get(position);
        final String postId = currentPizza.getId();
        pizzaName.setText(currentPizza.getPizzaName());
        pizzaIngredients.setText(currentPizza.getIngredients());
        pizzaWeight.setText(currentPizza.getWeight());
        pizzaPrice.setText(String.valueOf(currentPizza.getPrice()));

        //delete Button
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pizzas").child(postId);
                                databaseReference.removeValue();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        })
                        .show();
            }
        });

        return view;
    }
}
