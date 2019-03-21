package bchavez.cs134.miracosta.superheroes;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bchavez.cs134.miracosta.superheroes.model.JSONLoader;
import bchavez.cs134.miracosta.superheroes.model.Superhero;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Superhero Quiz";
    private static final int HEROES_IN_QUIZ = 10;

    private List<Superhero> mAllSuperheroes;
    private List<Superhero> mQuizSuperheroList;
    private Superhero mCorrectSuperhero;
    private int mTotalGuesses;
    private int mCorrectGuesses;
    private SecureRandom random;
    private Handler handler;

    private TextView mQuestionNumberTextView;
    private ImageView mSuperheroImageView;
    private TextView mAnswerTextView;
    private Button[] mButtons = new Button[4];
    private TextView gameTitleTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuizSuperheroList = new ArrayList<>(HEROES_IN_QUIZ);
        random = new SecureRandom();
        handler = new Handler();

        mQuestionNumberTextView = findViewById(R.id.questionNumberTextView);
        mSuperheroImageView = findViewById(R.id.superheroImageView);
        mAnswerTextView = findViewById(R.id.anserTextView);
        gameTitleTextView = findViewById(R.id.gameTitleTextView);

        mButtons[0] = findViewById(R.id.button0);
        mButtons[1] = findViewById(R.id.button1);
        mButtons[2] = findViewById(R.id.button2);
        mButtons[3] = findViewById(R.id.button3);

        try{
            mAllSuperheroes = JSONLoader.loadJSONFromAsset(this);
        }catch(IOException e){
            Log.e(TAG, e.getMessage());
        }

        resetQuiz();
    }

    public void resetQuiz(){

        mCorrectGuesses = 0;
        mTotalGuesses = 0;

        mQuizSuperheroList.clear();

        int size = mAllSuperheroes.size();
        int randomPosition;
        Superhero randomSuperhero;

        while(mQuizSuperheroList.size() <= HEROES_IN_QUIZ){
            randomPosition = random.nextInt(size);
            randomSuperhero = mAllSuperheroes.get(randomPosition);

            //Check for duplicates
            if(!mQuizSuperheroList.contains(randomSuperhero)){
                mQuizSuperheroList.add(randomSuperhero);
            }
        }

        for(int x = 0; x< mButtons.length; x++){
            mButtons[x].setText(mQuizSuperheroList.get(x).getName());
        }

        loadNextSuperhero();
    }

    private void loadNextSuperhero(){

        mCorrectSuperhero = mQuizSuperheroList.get(0);
        mQuizSuperheroList.remove(0);

        mAnswerTextView.setText("");

        mQuestionNumberTextView.setText("Question {mCorrectGuesses} of {HEROES_IN_QUIZ}");

        AssetManager am = getAssets();

        try{
            InputStream stream = am.open(mCorrectSuperhero.getFileName());
            Drawable image = Drawable.createFromStream(stream, mCorrectSuperhero.getName());
            mSuperheroImageView.setImageDrawable(image);
        }catch(IOException e){
            Log.e(TAG, e.getMessage());
        }

         do{
             Collections.shuffle(mAllSuperheroes);
         }while(mAllSuperheroes.subList(0,mButtons.length).contains(mCorrectSuperhero));

         for(int x = 0; x<mButtons.length; x++){
             mButtons[x].setEnabled(true);
             mButtons[x].setText(mAllSuperheroes.get(x).getName());
         }

         mButtons[random.nextInt(mButtons.length)].setText(mCorrectSuperhero.getName());
    }

    public void makeGuess(View v){
        mTotalGuesses ++;
        Button clickedButton = (Button) v;

        String guessedName = clickedButton.getText().toString();

        if(guessedName.equalsIgnoreCase(mCorrectSuperhero.getName())){
            mCorrectGuesses ++;

            //Check to see if game is over
            if(mCorrectGuesses <= HEROES_IN_QUIZ){

                //diable all buttons
                for(int x = 0; x < mButtons.length; x++){
                    mButtons[x].setEnabled(false);
                }

                mAnswerTextView.setText(mCorrectSuperhero.getName());
                mAnswerTextView.setTextColor(getResources().getColor(R.color.correctColor));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextSuperhero();
                    }
                }, 2000);

            }else{
                //crate an alertdialog with some tet and button rest quiz
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                double percentage = (double) mCorrectGuesses/mTotalGuesses*100;
                builder.setMessage("{mTotalGuesses} guesses, {percentage}% correct");
                builder.setPositiveButton("Reset Quiz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetQuiz();
                    }
                });
                builder.setCancelable(false);
                //create the dialog
                builder.create();
                //show the dialog
                builder.show();
            }
        }else{
            //diable button
            clickedButton.setEnabled(false);
            mAnswerTextView.setText("Incorrect!");
            mAnswerTextView.setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }
}
