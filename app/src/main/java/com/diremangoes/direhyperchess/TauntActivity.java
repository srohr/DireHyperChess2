package diremangoes.com.direhyperchess2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TauntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_taunt, menu);
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
    public void receiveTaunt(View view, String sw)
    {
        Intent intent = new Intent(this,TauntActivity.class);
        ((TextView)findViewById (R.id.myTauntView)).setText (sw);
    }
    /* Called when the user presses the Taunt button*/
    public void sendNewTaunt(View view) throws IOException{
        Intent intent = new Intent(this,TauntActivity.class);
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("swears",
                        "raw", getPackageName()));
        ArrayList<String> swearList = new ArrayList<String>();
        ArrayList<String> nounList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String swear;
        while((swear= reader.readLine())!=null)
            swearList.add(swear);
        ins = getResources().openRawResource(
                getResources().getIdentifier("nounphrases",
                        "raw", getPackageName()));
        reader = new BufferedReader(new InputStreamReader(ins));
        while((swear= reader.readLine())!=null)
            nounList.add(swear);
        int count = swearList.size();
        int count1= nounList.size();
        Random r1 = new Random();
        int num = r1.nextInt(count);
        int option = r1.nextInt(9);
        String sw="",sw1;
        sw1 = swearList.get(num);
        int r2=0;
        String sw2="",sw3="";
        switch(option)
        {
            case 1: sw = sw1 +" you!!";
                break;
            case 2: r2 = r1.nextInt(count1);
                    sw2 = nounList.get(r2);
                    sw=sw1 + " your mother's "+ sw2;
                break;
            case 3:  r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                sw="You're tearing me apart "+sw2;
                break;
            case 4:r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                r2 = r1.nextInt(count1);
                sw3 = nounList.get(r2);
                sw="You want the "+sw3+"? You can't handle the "+sw2;
                break;
            case 0: r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                r2 = r1.nextInt(count1);
                sw3 = nounList.get(r2);
                sw="Your mother was a " +sw2+" and your father smelled of " + sw3;
                break;
            case 5: r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                r2 = r1.nextInt(count1);
                sw3 = nounList.get(r2);
                sw="Ha Ha " +sw2+". There is no such thing as a shitty " + sw3;
                break;
            case 6:r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                sw="Leave your "+sw1 +" comments in your "+sw2;
                break;
            case 7:r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                sw="Shall I compare thee to a "+sw1 +" "+sw2+"?";
                break;
            case 8:r2 = r1.nextInt(count1);
                sw2 = nounList.get(r2);
                sw="You know who is a bitch? You and you stupid "+sw2;
                break;
        }
        receiveTaunt(view,sw);
    }
}
