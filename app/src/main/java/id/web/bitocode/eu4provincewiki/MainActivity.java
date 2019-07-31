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
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/*
 *
 * Tanggal Pengerjaan : July 10, 2019
 * NIM   : 10116073
 * Nama  : Muhammad Rizqi Zein Azis
 * Kelas : AKB-2 / IF-2
 *
 * CHANGELOG July 10, 2019
 * - Membuat semua kemungkinan halaman yang akan disediakan
 * - Membuat Navigation Drawer untuk berpindah halaman
 * - Membuat fungsi pindah halaman
 * - Membuat style baru
 *
 * CHANGELOG July 14, 2019
 * - Membuat Dimens
 * - Implementasi Firebase *Failed
 *
 * CHANGELOG July 15, 2019
 * - Implementasi Firebase *Success
 * - Integrasi Data firebase ke RecyclerView
 * - Edit Layout item_provinces
 *
 * CHANGELOG July 19, 2019
 * - Membuat fungsi untuk mengecek apakah ada internet atau tidak
 *
 * CHANGELOG July 25, 2019
 * - Rombak UX
 * - Rombak Database
 *
 * CHANGELOG July 27, 2019
 * - Implementasi Search di StateActivity
 * - Implementasi Search di RegionActivity
 *
 * CHANGELOG July 28, 2019
 * - Implementasi onClick pada Item RegionActivty
 * - Pembuatan ProvinceActivity baru untuk menerima data dari RegionActivity
 * - Rename AdapterProvinceRecyleView menjadi AdapterStateRecyleView
 * - Rename item_provinces menjadi item_states
 * - Pembuatan item_province untuk menampilkan data province/territory
 * - Mengubah awalan id item_states dari prov menjadi stat
 *
 * CHANGELOG July 31,2019
 * - Update note in main will get the data from database
 * - Deciding territory will show in territory activity
 * - Updating Territory Activity
 */

public class MainActivity extends AppCompatActivity
{
  
  private ActionBarDrawerToggle dt;
  private Intent start;
  
  
  //FIREBASE
  
  private ProgressDialog loading;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  
    if(getSupportActionBar() != null)
    {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("Introduction");
    }
    
    DrawerLayout dl = findViewById(R.id.activity_main);
    dt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    NavigationView nv = findViewById(R.id.nvMain);
    nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
    {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
      {
        int id = menuItem.getItemId();
        switch (id)
        {
          case R.id.navhome:
            Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();
            break;

          case R.id.navchoosebyregion:
            start = new Intent(MainActivity.this, StateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterritory:
            start = new Intent(MainActivity.this, TerritoryActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterrain:
            start = new Intent(MainActivity.this, TerrainActivity.class);
            startActivity(start);
            break;
        
          default:
            return true;
        }
        return true;
      }
    });
    
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
  
    final TextView tv_note = findViewById(R.id.tv_mainAnnouncement);
  
    loading = ProgressDialog.show( MainActivity.this, null, "Loading Notes...", true, false);
    if(haveNetworkConnection())
    {
      database.child("Announcement").child("Notes").addValueEventListener(new ValueEventListener()
      {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
          String request = dataSnapshot.getValue(String.class);
          tv_note.setText(request);
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
