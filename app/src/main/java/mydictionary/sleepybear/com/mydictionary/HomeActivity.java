/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary;

import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mydictionary.sleepybear.com.mydictionary.db.DictionaryHelper;
import mydictionary.sleepybear.com.mydictionary.model.DictionaryModel;
import mydictionary.sleepybear.com.mydictionary.utils.AppPreference;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {
    private AppPreference appPreference;
    private DictionaryHelper dictionaryHelper;
    private ArrayList<DictionaryModel> dictionaryList = new ArrayList<>();

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        // ID to EN item
        appPreference = new AppPreference(this);
        appPreference.setENtoID(false);

        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        dictionaryHelper = new DictionaryHelper(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.en_to_id_item) {
            appPreference.setENtoID(true);
        } else if (id == R.id.id_to_en_item) {
            appPreference.setENtoID(false);
        } else if (id == R.id.nav_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setMessage(R.string.dialog_about_content)
                    .setTitle(R.string.dialog_about_title);
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Log.d("ROS", String.valueOf(text));
        searchWord(String.valueOf(text));
        searchBar.disableSearch();
    }

    private void searchWord(String text) {
        try {
            dictionaryHelper.open();
            dictionaryList = dictionaryHelper.getValueByKeyword(text, appPreference.isENToID());
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            dictionaryHelper.close();
        }
        if(dictionaryList.size()>=1){
            Log.d("ROS",dictionaryList.get(0).getValue());
            Toast.makeText(HomeActivity.this,dictionaryList.get(0).getValue() ,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeActivity.this, getResources().getString(R.string.keyword_not_found, text),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }
}
