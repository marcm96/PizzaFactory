package com.pfac.marcm.pizzafactory.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.pfac.marcm.pizzafactory.R;
import com.pfac.marcm.pizzafactory.model.Pizza;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcm on 29/10/2017.
 */

public class PizzaDetailsFragment extends Fragment {
    Pizza pizza;
    Pizza[] pizzas;
    Integer position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        if (bundle != null){
            position = bundle.getInt("position");
            pizzas = PizzaListFragment.getPizzas().toArray(new Pizza[0]);
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
        final EditText pizzaOrderTime = view.findViewById(R.id.timeOrderPizza);

        pizzaName.setText(pizza.getPizzaName());
        pizzaIngredients.setText(pizza.getIngredients());
        pizzaWeight.setText(pizza.getWeight());
        pizzaPrice.setText(String.valueOf(pizza.getPrice()));


        //time picker item
        pizzaOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        pizzaOrderTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        //graph item
        initGraph(view);


        //save button
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

        //place order button
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

    private void initGraph(View view) {

        GraphView graph = (GraphView) view.findViewById(R.id.graph);

        Map<Integer, Integer> nameMap = new HashMap<>();
        for(Pizza p: pizzas){
            Integer name = p.getPrice();
            if (nameMap.containsKey(name)) {
                int value = nameMap.get(name);
                value++;
                nameMap.put(name, value);
            } else {
                nameMap.put(name, 1);
            }
        }

        int sizeMap = nameMap.keySet().size();
        String[] horizontalLabels = new String[sizeMap];
        DataPoint[] dataPoints = new DataPoint[sizeMap];

        int i = 0;
        for (Integer name : nameMap.keySet()){
            dataPoints[i] = new DataPoint(i, nameMap.get(name).doubleValue());
            horizontalLabels[i] = String.valueOf(name);
            i++;
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(horizontalLabels);

        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
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
