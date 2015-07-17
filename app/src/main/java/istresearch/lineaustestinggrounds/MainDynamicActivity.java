package istresearch.lineaustestinggrounds;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * This is the main class for LinneuasTestingGround. It acts as the model for
 * the dynamic GUI setup.
 *
 * @author David Tichnell
 *
 *
 */
public class MainDynamicActivity extends ActionBarActivity implements View.OnDragListener
{
    RelativeLayout relativeLayout;
    Canvas background;
    Bitmap bitmap;
    Entity entitySelected;
    ArrayList<View> viewsOnSreen = new ArrayList<>();
    ArrayList<Entity> entities = new ArrayList<>();
    ImageButton drawLinks;
    FrameLayout answerHolder, questionHolder;
    float x1, y1;
    boolean drawLines, oneViewAlreadySelected;
    Paint drawingPaint;
    ImageButton createEntity, refresh;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dynamic);

        //Initializes all of the static fields on screen
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.setOnDragListener(this);
        createEntity = (ImageButton) findViewById(R.id.addButton);
        createEntity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO make pop up dialog and create a new entity
            }
        });

        //Sets up the linking button and the paint used to
        //graphically represent the links on screen
        drawingPaint = new Paint();
        drawingPaint.setAntiAlias(true);
        drawingPaint.setColor(0xFF000000);//black
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawLinks = (ImageButton) findViewById(R.id.drawLinks);
        drawLinks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(getDrawLines())
                {
                    setDrawLines(false);
                    drawLinks.setPressed(false);
                }
                else
                {
                    setDrawLines(true);
                    drawLinks.setPressed(true);
                }
            }
        });

        /*
            The two lines below are where any preset classes should be represented.
            I created layout holders for the image buttons so the list view would
            not collapse on itself while dragging. There are better ways to do this;
            this was simply the fastest way
         */
        answerHolder = (FrameLayout) findViewById(R.id.answerHolder);
        questionHolder = (FrameLayout) findViewById(R.id.questionHolder);

        //Initializes all of the dynamic fields on screen
        makeNewQuestionImage();
        makeNewAnswerImage();

        //Initializes the reference variables used in the class
        x1 = y1 = 0;
        drawLines = oneViewAlreadySelected = false;

        //Waits a quarter of a second before running so the views have time to populate
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                //Sets the background canvas
                bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
                background = new Canvas(bitmap);
                relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }
        }, 500);

        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                refreshView();
            }
        });
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

    //TODO think about condensing these new image methods down to one, generic method
    public void makeNewQuestionImage()
    {
        final ImageButton blankQuestion = new ImageButton(this);
        //Adds the question icon to the view
        //TODO change icon/lettering so that it is generic and changes dependent on the name of the class
        blankQuestion.setImageResource(R.drawable.question);

        questionHolder.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                entities.add(new Question(""));  //adds a new Question object to the entities list
                //Sets up the info for the drag event
                ClipData data = ClipData.newPlainText("", "Question" + (entities.size() - 1));
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
        });
    }

    public void makeNewAnswerImage()
    {
        //Creates a new ImageView object that is empty
        ImageView answerQuestion = new ImageView(this);
        //Adds the question icon to the view
        answerQuestion.setImageResource(R.drawable.question);

        answerHolder.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                entities.add(new Answer(""));
                ClipData data = ClipData.newPlainText("", "Answer" + (entities.size() - 1));
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
        });
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent)
    {
        switch(dragEvent.getAction())
        {
            case DragEvent.ACTION_DRAG_STARTED:
                //Do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //Do nothing
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //Do nothing
                break;
            case DragEvent.ACTION_DROP:
                //Resets the icon view
                View viewBeingDragged = (View) dragEvent.getLocalState();
                addNewView((int)dragEvent.getX(), (int)dragEvent.getY(), entities.get(entities.size() - 1));
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //Make sure the view is updated
                view.invalidate();
                break;
        }
        return true;
    }

    public boolean getDrawLines()
    {
        return drawLines;
    }

    public void setDrawLines(boolean bool)
    {
        drawLines = bool;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void refreshView()
    {
        //Remove below lines if not testing
        FileReference test = new FileReference("", "");
        entities.add(test);
        entities.get(0).sendLink(test);

        //TODO run through the entities to cross check for activities on screen
        if(viewsOnSreen.size() < entities.size())
        {
            for(int i = viewsOnSreen.size(); i < entities.size(); i++)
            {
                if(entities.get(i).linksToThisNode != null)
                {
                    //Only places entity south of parent
                    int x = viewsOnSreen.get(viewsOnSreen.size() - 1).getLeft();
                    x = x + 25;
                    int y = viewsOnSreen.get(viewsOnSreen.size() - 1).getBottom();
                    y = y + 55;

                    addNewView(x, y, entities.get(i));
                    //only assuming last view added was parent node until new linked list is made
                    Toast.makeText(getApplicationContext(), "Child y: " + y + " Parent bottom: " + viewsOnSreen.get(viewsOnSreen.size()-2).getBottom(), Toast.LENGTH_LONG).show();
                    background.drawLine(viewsOnSreen.get(viewsOnSreen.size()-2).getBottom(), viewsOnSreen.get(viewsOnSreen.size()-2).getLeft(),
                            x, y, drawingPaint);
                    relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                }
                else
                {
                    addNewView(5, 5, entities.get(i));
                }
                //TODO create better logic for placing elements depending on their parent/child relationship
            }
        }
    }

    public void addNewView(int x, int y, Entity tag)
    {
        final int X = x;
        final int Y = y;
        final EditText editText = new EditText(getApplicationContext());
        editText.setTextColor(Color.BLACK);
        editText.setHintTextColor(Color.GRAY);
        editText.setTag(tag);
        String objectPath = editText.getTag().getClass().toString();
        editText.setHint("Enter " + objectPath.substring(objectPath.lastIndexOf(".") + 1) + " here");

        editText.setFocusableInTouchMode(true);
        editText.setTextSize(10);

        editText.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            //TODO possibly add reflection in order to see what parameters can be edited in class?
            public void onClick(View view)
            {
                //TODO add checking for this view being selected twice
                if(getDrawLines())
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // hides the keyboard popup

                    if(oneViewAlreadySelected)
                    {
                        Entity secondEntity = (Entity) editText.getTag();
                        //Sends link from first node to second node
                        entitySelected.sendLink(secondEntity);
                        //Second node accepts link invitation from first node
                        secondEntity.receiveLink(entitySelected.linksFromThisNode.get(entitySelected.linksFromThisNode.size()-1));

                        //Update the background
                        background.drawLine(x1, y1, editText.getX(), editText.getTop(), drawingPaint);
                        relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
                        oneViewAlreadySelected = false;
                        entitySelected = null;
                    }
                    else
                    {
                        x1 = editText.getX();
                        y1 = editText.getBottom();
                        oneViewAlreadySelected = true;
                        entitySelected = (Entity) editText.getTag();
                    }
                }
                else
                {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT); // restore input type
                }
            }
        });

        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) x;
        params.topMargin = (int) y;
        relativeLayout.addView(editText, params);
        viewsOnSreen.add(editText);
    }
}
