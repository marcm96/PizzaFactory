package com.pfac.marcm.pizzafactory.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pfac.marcm.pizzafactory.LoginActivity;
import com.pfac.marcm.pizzafactory.MainActivity;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.adapters.PizzaListAdapter;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by marcm on 29/10/2017.
 */

public class PizzaListFragment extends Fragment {

    static List<Pizza> pizzas;
    DatabaseReference pizzasDB;

    String role;

    Button goToAddButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pizzasDB = FirebaseDatabase.getInstance().getReference("pizzas");
        pizzasDB.keepSynced(true);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        role = sharedPreferences.getString("role", null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pizzas = new ArrayList<>();

        View view = inflater.inflate(R.layout.pizza_list_layout, container, false);


        goToAddButton = view.findViewById(R.id.addPizza);
        if (Objects.equals(role, "guest")){
            goToAddButton.setVisibility(View.GONE);
        } else {
            goToAddButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create and populate list
        ListView pizzaListView = view.findViewById(R.id.pizzaListView);

        final PizzaListAdapter pizzaListAdapter = new PizzaListAdapter(getContext(), R.layout.pizza_list_item, pizzas);

        //take values from DB
        pizzasDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pizzas.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Pizza pizza = ds.getValue(Pizza.class);
                    if (pizza != null) {
                        pizza.setId(ds.getKey());
                    }
                    pizzas.add(pizza);
                }
                pizzaListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pizzaListView.setAdapter(pizzaListAdapter);
        pizzaListView.setDividerHeight(0);

        //set click on list item
        pizzaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PizzaDetailsFragment pizzaDetailsFragment = new PizzaDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("position", (int) l);

                pizzaDetailsFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mainFragment, pizzaDetailsFragment).addToBackStack(null)
                        .commit();
            }
        });

        //get button and select event on it for add pizza
        goToAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PizzaAddFragment pizzaAddFragment = new PizzaAddFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mainFragment, pizzaAddFragment).addToBackStack(null)
                        .commit();
            }
        });

    }

    public static List<Pizza> getPizzas() {
        return pizzas;
    }
}
