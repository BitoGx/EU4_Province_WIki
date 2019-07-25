package id.web.bitocode.eu4provincewiki;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import id.web.bitocode.eu4provincewiki.model.Eu4Model;


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
 * - Edit Layout item_provinces *part1
 *
 * CHANGELOG July 19, 2019
 * - Membuat fungsi untuk mengecek apakah ada internet atau tidak
 *
 * CHANGELOG July 25, 2019
 * - Rombak UX
 * - Rombak Database
 *
 */

public class MainActivity extends AppCompatActivity
{
  
  private DrawerLayout dl;
  private ActionBarDrawerToggle dt;
  private NavigationView nv;
  private Intent start;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Introduction");
  
    dl = findViewById(R.id.activity_main);
    dt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    nv = findViewById(R.id.nvMain);
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
        
          case R.id.navstatefav:
            start = new Intent(MainActivity.this, FavouriteStateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstate:
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
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {
    if(dt.onOptionsItemSelected(menuItem))
      return true;
    return super.onOptionsItemSelected(menuItem);
  }
}
