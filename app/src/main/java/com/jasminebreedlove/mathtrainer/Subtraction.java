package com.jasminebreedlove.mathtrainer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Subtraction extends AppCompatActivity {

    TextView timer, scoreTV, problem, pausedTV;
    Button button0, button1, button2, button3;
    CountDownTimer cdt;
    RelativeLayout rL, sub;
    boolean soundOnOff;
    SharedPreferences preferences;

    // variables to set up game play
    int userScore = 0;
    int numOfQuestions = 0;
    int correctAnswerLocation;
    ArrayList<Integer> answerChoices = new ArrayList<Integer>();
    long timeLeft;
    boolean isPaused = false;
    Menu menu = null; // initialize menu globally so we can dynamically change the Play/Pause button on action bar

    MediaPlayer mediaPlayerRight, mediaPlayerWrong; // for sound effects

    // need to pause game if user goes back to previous activity or exits app
    // use dialog pop up to ask user if they want to reset or continue where they left off


    //time keeping method
    public void beginTimer(View view) {
        timer.setText("20s"); // set text view for timer to 20 seconds

        //timer
        cdt = new CountDownTimer(20100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                //set timer text view to how many seconds left
                timer.setText(String.valueOf(millisUntilFinished / 1000 + "s")); // how many seconds left
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                //toast is for debugging purposes
                Toast.makeText(Subtraction.this, "Time's Up!", Toast.LENGTH_SHORT).show();

                questionsCount();

            }
        }.start();

    } // end beginTimer

    // custom methods to pause/resume game timer
    public void pauseTimer() {
        cdt.cancel();
        isPaused = true;
        //    Log.i("time left when paused", "time left " + timeLeft/1000 + "s");
        //dynamically changing icons on action bar for play/pause on game pause
        MenuItem itemPause = menu.findItem(R.id.pause);
        itemPause.setVisible(false);
        MenuItem itemPlay = menu.findItem(R.id.play);
        itemPlay.setVisible(true);
        sub.setVisibility(View.INVISIBLE); // hiding game board when user pauses the game
        pausedTV.setVisibility(View.VISIBLE); // show parent activity with word Paused!
        //    Toast.makeText(this, "Game Paused!", Toast.LENGTH_LONG).show();
    } // end pauseTimer

    public void resumeTimer() {
        cdt = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                //set timer text view to how many seconds left
                timer.setText(String.valueOf(timeLeft / 1000 + "s")); // how many seconds left
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                //toast is for debugging purposes
                Toast.makeText(Subtraction.this, "Time's Up!", Toast.LENGTH_SHORT).show();

                questionsCount();
            }
        }.start(); // resume the cdt timer

        isPaused = false;
        //    Log.i("TIME RESUMED AT", "RESUMED: " + timeLeft/1000 + "s");
        //dynamically changing icons on action bar for play/pause on resume
        MenuItem itemPause = menu.findItem(R.id.pause);
        itemPause.setVisible(true);
        MenuItem itemPlay = menu.findItem(R.id.play);
        itemPlay.setVisible(false);
        sub.setVisibility(View.VISIBLE); // bring back game board when user presses play
        pausedTV.setVisibility(View.INVISIBLE);
        //    Toast.makeText(this, "Game Resumed", Toast.LENGTH_LONG).show();
    } // end resume timer


    // method to generate random new question
    public void generateQuestion() {

        // initialize random number generators
        Random randomNum = new Random();
        int a = randomNum.nextInt(31); // num will be a num b/t 0 and 30
        int b = randomNum.nextInt(31);// num will be a num b/t 0 and 30

        //set the equation text view to these numbers
        problem.setText(Integer.toString(a) + " - " + Integer.toString(b) + " = ? "); //math operator will change per activity

        // randomize where the correct answer will be in the 4 answer choices
        correctAnswerLocation = randomNum.nextInt(4); // b/t buttons 0 to 3

        answerChoices.clear(); // need to clear previous answer choice options so that new random choices will populate

        int wrongAnswer; // initialize variable for wrong answer choice options

        //create loop for the array of answer choices to determine where correct answer will be
        //...and make sure it doesn't show up in the other 3 choices as well
        //...and generate random wrong answers for the other 3 choice options
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerLocation) { // if the correct answer will be in this particular spot in list of choices...
                answerChoices.add(a - b); // subtract the 2 random int's together and store that number in our array
            } else {
                wrongAnswer = randomNum.nextInt(61); // set other 3 choice's values to random number b/t 0 and 60
                // NEED TO INCLUDE SOMETHING TO BE SURE THE OTHER 3 ANSWERS DON'T END UP WITH THE SAME RANDOM VALUE OF WRONG ANSWER TOO!!!

                while (wrongAnswer == a - b) {
                    wrongAnswer = randomNum.nextInt(61); // if any of the other 3 choices ends up with same value as the correct answer location value, immediately change it to another random int
                }// end while
                answerChoices.add(wrongAnswer); // add random int values to the wrong answer choice options (our array list)
            } // end if-else
        } // end for loop

        // update the text of our answer choice buttons to represent the random numbers we generated above
        button0.setText(Integer.toString(answerChoices.get(0)));
        button1.setText(Integer.toString(answerChoices.get(1)));
        button2.setText(Integer.toString(answerChoices.get(2)));
        button3.setText(Integer.toString(answerChoices.get(3)));

        beginTimer(timer);
    } // end create question


    // handles when user picks an answer (on click method for our 4 button choices)
    public void selectedAnswer(View view) {
        if(view.getTag().toString().equals(Integer.toString(correctAnswerLocation))) { // if they pick the right answer
            userScore++;
            Toast.makeText(getApplicationContext(),"Nice Work!", Toast.LENGTH_SHORT).show();

            //sound effect for right answer pick
            if (soundOnOff == false) {
                //   Log.i("Turn SOUND off on press", "SOUND EFFECTS SHOULD not PLAY");
                mediaPlayerRight.setVolume(0,0);
            } else {
                //    Log.i("turn SOUND ON on press", "SOUND EFFECTS SHOULD BE PLAYING");
                mediaPlayerRight.start();
            }
            //highlight their selection green

        } else {
            Toast.makeText(getApplicationContext(),"Wrong!", Toast.LENGTH_SHORT).show();
            //play sound effect for incorrect answer
            if (soundOnOff == false) {
                //   Log.i("Turn SOUND off on press", "SOUND EFFECTS SHOULD not PLAY");
                mediaPlayerWrong.setVolume(0,0);
            } else {
                //    Log.i("turn SOUND ON on press", "SOUND EFFECTS SHOULD BE PLAYING");
                mediaPlayerWrong.start();
            }
            // highlight their selection red

        } // end if-else
        //    iv.setImageResource(0);
        scoreTV.setText(Integer.toString(userScore) + "/10");
        cdt.cancel(); // stop timer when user clicks an answer so that timer will reset on next question

        questionsCount();

    } // end chosen answer


    // method to keep track of the number of questions asked
    public void questionsCount() {
        numOfQuestions++;
        if (numOfQuestions < 10) {
            generateQuestion();
        } else {
            //alert dialog which pops up when game is over (after 10 questions asked)
            new AlertDialog.Builder(Subtraction.this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Game Over")
                    .setMessage("Final Stats:\nYou answered " + userScore + " out of 10 correctly")
                    .setPositiveButton("Play Again?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //reset statistics and start over with new question
                            userScore = 0;
                            numOfQuestions = 0;
                            scoreTV.setText("0/10");
                            generateQuestion();// call method to restart game by generating new question
                        }
                    })
                    .setNegativeButton("Select Another Category", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // create an intent to direct user back to the main page
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                        }
                    }).show();
        } // end if-else clause
    } // end questionsCount method

    // method to handle back button press
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //when user presses back button, pause game & ask if they really want to quit the current game
            pauseTimer();
            new AlertDialog.Builder(Subtraction.this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Exit Game?")
                    .setMessage("Are you sure you want to quit your current game?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(Subtraction.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("No", null).show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    } // end onKeyDown()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);

        timer = (TextView)findViewById(R.id.timerView);
        scoreTV = (TextView)findViewById(R.id.scoreView);
        problem = (TextView)findViewById(R.id.equation);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        rL = (RelativeLayout)findViewById(R.id.relativeLayout);
        sub = (RelativeLayout)findViewById(R.id.lowerRelative);
        pausedTV = (TextView)findViewById(R.id.pauseText);
        mediaPlayerRight = MediaPlayer.create(this, R.raw.e_chime);
        mediaPlayerWrong = MediaPlayer.create(this, R.raw.buzzer);
        preferences = this.getSharedPreferences("com.jasminebreedlove.mathtrainer", Context.MODE_PRIVATE);

        generateQuestion();


        if (preferences != null) {
            soundOnOff = preferences.getBoolean("sound", true);
            //   Log.i("preferences", String.valueOf(soundOnOff));
        } else {
            //    System.out.println("no data available");
            Toast.makeText(this, "Error in retrieving saved settings", Toast.LENGTH_SHORT).show();
        }
    } // end oncreate


    @Override
    public void onPause() {
        super.onPause();
        pauseTimer();
        //   Log.i("on pause activity", "activity has been released");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_addition, menu);
        return true;
    } // end OnCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.pause) {
            //    Log.i("pause button", "pause button pressed");
            pauseTimer();
        } else if (id == R.id.play) {
            //    Log.i("PLAY BUTTON PRESSED", "PLAY BUTTON HAS BEEN PRESSED");
            resumeTimer();
        }
        return super.onOptionsItemSelected(item);
    } // end onOptionsItemSelected

} // end class
