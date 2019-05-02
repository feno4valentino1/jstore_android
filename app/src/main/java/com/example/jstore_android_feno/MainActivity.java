package com.example.jstore_android_feno;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import com.android.volley.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Supplier> listSupplier = new ArrayList<>();
    private ArrayList<Item> listItem = new ArrayList<>();
    private HashMap<Supplier, ArrayList<Item>> childMapping = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshList();
        listAdapter = new MainListAdapter(listSupplier, childMapping);
        expListView.setAdapter(listAdapter);
    }

    protected void refreshList()
    {
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i=0; i<jsonResponse.length(); i++)
                    {
                        JSONObject item = jsonResponse.getJSONObject(i);

                        int itemID = item.getInt("id");
                        String itemName = item.getString("name");
                        int itemPrice = item.getInt("price");
                        String itemCategory = item.getString("category");
                        String itemStatus = item.getString("status");

                        JSONObject supplier = item.getJSONObject("supplier");

                        int supplierID = supplier.getInt("id");
                        String supplierName = supplier.getString("name");
                        String supplierEmail = supplier.getString("email");
                        String supplierPhoneNumber = supplier.getString("phoneNumber");

                        JSONObject location = supplier.getJSONObject("location");

                        String locationProvince = supplier.getString("province");
                        String locationDescription = supplier.getString("description");
                        String locationCity = supplier.getString("city");

                        Location locationTemp = new Location(locationProvince, locationDescription, locationCity);
                        Supplier supplierTemp = new Supplier(supplierID, supplierName, supplierEmail, supplierPhoneNumber, locationTemp);
                        Item itemTemp = new Item(itemID, itemName, itemPrice, itemCategory, itemStatus, supplierTemp);

                        listSupplier.add(supplierTemp);
                        listItem.add(itemTemp);
                        childMapping.put(listSupplier.get(i), listItem);
                    }
                }
                catch (JSONException e)
                {

                }
            }
        };
    }
}
