package istresearch.lineaustestinggrounds;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
        final EditText tv = new EditText(this);
        tv.setFocusableInTouchMode(true);
        /*tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO set editing option pop up
                tv.setText("Horray!!!");
            }
        });*/
        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(600, 50);

        //TODO change so that an editing option pops up
        if(modifier == "question")
        {
            tv.setHint("add question here");
        }
        else if(modifier == "answer")
        {
            tv.setHint("add answer here");
        }
        else
        {
            tv.setHint("add text here");
        }
        params.leftMargin = (int) x;
        params.topMargin = (int) y;
        canvas.addView(tv, params);
        canvas.setOnTouchListener(null);
        return true;
    }

    public void makeNewQuestionImage()
    {
        final ImageButton blankQuestion = new ImageButton(this);
        //Adds the question icon to the view
        blankQuestion.setImageResource(R.drawable.question);

        //Sets the view holder for the question to be able to be clicked
        //TODO change to onTouchListener for the drag events
        questionHolder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setModifier("question");
                setCanvasOnAndOff("on");
            }
        });
    }

    public void makeNewAnswerImage()
    {
        //Creates a new ImageView object that is empty
        ImageView answerQuestion = new ImageView(this);
        //Adds the question icon to the view
        answerQuestion.setImageResource(R.drawable.question);

        //Sets the view holder for the question to be able to be clicked
        answerHolder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setModifier("answer");
                setCanvasOnAndOff("on");
            }
        });
    }

    public void setModifier(String m)
    {
        modifier = m;
    }

    public void setCanvasOnAndOff(String s)
    {
        if (s == "on")
        {
            canvas.setOnTouchListener(this);
        }
        else
        {
            canvas.setOnTouchListener(null);
        }
    }
}
