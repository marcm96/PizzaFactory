package com.pfac.marcm.pizzafactory.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.adapters.PizzaListAdapter;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcm on 29/10/2017.
 */

public class PizzaListFragment extends Fragment {

    static List<Pizza> pizzas;
    final DatabaseReference pizzasDB = FirebaseDatabase.getInstance().getReference("pizzas");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pizza_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView pizzaListView = view.findViewById(R.id.pizzaListView);

        this.pizzas = new ArrayList<>();
        final PizzaListAdapter pizzaListAdapter = new PizzaListAdapter(getContext(), R.layout.pizza_list_item, pizzas);

        pizzasDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Pizza pizza = ds.getValue(Pizza.class);
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
    }

    public static List<Pizza> getPizzas() {
        return pizzas;
    }
}
