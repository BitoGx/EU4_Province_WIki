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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.web.bitocode.eu4provincewiki.adapter.AdapterRegionRecyclerView;
import id.web.bitocode.eu4provincewiki.model.RegionsModel;


/*
 *
 * Tanggal Pengerjaan : July 10, 2019
 * NIM   : 10116073
 * Nama  : Muhammad Rizqi Zein Azis
 * Kelas : AKB-2 / IF-2
 *
 */

public class StateActivity extends AppCompatActivity implements AdapterRegionRecyclerView.onRegionListener
{
  
  private ActionBarDrawerToggle dt;
  private Intent start;
  
  //FIREBASE
  
  private List<RegionsModel> daftarRegion;
  
  private ProgressDialog loading;
  private RecyclerView rv_Data;
  
  AdapterRegionRecyclerView recycler;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_state);
  
    if(getSupportActionBar() != null)
    {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("Choose Region");
    }
    
  
    DrawerLayout dl = findViewById(R.id.activity_state);
    dt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
    
    Button btnRefresh = findViewById(R.id.btn_stateRefresh);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    NavigationView nv = findViewById(R.id.nvState);
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
        
          case R.id.navchoosebyregion:
            Toast.makeText(StateActivity.this, "Choose by Region",Toast.LENGTH_SHORT).show();
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
    
  
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    
    rv_Data = findViewById(R.id.rv_contData);
    
    loading = ProgressDialog.show(StateActivity.this, null, "Loading Region...", true, false);
    if(haveNetworkConnection())
    {
      database.child("Region").orderByChild("Region").addValueEventListener(new ValueEventListener()
      {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
          daftarRegion = new ArrayList<>();
          for(DataSnapshot ds : dataSnapshot.getChildren())
          {
            RegionsModel request = ds.getValue(RegionsModel.class);
            if (request != null)
            {
              request.setName(ds.child("Name").getValue(String.class));
              request.setRegion(ds.child("Region").getValue(String.class));
              request.setTotal_Tax(ds.child("Total_Tax").getValue(Long.class));
              request.setTotal_Production(ds.child("Total_Production").getValue(Long.class));
              request.setTotal_Manpower(ds.child("Total_Manpower").getValue(Long.class));
              request.setTotal_Territory(ds.child("Total_Territory").getValue(Long.class));
              daftarRegion.add(request);
            }
          }
          initRecyclerView();
          loading.dismiss();
        }
  
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
          System.out.println(databaseError.getDetails()+"I think we have an error : "+databaseError.getMessage());
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
  
  private void initRecyclerView()
  {
    recycler  = new AdapterRegionRecyclerView(daftarRegion,this);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StateActivity.this);
    rv_Data.setLayoutManager(mLayoutManager);
    rv_Data.setItemAnimator(new DefaultItemAnimator());
    rv_Data.setAdapter(recycler);
  }
  
  
  @Override
  public void onRegionClick(int position)
  {
    RegionsModel data = daftarRegion.get(position);
    Intent delivery = new Intent(this, RegionActivity.class);
    delivery.putExtra("Region", data.getName());
    startActivity(delivery);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu,menu);
    final MenuItem menuItem = menu.findItem(R.id.app_bar_search);
    final SearchView searchView = (SearchView) menuItem.getActionView();
    if(haveNetworkConnection())
    {
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
      {
        @Override
        public boolean onQueryTextSubmit(String query)
        {
          return false;
        }
    
        @Override
        public boolean onQueryTextChange(String newText)
        {
          recycler.getFilter().filter(newText);
          return false;
        }
      });
    }
    return true;
  }
}
