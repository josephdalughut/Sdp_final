package com.example.mycompany.sdp_final.gui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycompany.sdp_final.R;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView textSource;
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSource = (TextView) findViewById(R.id.sourceuri);
        ImageView iv = (ImageView) findViewById(R.id.view);
        //Load the background image

        try {
            InputStream bitmap=getAssets().open("image.jpg");
            bit= BitmapFactory.decodeStream(bitmap);
            iv.setImageBitmap(bit);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        iv.setOnTouchListener(new View.OnTouchListener(){

            //@Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        textSource.setText("ACTION_DOWN-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));
                        break;
                  case MotionEvent.ACTION_MOVE:
                        textSource.setText("ACTION_MOVE-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));
                        break;
                    case MotionEvent.ACTION_UP:

                        textSource.setText("ACTION_UP-" + x + " : " + y);
                        textSource.setBackgroundColor(
                                getProjectedColor((ImageView) v, bit, x, y));

                        break;
               }
    /*
     * Return 'true' to indicate that the event have been consumed.
     * If auto-generated 'false', your code can detect ACTION_DOWN only,
     * cannot detect ACTION_MOVE and ACTION_UP.
     */
                return true;
            }});
    }

    /*
 * Project position on ImageView to position on Bitmap
 * return the color on the position
 */
    private int getProjectedColor(ImageView iv, Bitmap bm, int x, int y) {
        if(x<0 || y<0 || x > iv.getWidth() || y > iv.getHeight()){
            //outside ImageView
            return android.R.color.background_light;
        }else{
            int projectedX = (int)((double)x * ((double)bm.getWidth()/(double)iv.getWidth()));
            int projectedY = (int)((double)y * ((double)bm.getHeight()/(double)iv.getHeight()));

            textSource.setText(x + ":" + y + "/" + iv.getWidth() + " : " + iv.getHeight() + "\n" +
                            projectedX + " : " + projectedY + "/" + bm.getWidth() + " : " + bm.getHeight()
            );

            return bm.getPixel(projectedX, projectedY);
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


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RQS_IMAGE1:
                    source = data.getData();
                    textSource.setText(source.toString());

                    try {
                        bitmapMaster = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(source));
                        imageResult.setImageBitmap(bitmapMaster);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
*/
}
