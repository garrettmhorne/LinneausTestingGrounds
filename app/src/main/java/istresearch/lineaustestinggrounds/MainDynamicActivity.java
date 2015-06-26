package istresearch.lineaustestinggrounds;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainDynamicActivity extends ActionBarActivity implements View.OnTouchListener
{
    RelativeLayout canvas;
    ArrayList<ImageView> qIVs = new ArrayList<>();
    ArrayList<ImageView> aIVs = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Answer> answers = new ArrayList<>();
    ImageButton drawLinks;
    FrameLayout answerHolder, questionHolder;
    float x, y;
    String modifier;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dynamic);

        //initializing all of the static fields on screen
        canvas = (RelativeLayout) findViewById(R.id.canvas);
        canvas.setOnTouchListener(this);
        drawLinks = (ImageButton) findViewById(R.id.drawLinks);
        answerHolder = (FrameLayout) findViewById(R.id.answerHolder);
        questionHolder = (FrameLayout) findViewById(R.id.questionHolder);

        //initializing all of the dynamic fields on screen
        makeNewQuestionImage();
        makeNewAnswerImage();
        x = y = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_dynamic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        x = motionEvent.getX();
        y = motionEvent.getY();
        //TODO
        //draws the icon to the panel
        TextView tv = new TextView(this);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.question);
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(50, 50);
        params.leftMargin = (int) x;
        params.topMargin = (int) y;
        canvas.addView(iv, params);
        return true;
    }

    public void makeNewQuestionImage()
    {
        //TODO
        //Creates a new ImageView object that is empty
        ImageView blankQuestion = new ImageView(this);
        //Adds the question icon to the view
        blankQuestion.setImageResource(R.drawable.question);
        //Sets up the onTouch drag events
        blankQuestion.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                //TODO make this code dynamic
                modifier = "question";
                /*not used yet
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        //not used yet
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //TODO call drag events
                        break;
                    case MotionEvent.ACTION_UP:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        break;
                }*/
                return true;
            }
        });
        //place the new image view inside of the question holder
    }

    public void makeNewAnswerImage()
    {
        //TODO
        //create a new image view inside of the question holder
        //set the ID to be "question"[next number]
        //set the field to be draggable
        //set the onDrag events
    }
}
