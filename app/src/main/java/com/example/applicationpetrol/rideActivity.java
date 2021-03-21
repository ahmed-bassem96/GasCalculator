package com.example.applicationpetrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class rideActivity extends AppCompatActivity {
ListView rideLV;
    tipAdapter adapter1;
    List<Ride> selectAllRides;
    RideDAO DataRideDAO;
    ArrayList<String>rides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        rideLV=findViewById(R.id.rideLV);

        rides=new ArrayList<>();
        getSupportActionBar().hide();
        DataRideDAO=RideDatabase.getInstance(this).rideDAO();

         selectAllRides= RideDatabase.getInstance(this).rideDAO().selectStartPlace();

    for (Ride selectAllRide : selectAllRides) {
        rides.add(selectAllRide.startPlace + " ----> " + selectAllRide.endPlace + " - " + selectAllRide.otherPlace+selectAllRide.cost);
    }


         adapter1=new tipAdapter(this, (ArrayList<Ride>) selectAllRides);
        rideLV.setAdapter(adapter1);
}



    public void clear(View view) {
        RideDatabase.getInstance(this).rideDAO().clearAll();
        selectAllRides.clear();
        adapter1.notifyDataSetChanged();
    }

    class tipAdapter extends ArrayAdapter<Ride>{
        private ArrayList<Ride> selectAllRides;

        public tipAdapter(@NonNull Context context, ArrayList<Ride>selectAllRides) {
             super(context,0,selectAllRides );
            this.selectAllRides = selectAllRides;
        }
         @NonNull
         @Override
         public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
             if(convertView==null)
                 convertView=getLayoutInflater().inflate(R.layout.shapelv,parent,false);


        TextView fText=convertView.findViewById(R.id.fText);
        TextView sText=convertView.findViewById(R.id.sText);
        TextView tText=convertView.findViewById(R.id.tText);
        TextView cText=convertView.findViewById(R.id.cText);
        ImageView imageView5=convertView.findViewById(R.id.imageView5);


        fText.setText((CharSequence)""+ getItem(position).getStartPlace());
        sText.setText((CharSequence)""+ getItem(position).getEndPlace());
        tText.setText((CharSequence)""+ getItem(position).getOtherPlace());
        cText.setText((CharSequence)""+ getItem(position).getCost());


             imageView5.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     DataRideDAO.deleteRide( getItem(position).id);
                    selectAllRides.remove(position);
                     notifyDataSetChanged();


                 }
             });







        return convertView;

    }     }
}

