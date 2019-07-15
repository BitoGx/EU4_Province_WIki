package id.web.bitocode.eu4provincewiki;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

/*
 *
 * Tanggal Pengerjaan : July 10, 2019
 * NIM   : 10116073
 * Nama  : Muhammad Rizqi Zein Azis
 * Kelas : AKB-2 / IF-2
 *
 */

public class TerritoryActivity extends AppCompatActivity
{
  
  private DrawerLayout dl;
  private ActionBarDrawerToggle dt;
  private NavigationView nv;
  private Intent start;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_territory);
  
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("Territory");
  
    dl = findViewById(R.id.activity_territory);
    dt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
  
    dl.addDrawerListener(dt);
    dt.syncState();
  
    nv = findViewById(R.id.nvTerritory);
    nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
    {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
      {
        int id = menuItem.getItemId();
        switch (id)
        {
          case R.id.navhome:
            start = new Intent(TerritoryActivity.this, MainActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstatefav:
            start = new Intent(TerritoryActivity.this, FavouriteStateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navstate:
            start = new Intent(TerritoryActivity.this, StateActivity.class);
            startActivity(start);
            break;
        
          case R.id.navterritory:
            Toast.makeText(TerritoryActivity.this, "Territory",Toast.LENGTH_SHORT).show();
            break;
        
          case R.id.navterrain:
            start = new Intent(TerritoryActivity.this, TerrainActivity.class);
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
