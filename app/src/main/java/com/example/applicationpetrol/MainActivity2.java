package com.example.applicationpetrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity2 extends AppCompatActivity  implements LocationListener {
   EditText placeText3, placeText4,placeText5;
    String firstPlace, secondPlace,thirdPlace;
    TextView resultText2,costText2;
    Button costButton2;
    Spinner petprice2,carType2;
    String addressLine;
    LocationManager manager;
    double latitude, longitude,cost;
    String address1,address2,address3;
    ArrayList<String> prices2 = new ArrayList<>();
    ArrayList<String> type2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        placeText3=findViewById(R.id.placeText3);
        placeText4=findViewById(R.id.placeText4);
        placeText5=findViewById(R.id.placeText5);
        resultText2=findViewById(R.id.resultText2);
        costButton2=findViewById(R.id.costButton2);
        petprice2=findViewById(R.id.petprice2);
        carType2=findViewById(R.id.carType2);
        costText2=findViewById(R.id.costText2);

        Collections.addAll(prices2, "select  car's petrol", "Petrol 80", "Petrol 92", "Petrol 95");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, prices2);
        petprice2.setAdapter(adapter);
        //carType
        Collections.addAll(type2, "select  car's cylinder", "4 cylinder", "6 cylinder");
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, type2);
        carType2.setAdapter(adapter2);
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
        // resultText.setText("lat: "+latitude+" long: "+longitude);
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String addressLine = addressList.get(0).getAddressLine(0);
            //  resultText.append("\n"+addressLine);
        } catch (IOException e) {
            Toast.makeText(this, "error in address", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void cost(View view) {

        try {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String []prem={Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this,prem,1);
        }else {
            manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        }

        address1 = placeText3.getText().toString() + "egypt";
        address2 = placeText4.getText().toString() + "egypt";
        address3 = placeText5.getText().toString() + "egypt";
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        Location loc1 = null, loc2 = null,loc3=null;
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
            addresses = geocoder.getFromLocationName(address3, 1);
            if (addresses.size() > 0) {
                loc3 = new Location("");
                loc3.setLatitude(addresses.get(0).getLatitude());
                loc3.setLongitude(addresses.get(0).getLongitude());
            }
            firstPlace = placeText3.getText().toString();
            secondPlace = placeText4.getText().toString();
            thirdPlace=placeText5.getText().toString();

            if (firstPlace.equalsIgnoreCase("")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(placeText3);
                Toast.makeText(this, "please enter start place", Toast.LENGTH_SHORT).show();
                return;
            }
            if (secondPlace.equalsIgnoreCase("")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(placeText4);
                Toast.makeText(this, "please enter end place", Toast.LENGTH_SHORT).show();
                return;
            }
            if (thirdPlace.equalsIgnoreCase("")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(placeText5);
                Toast.makeText(this, "please enter  end place", Toast.LENGTH_SHORT).show();
                return;
            }
            if (petprice2.getSelectedItem().toString().equals("select  car's petrol")) {
                YoYo.with(Techniques.Shake).duration(600).repeat(2).playOn(petprice2);
                Toast.makeText(this, "please select diesel type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (loc1 != null && loc2 != null && loc3 !=null) {
                double distance,distance1,distance2;
                distance1 = loc1.distanceTo(loc2);
                distance2=loc2.distanceTo(loc3);
                distance=distance1+distance2;
                distance /= 1000;
                if (petprice2.getSelectedItem().toString().equals("Petrol 80")&& carType2.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) * 6.25;
                }
               else if (petprice2.getSelectedItem().toString().equals("Petrol 92")&& carType2.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) *7.50;
                }
              else if (petprice2.getSelectedItem().toString().equals("Petrol 95")&&carType2.getSelectedItem().toString().equals("4 cylinder")) {
                    cost = (distance * 0.10) * 8.50;
                }
                else  if (petprice2.getSelectedItem().toString().equals("Petrol 80")&& carType2.getSelectedItem().toString().equals("6 cylinder")) {
                    cost =  (distance * 0.12)*6.25;
                }
                else if (petprice2.getSelectedItem().toString().equals("Petrol 92")&& carType2.getSelectedItem().toString().equals("6 cylinder")) {
                    cost = (distance * 0.12) * 7.50;
                }
                else  if (petprice2.getSelectedItem().toString().equals("Petrol 95")&&carType2.getSelectedItem().toString().equals("6 cylinder")) {
                    cost = (distance * 0.12) * 8.50;
                }
                        resultText2.setText("\n Distance : " + distance + " KM" + "\n\n cost: " + cost + " LE");
                        costText2.setText(""+cost+"LE");
                    }
            }

            catch(IOException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            Toast.makeText(this, "check setting  your Location", Toast.LENGTH_SHORT).show();

        }
        }

    public void add(View view) {
        if(!placeText3.getText().toString().equalsIgnoreCase("")&&!placeText4.getText().toString().equalsIgnoreCase("")&&!placeText5.getText().toString().equalsIgnoreCase("")){

            Ride r = new Ride();
            r.startPlace = placeText3.getText().toString();
            r.endPlace = placeText4.getText().toString();
            r.otherPlace = placeText5.getText().toString();
            r.cost=costText2.getText().toString();

            long result2 = RideDatabase.getInstance(this).rideDAO().insertRide(r);
            if (result2 > 0)
                Toast.makeText(this, "ride saved", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "problem saving", Toast.LENGTH_SHORT).show();
    }
   else{
            Toast.makeText(this, "please, enter three places", Toast.LENGTH_SHORT).show();}}

    public void delete2(View view)
    {
      //  RideDatabase.getInstance(this).rideDAO().deleteRide();
    }

    public void map(View view) {
        Intent in=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+String.valueOf(placeText5.getText().toString())));
        in.setPackage("com.google.android.apps.maps");
        startActivity(in);
    }



    public void speach2(View view) {
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT,"text");
        startActivityForResult(in,1);
    }

    public void speach3(View view) {
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT,"text");
        startActivityForResult(in,2);
    }

    public void speach4(View view) {
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT,"text");
        startActivityForResult(in,3);
    }
    ArrayList<String> stringArrayExtra;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        stringArrayExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            placeText3.setText(stringArrayExtra.get(0));
            stringArrayExtra = null;

        } if (requestCode == 2 && resultCode == RESULT_OK)
        {
            stringArrayExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            placeText4.setText(stringArrayExtra.get(0));
            stringArrayExtra = null;

        }if (requestCode == 3 && resultCode == RESULT_OK)
        {
            stringArrayExtra = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            placeText5.setText(stringArrayExtra.get(0));
            stringArrayExtra = null;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
