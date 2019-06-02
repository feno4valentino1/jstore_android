package com.example.jstore_android_feno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private int currentUserId;
    private String currentUserName;
    MainListAdapter mainListAdapter;
    ExpandableListView expListView;
    TextView tvTest;
    RecyclerView rvOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            currentUserId = extras.getInt("customer_id");
            currentUserName = extras.getString("customer_name");
        }

        expListView = findViewById(R.id.lvExp);
        tvTest = findViewById(R.id.tvTest);
        rvOrder = findViewById(R.id.rvOrder);
        refreshList();
        initRecyclerView();
        tvTest.setText("Welcome "+currentUserName+"!");
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View v, int i, int j, long l){
                Intent intent = new Intent(MainActivity.this, BuatPesananActivity.class);

                int item_id = childMapping.get(listSupplier.get(i)).get(j).getId();
                String item_name = childMapping.get(listSupplier.get(i)).get(j).getName();
                String item_category = childMapping.get(listSupplier.get(i)).get(j).getCategory();
                String item_status = childMapping.get(listSupplier.get(i)).get(j).getStatus();
                int item_price = childMapping.get(listSupplier.get(i)).get(j).getPrice();

                intent.putExtra("item_id", item_id);
                intent.putExtra("item_name", item_name);
                intent.putExtra("item_category", item_category);
                intent.putExtra("item_status", item_status);
                intent.putExtra("item_price", item_price);
                intent.putExtra("currentUserId", currentUserId);

                startActivity(intent);
                return false;
            }
        });
    }

    private void initRecyclerView(){
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int i=0;i<jsonResponse.length();i++)
                    {
                        JSONObject invoice = jsonResponse.getJSONObject(i);
                        int id = invoice.getInt("id");
                        String date = invoice.getString("date");
                        JSONArray item_json = invoice.getJSONArray("item");
                        ArrayList<String> items = new ArrayList<>();
                        for(int k=0; k <item_json.length();k++)
                        {
                            for(Item item : listItem)
                            {
                                if(item.getId() == Integer.valueOf(item_json.get(k).toString().trim()))
                                {
                                    items.add(item.getName());
                                }
                            }
                        }

                        String invoiceType = invoice.getString("invoiceType");
                        String invoiceStatus = invoice.getString("invoiceStatus");
                        Integer totalPrice = invoice.getInt("totalPrice");

                        if(invoiceStatus.equals("Paid"))
                        {
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus);
                            temp.setActive(false);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Unpaid"))
                        {
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus);
                            temp.setActive(false);
                            invoices.add(temp);
                        }else if(invoiceStatus.equals("Installment"))
                        {
                            int installmentPeriod = invoice.getInt("installmentPeriod");
                            int installmentPrice = invoice.getInt("installmentPrice");
                            Invoice temp = new Invoice(id, date, items, totalPrice, invoiceType, invoiceStatus, installmentPeriod, installmentPrice);
                            temp.setActive(false);
                            invoices.add(temp);
                        }
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        PesananFetchRequest request = new PesananFetchRequest(currentUserId,responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }

    protected void refreshList()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.d("new", "new");
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++)
                    {
                        JSONObject item = jsonResponse.getJSONObject(i);
                        JSONObject supplier = item.getJSONObject("supplier");
                        JSONObject location = supplier.getJSONObject("location");
                        String province = location.getString("province");
                        String description = location.getString("description");
                        String city = location.getString("city");
                        Location locationTemp = new Location(province, description, city);
                        int supplierId = supplier.getInt("id");
                        String supplierName = supplier.getString("name");
                        String supplierEmail = supplier.getString("email");
                        String supplierNumber = supplier.getString("phoneNumber");
                        Supplier supplierTemp = new Supplier(supplierId, supplierName, supplierEmail, supplierNumber, locationTemp);
                        Log.d("supplier",supplierTemp.getName());

                        if(listSupplier.size()>0)
                        {
                            for(Supplier object : listSupplier)
                            {
                                if(!(object.getId() == supplierTemp.getId()))
                                {
                                    listSupplier.add(supplierTemp);
                                }
                            }
                        }
                        else {
                            listSupplier.add(supplierTemp);
                        }

                        Log.d("supplier", String.valueOf(listSupplier.size()));
                        int itemId = item.getInt("id");
                        int itemPrice = item.getInt("price");
                        String itemName = item.getString("name");
                        String itemCategory = item.getString("category");
                        String itemStatus = item.getString("status");
                        Item itemTemp = new Item(itemId, itemName, itemPrice, itemCategory, itemStatus, supplierTemp);
                        Log.d("itemTemp",itemTemp.getName());

                        listItem.add(itemTemp);
                    }

                    for(Supplier supplier:listSupplier)
                    {
                        ArrayList<Item> tmp = new ArrayList<>();
                        for(Item item:listItem)
                        {
                            if(item.getSupplier().getId() == supplier.getId())
                            {
                                tmp.add(item);
                            }
                        }
                        childMapping.put(supplier,tmp);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Log.d("item", String.valueOf(listItem.size()));
                Log.d("test", String.valueOf(listSupplier.size()));

                ArrayList<String> listDataHeader= new ArrayList<>();
                HashMap<String, ArrayList<String>> listDataChild = new HashMap<>();
                for(Supplier supplier : listSupplier)
                {
                    listDataHeader.add(supplier.getName());
                    ArrayList<Item> tmpItem = childMapping.get(supplier);
                    ArrayList<String> item = new ArrayList<>();
                    for(Item i : tmpItem)
                    {
                        item.add(i.getName());
                    }
                    listDataChild.put(supplier.getName(), item);
                }

                mainListAdapter = new MainListAdapter(MainActivity.this, listDataHeader, listDataChild);
                Log.d("testtest", String.valueOf(listDataHeader));
                Log.d("testtest", String.valueOf(listDataChild));
                expListView.setAdapter(mainListAdapter);
            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
