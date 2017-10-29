//package com.pfac.marcm.pizzafactory;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.pfac.marcm.pizzafactory.model.Pizza;
//
//import java.util.ArrayList;
//
///**
// * Created by marcm on 29/10/2017.
// */
//
//public class PizzaAdaptor extends BaseAdapter {
//    private LayoutInflater inflater = null;
//    Activity context;
//    ArrayList<Pizza> pizzas;
//
//
//    public PizzaAdaptor(Activity context, ArrayList<Pizza> pizzas) {
//        this.context = context;
//        this.pizzas = pizzas;
//        inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return pizzas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View itemView = convertView;
//        itemView - (itemView == null) ? inflater.inflate(R.layout.list_item, null) : itemView;
//    }
//}
