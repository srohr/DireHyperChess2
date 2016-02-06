package com.diremangoes.direhyperchess;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView [][] GUIBoard = new ImageView[8][8];
        {
            GUIBoard[0][0] = (ImageView) findViewById(R.id.cellA1);
            GUIBoard[1][0] = (ImageView) findViewById(R.id.cellA2);
            GUIBoard[2][0] = (ImageView) findViewById(R.id.cellA3);
            GUIBoard[3][0] = (ImageView) findViewById(R.id.cellA4);
            GUIBoard[4][0] = (ImageView) findViewById(R.id.cellA5);
            GUIBoard[5][0] = (ImageView) findViewById(R.id.cellA6);
            GUIBoard[6][0] = (ImageView) findViewById(R.id.cellA7);
            GUIBoard[7][0] = (ImageView) findViewById(R.id.cellA8);
            GUIBoard[0][1] = (ImageView) findViewById(R.id.cellB1);
            GUIBoard[1][1] = (ImageView) findViewById(R.id.cellB2);
            GUIBoard[2][1] = (ImageView) findViewById(R.id.cellB3);
            GUIBoard[3][1] = (ImageView) findViewById(R.id.cellB4);
            GUIBoard[4][1] = (ImageView) findViewById(R.id.cellB5);
            GUIBoard[5][1] = (ImageView) findViewById(R.id.cellB6);
            GUIBoard[6][1] = (ImageView) findViewById(R.id.cellB7);
            GUIBoard[7][1] = (ImageView) findViewById(R.id.cellB8);
            GUIBoard[0][2] = (ImageView) findViewById(R.id.cellC1);
            GUIBoard[1][2] = (ImageView) findViewById(R.id.cellC2);
            GUIBoard[2][2] = (ImageView) findViewById(R.id.cellC3);
            GUIBoard[3][2] = (ImageView) findViewById(R.id.cellC4);
            GUIBoard[4][2] = (ImageView) findViewById(R.id.cellC5);
            GUIBoard[5][2] = (ImageView) findViewById(R.id.cellC6);
            GUIBoard[6][2] = (ImageView) findViewById(R.id.cellC7);
            GUIBoard[7][2] = (ImageView) findViewById(R.id.cellC8);
            GUIBoard[0][3] = (ImageView) findViewById(R.id.cellD1);
            GUIBoard[1][3] = (ImageView) findViewById(R.id.cellD2);
            GUIBoard[2][3] = (ImageView) findViewById(R.id.cellD3);
            GUIBoard[3][3] = (ImageView) findViewById(R.id.cellD4);
            GUIBoard[4][3] = (ImageView) findViewById(R.id.cellD5);
            GUIBoard[5][3] = (ImageView) findViewById(R.id.cellD6);
            GUIBoard[6][3] = (ImageView) findViewById(R.id.cellD7);
            GUIBoard[7][3] = (ImageView) findViewById(R.id.cellD8);
            GUIBoard[0][4] = (ImageView) findViewById(R.id.cellE1);
            GUIBoard[1][4] = (ImageView) findViewById(R.id.cellE2);
            GUIBoard[2][4] = (ImageView) findViewById(R.id.cellE3);
            GUIBoard[3][4] = (ImageView) findViewById(R.id.cellE4);
            GUIBoard[4][4] = (ImageView) findViewById(R.id.cellE5);
            GUIBoard[5][4] = (ImageView) findViewById(R.id.cellE6);
            GUIBoard[6][4] = (ImageView) findViewById(R.id.cellE7);
            GUIBoard[7][4] = (ImageView) findViewById(R.id.cellE8);
            GUIBoard[0][5] = (ImageView) findViewById(R.id.cellF1);
            GUIBoard[1][5] = (ImageView) findViewById(R.id.cellF2);
            GUIBoard[2][5] = (ImageView) findViewById(R.id.cellF3);
            GUIBoard[3][5] = (ImageView) findViewById(R.id.cellF4);
            GUIBoard[4][5] = (ImageView) findViewById(R.id.cellF5);
            GUIBoard[5][5] = (ImageView) findViewById(R.id.cellF6);
            GUIBoard[6][5] = (ImageView) findViewById(R.id.cellF7);
            GUIBoard[7][5] = (ImageView) findViewById(R.id.cellF8);
            GUIBoard[0][6] = (ImageView) findViewById(R.id.cellG1);
            GUIBoard[1][6] = (ImageView) findViewById(R.id.cellG2);
            GUIBoard[2][6] = (ImageView) findViewById(R.id.cellG3);
            GUIBoard[3][6] = (ImageView) findViewById(R.id.cellG4);
            GUIBoard[4][6] = (ImageView) findViewById(R.id.cellG5);
            GUIBoard[5][6] = (ImageView) findViewById(R.id.cellG6);
            GUIBoard[6][6] = (ImageView) findViewById(R.id.cellG7);
            GUIBoard[7][6] = (ImageView) findViewById(R.id.cellG8);
            GUIBoard[0][7] = (ImageView) findViewById(R.id.cellH1);
            GUIBoard[1][7] = (ImageView) findViewById(R.id.cellH2);
            GUIBoard[2][7] = (ImageView) findViewById(R.id.cellH3);
            GUIBoard[3][7] = (ImageView) findViewById(R.id.cellH4);
            GUIBoard[4][7] = (ImageView) findViewById(R.id.cellH5);
            GUIBoard[5][7] = (ImageView) findViewById(R.id.cellH6);
            GUIBoard[6][7] = (ImageView) findViewById(R.id.cellH7);
            GUIBoard[7][7] = (ImageView) findViewById(R.id.cellH8);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
