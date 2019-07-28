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
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.web.bitocode.eu4provincewiki.adapter.AdapterProvinceRecyclerView;
import id.web.bitocode.eu4provincewiki.model.ProvinceModel;

public class ProvinceActivity extends AppCompatActivity
{
  
  private ActionBarDrawerToggle dt;
  private Intent start;
  
  //FIREBASE
  
  private List<ProvinceModel> daftarProvince;
  
  private ProgressDialog loading;
  private RecyclerView rv_Data;
  
  AdapterProvinceRecyclerView recycler;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_province);
  
    Intent getData = getIntent();
    final String State = getData.getStringExtra("State");
  
    if(getSupportActionBar() != null)
    {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(State);
    }
  
    DrawerLayout dl = findViewById(R.id.activity_province);
    dt = new ActionBarDrawerToggle(this, dl,R.string.Open,R.string.Close);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    NavigationView nv = findViewById(R.id.nvProvince);
    nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
    {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
      {
        int id = menuItem.getItemId();
        switch (id)
        {
          case R.id.navhome:
            start = new Intent(ProvinceActivity.this, MainActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstatefav:
            start = new Intent(ProvinceActivity.this, FavouriteStateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstate:
            start = new Intent(ProvinceActivity.this, StateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterritory:
            start = new Intent(ProvinceActivity.this, TerritoryActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterrain:
            start = new Intent(ProvinceActivity.this, TerrainActivity.class);
            startActivity(start);
            break;
        
          default:
            return true;
        }
        return true;
      }
    });
  
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    
    rv_Data = findViewById(R.id.rv_provData);
  
    loading = ProgressDialog.show(ProvinceActivity.this, null, "Loading State...", true, false);
    if(haveNetworkConnection())
    {
      database.child("Territory").child(State).orderByChild("Id").addValueEventListener(new ValueEventListener()
      {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
          daftarProvince = new ArrayList<>();
          for(DataSnapshot ds : dataSnapshot.getChildren())
          {
            ProvinceModel request = ds.getValue(ProvinceModel.class);
            request.setId(ds.child("Id").getValue(Long.class));
            request.setName(ds.child("Name").getValue(String.class));
            request.setOwner(ds.child("Owner").getValue(String.class));
            request.setTax(ds.child("Tax").getValue(Long.class));
            request.setProduction(ds.child("Production").getValue(Long.class));
            request.setManpower(ds.child("Manpower").getValue(Long.class));
            request.setReligion(ds.child("Religion").getValue(String.class));
            request.setCulture(ds.child("Culture").getValue(String.class));
            request.setTrade_Goods(ds.child("Trade_Goods").getValue(String.class));
            request.setTrade_Node(ds.child("Trade_Node").getValue(String.class));
            request.setPermanent_Modifiers(ds.child("Permanent_Modifiers").getValue(String.class));
            daftarProvince.add(request);
          }
          initRecyclerView();
          loading.dismiss();
        }
      
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
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
    recycler  = new AdapterProvinceRecyclerView(daftarProvince);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProvinceActivity.this);
    rv_Data.setLayoutManager(mLayoutManager);
    rv_Data.setItemAnimator(new DefaultItemAnimator());
    rv_Data.setAdapter(recycler);
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
