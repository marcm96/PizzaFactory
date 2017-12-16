package com.pfac.marcm.pizzafactory.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.model.Pizza;

/**
 * Created by marcm on 16/12/2017.
 */

public class PizzaAddFragment extends Fragment {
    Pizza pizza;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pizza_item_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText pizzaName = view.findViewById(R.id.nameEditText);
        final EditText pizzaIngredients = view.findViewById(R.id.ingredientsEditText);
        final EditText pizzaWeight = view.findViewById(R.id.weightEditText);
        final EditText pizzaPrice = view.findViewById(R.id.priceEditText);

        Button addButton = view.findViewById(R.id.addNewPizzaButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pizzas");
                DatabaseReference pushedPostRef = databaseReference.push();
                String postId = pushedPostRef.getKey();

                pizza = new Pizza(postId, pizzaName.getText().toString(), pizzaIngredients.getText().toString(),
                        pizzaWeight.getText().toString(), Integer.parseInt(String.valueOf(pizzaPrice.getText())));

                databaseReference.child(postId).setValue(pizza);

//                getActivity().getSupportFragmentManager().popBackStack();
                getFragmentManager().popBackStackImmediate();
            }
        });
    }
}
