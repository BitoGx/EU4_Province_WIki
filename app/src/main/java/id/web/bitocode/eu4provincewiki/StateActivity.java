package id.web.bitocode.eu4provincewiki;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.web.bitocode.eu4provincewiki.adapter.AdapterStateRecyclerView;
import id.web.bitocode.eu4provincewiki.model.Eu4Model;


/*
 *
 * Tanggal Pengerjaan : July 10, 2019
 * NIM   : 10116073
 * Nama  : Muhammad Rizqi Zein Azis
 * Kelas : AKB-2 / IF-2
 *
 */

public class StateActivity extends AppCompatActivity
{
  
  private DrawerLayout dl;
  private ActionBarDrawerToggle dt;
  private NavigationView nv;
  private Intent start;
  
  //FIREBASE
  
  private DatabaseReference database;
  private DatabaseReference yourdatabase;
  
  private List<Eu4Model> daftarReq;
  
  private ProgressDialog loading;
  private RecyclerView recyclerView;
  
  private Button btnRefresh;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_state);
  
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("State");
  
    dl = findViewById(R.id.activity_state);
    dt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
    
    btnRefresh = findViewById(R.id.refreshButton);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    nv = findViewById(R.id.nvState);
    nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
    {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
      {
        int id = menuItem.getItemId();
        switch (id)
        {
          case R.id.navhome:
            start = new Intent(StateActivity.this, MainActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstatefav:
            start = new Intent(StateActivity.this, FavouriteStateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstate:
            Toast.makeText(StateActivity.this, "States",Toast.LENGTH_SHORT).show();
            break;
        
          case R.id.navterritory:
            start = new Intent(StateActivity.this, TerritoryActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterrain:
            start = new Intent(StateActivity.this, TerrainActivity.class);
            startActivity(start);
            break;
        
          default:
            return true;
        }
        return true;
      }
    });
    
  
    database = FirebaseDatabase.getInstance().getReference();
    yourdatabase = database.child("User");
    Eu4Model eu4Model = new Eu4Model();
    yourdatabase.setValue(eu4Model);
  
    recyclerView = findViewById(R.id.rv_province);
    
    loading = ProgressDialog.show(StateActivity.this, null, "Loading...?", true, false);
    if(haveNetworkConnection())
    {
      database.child("Provinces").addValueEventListener(new ValueEventListener()
      {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
          daftarReq = new ArrayList<>();
          for(DataSnapshot ds : dataSnapshot.getChildren())
          {
            Eu4Model requests = ds.getValue(Eu4Model.class);
            requests.setContinent(ds.child("Continent").getValue(String.class));
            requests.setCulture(ds.child("Culture").getValue(String.class));
            requests.setId(ds.child("Id").getValue(Long.class));
            requests.setManpower(ds.child("Manpower").getValue(Long.class));
            requests.setOwner(ds.child("Owner").getValue(String.class));
            requests.setPermanent_Modifiers(ds.child("Permanent_Modifiers").getValue(String.class));
            requests.setProduction(ds.child("Production").getValue(Long.class));
            requests.setRegion(ds.child("Region").getValue(String.class));
            requests.setReligion(ds.child("Religion").getValue(String.class));
            requests.setState(ds.child("State").getValue(String.class));
            requests.setTax(ds.child("Tax").getValue(Long.class));
            requests.setTerritory(ds.child("Territory").getValue(String.class));
            requests.setTrade_Goods(ds.child("Trade_Goods").getValue(String.class));
            requests.setTrade_Node(ds.child("Trade_Node").getValue(String.class));
            daftarReq.add(requests);
          }
          AdapterStateRecyclerView recycler  = new AdapterStateRecyclerView(daftarReq);
          RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StateActivity.this);
          recyclerView.setLayoutManager(mLayoutManager);
          recyclerView.setItemAnimator(new DefaultItemAnimator());
          recyclerView.setAdapter(recycler);
          recyclerView.setHasFixedSize(true);
          loading.dismiss();
        }
    
        @Override
        public void onCancelled(DatabaseError databaseError)
        {
          System.out.println(databaseError.getDetails()+"Please Try Again"+databaseError.getMessage());
          loading.dismiss();
        }
      });
    }
    else
    {
      Toast.makeText(this, "Turn on Wi-Fi or Mobile Network on", Toast.LENGTH_SHORT).show();
      loading.dismiss();
    }
    
    btnRefresh.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        finish();
        startActivity(getIntent());
      }
    });
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {
    if(dt.onOptionsItemSelected(menuItem))
      return true;
    return super.onOptionsItemSelected(menuItem);
  }
  
  private boolean haveNetworkConnection()
  {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;
    
    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    for (NetworkInfo ni : netInfo)
    {
      if (ni.getTypeName().equalsIgnoreCase("WIFI"))
        if (ni.isConnected())
          haveConnectedWifi = true;
      if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
        if (ni.isConnected())
          haveConnectedMobile = true;
    }
    return haveConnectedWifi || haveConnectedMobile;
  }
  
}
