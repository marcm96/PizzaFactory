package com.pfac.marcm.pizzafactory.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.List;

/**
 * Created by marcm on 29/10/2017.
 */

public class PizzaDetailsFragment extends Fragment {
    Pizza pizza;
    Integer position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if (bundle != null){
            position = bundle.getInt("position");
            pizza = PizzaListFragment.getPizzas().get(position);
        }
        return inflater.inflate(R.layout.pizza_item_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pizzaName = view.findViewById(R.id.pizzaNameTextView);
        final EditText pizzaIngredients = view.findViewById(R.id.pizzaIngredientsEditText);
        final EditText pizzaWeight = view.findViewById(R.id.pizzaWeightEditText);
        final EditText pizzaPrice = view.findViewById(R.id.pizzaPriceEditText);

        pizzaName.setText(pizza.getPizzaName());
        pizzaIngredients.setText(pizza.getIngredients());
        pizzaWeight.setText(pizza.getWeight());
        pizzaPrice.setText(String.valueOf(pizza.getPrice()));

        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizza.setIngredients(pizzaIngredients.getText().toString());
                pizza.setWeight(pizzaWeight.getText().toString());
                pizza.setPrice(Integer.parseInt(String.valueOf(pizzaPrice.getText())));

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pizzas").child(position.toString());
                databaseReference.setValue(pizza);

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button placeOrderButton = view.findViewById(R.id.placeOrderButton);
        final EditText editText = view.findViewById(R.id.orderEditText);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = editText.getText().toString();
                openGmail(getActivity(), new String[] {"test@test.ro"}, content);
            }
        });
    }

    public static void openGmail(Activity activity, String[] email, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Order");
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        activity.startActivity(emailIntent);
    }

}
