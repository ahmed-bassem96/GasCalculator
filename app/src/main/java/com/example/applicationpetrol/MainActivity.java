package com.example.applicationpetrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements LocationListener {
    EditText placeText, placeText2;
    String startPlace, endPlace;
    TextView resultText, costText;
    LocationManager manager;
    double latitude, longitude;
    String addressLine;
    Button costButton;
    Spinner petprice, carType;
    View view;
    SharedPreferences pref;
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();
    double cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placeText = findViewById(R.id.placeText);
        placeText2 = findViewById(R.id.placeText2);
        resultText = findViewById(R.id.resultText);
        costButton = findViewById(R.id.costButton);
        pref = getPreferences(MODE_PRIVATE);
        petprice = findViewById(R.id.petprice);
        carType = findViewById(R.id.carType);
        costText = findViewById(R.id.costText);
        Collections.addAll(prices, "select car's petrol", "Petrol 80", "Petrol 92", "Petrol 95");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, prices);
        petprice.setAdapter(adapter);
        //carType
        Collections.addAll(type, "select car's cylinder", "4 cylinder", "6 cylinder");
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, type);
        carType.setAdapter(adapter2);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "please grant permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            addressList.get(0).getAddressLine(0);
        } catch (IOException e) {
            Toast.makeText(this, "error in address", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void calc(View view) {


        try {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] prem = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, prem, 1);
        } else {
            manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        }

        String address1 = placeText.getText().toString() + "egypt";
        String address2 = placeText2.getText().toString() + "egypt";
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        Location loc1 = null, loc2 = null;
        try {
            addresses = geocoder.getFromLocationName(address1, 1);
            if (addresses.size() > 0) {
                loc1 = new Location(" ");
                loc1.setLatitude(addresses.get(0).getLatitude());
                loc1.setLongitude(addresses.get(0).getLongitude());
            }
            addresses = geocoder.getFromLocationName(address2, 1);
            if (addresses.size() > 0) {
                loc2 = new Location("");
                loc2.setLatitude(addresses.get(0).getLatitude());
                loc2.setLongitude(addresses.get(0).getLongitude());
            }

            startPlace = placeText.getText().toString();
            endPlace = placeText2.getText().toString();
            if (startPlace.equalsIgnoreCase("")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(placeText);
                Toast.makeText(this, "please enter start place", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endPlace.equalsIgnoreCase("")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(placeText2);
                Toast.makeText(this, "please enter end place", Toast.LENGTH_SHORT).show();
                return;
            }
            if (petprice.getSelectedItem().toString().equals("select car's petrol")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(petprice);
                Toast.makeText(this, "please select diesel type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (loc1 != null && loc2 != null) {
                double distance = loc1.distanceTo(loc2);
                distance /= 1000;

                if (petprice.getSelectedItem().toString().equals("Petrol 80") && carType.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) * 6.25;
                } else if (petprice.getSelectedItem().toString().equals("Petrol 92") && carType.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) * 7.50;
                } else if (petprice.getSelectedItem().toString().equals("Petrol 95") && carType.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) * 8.50;
                } else if (petprice.getSelectedItem().toString().equals("Petrol 80") && carType.getSelectedItem().toString().equals("6 cylinder")) {
                    cost = (distance * 0.12) * 6.25;
                } else if (petprice.getSelectedItem().toString().equals("Petrol 92") && carType.getSelectedItem().toString().equals("6 cylinder")) {
                    cost = (distance * 0.12) * 7.50;
                } else if (petprice.getSelectedItem().toString().equals("Petrol 95") && carType.getSelectedItem().toString().equals("6 cylinder")) {

                    cost = (distance * 0.12) * 8.50;
                }
                resultText.setText("\n Distance : " + distance + " KM" + "\n\n cost : " + cost + "LE");
                costText.setText("" + cost + "LE");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        } catch (Exception e) {
            Toast.makeText(this, "check setting  your Location", Toast.LENGTH_SHORT).show();
        }
    }


    Ride r;

    public void insert(View view) {
        if (!placeText.getText().toString().equalsIgnoreCase("") && !placeText2.getText().toString().equalsIgnoreCase("")) {
            r = new Ride();
            r.startPlace = placeText.getText().toString();
            r.endPlace = placeText2.getText().toString();
            r.cost = costText.getText().toString();
            long result = RideDatabase.getInstance(this).rideDAO().insertRide(r);
            if (result > 0)
                Toast.makeText(this, "ride saved", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "problem saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "enter two places", Toast.LENGTH_SHORT).show();
        }
    }

    public void map(View view) {

        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + String.valueOf(placeText2.getText().toString())));
        in.setPackage("com.google.android.apps.maps");
        startActivity(in);
    }




    public void speach(View view) {
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT,"text");
        startActivityForResult(in,1);
    }

    public void speach2(View view) {
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT,"text");
        startActivityForResult(in,2);
    }







    ArrayList<String> stringArrayExtra;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        stringArrayExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            placeText.setText(stringArrayExtra.get(0));
            stringArrayExtra = null;

        } if (requestCode == 2 && resultCode == RESULT_OK)
        {
            stringArrayExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            placeText2.setText(stringArrayExtra.get(0));
            stringArrayExtra = null;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}





