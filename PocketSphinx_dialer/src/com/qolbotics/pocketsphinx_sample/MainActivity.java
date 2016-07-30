/* PocketSphinx Continue Recognition Demo
 * Developed by Luis G III
 * e-mail: loiis.x14@gmail.com
 * visit: http://hellospoonpr@gmail.com and get your own HelloSpoon robot!
 * */
package com.qolbotics.pocketsphinx_sample;

import java.io.File;

import java.io.IOException;



import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements RecognitionListener{
	
	SpeechRecognizer recognizer;
	TextView recognizer_state, recognized_word;
	int conteo = 0;
	int permiso_flag=0;
	Handler a = new Handler();
	static int count=0;
	static String res="";
	static String one="one";
	static String zero="zero";
	static String two="two";
	static String three="three";
	static String four="four";
	static String five="five";
	static String six="six";
	static String seven="seven";
	static String eight="eight";
	static String nine="nine";
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		recognizer_state = (TextView) findViewById(R.id.textView2);
		recognized_word = (TextView) findViewById(R.id.textView3);
		
		new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(getApplicationContext());
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }
        
            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                } else {
    
                	FireRecognition();
                }
            }
        }.execute(); 
        
       
	}
	
	
	
	public void Call(View v)
	{
		EditText e=(EditText) findViewById(R.id.editText1);

		
		Intent i=new Intent(Intent.ACTION_CALL);
		i.setData(Uri.parse("tel:"+e.getText().toString()));
		startActivity(i);
		
		
		
		
	}
	@Override
	public void onStop(){
		super.onStop();
		recognizer.removeListener(this);
	}
	
	
	public void FireRecognition(){
		count++;
		Log.d("Recognition","Recognition Started");
        recognizer_state.setText("Recognition Started!");
        //conteo = 0;
		
        recognizer.startListening("digits");
        //Timer();
        //recognizer.stop();
        
	}

	@Override
	public void onBeginningOfSpeech() 
	{
		// TODO Auto-generated method stub
	//	FireRecognition();
		//recognized_word.setText("hello");
		
		
	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		//recognizer.stop();
	}	

	private void setupRecognizer(File assetsDir) {
		
	    File modelsDir = new File(assetsDir, "models");
	    recognizer = defaultSetup()
	            .setAcousticModel(new File(modelsDir, "hmm/en-us-semi"))
	            .setDictionary(new File(modelsDir, "dict/cmu07a.dic"))
	            .setRawLogDir(assetsDir).setKeywordThreshold(1e-40f)
	            .getRecognizer();
	    recognizer.addListener(this);

	    
	    File digitsGrammar = new File(modelsDir, "grammar/digits.gram");
	    recognizer.addGrammarSearch("digits", digitsGrammar);
	    
	     
	}
	
	@Override
	public void onResult(Hypothesis hup) {
		//conteo +=1;
		//if(conteo==1){
			
	Timer();
		
	}
	
	
	@Override
	public void onPartialResult(Hypothesis arg0) {
		if(arg0 == null){ return; }
		String comando = arg0.getHypstr();
		
		
		//String res="";
		//for getting to no. conversion and dialing  
		
		EditText e1=(EditText) findViewById(R.id.editText1);

		if(comando.equals(one))
		res=res.concat("1");
		else
		if(comando.equals(zero))
		res=res.concat("0");
else
		if(comando.equals(two))
		res=res.concat("2");
else
		if(comando.equals(three))
			res=res.concat("3");
else
		if(comando.equals(four))
			res=res.concat("4");
else
		if(comando.equals(five))
			res=res.concat("5");
else
		if(comando.equals(six))
			res=res.concat("6");
else
		if(comando.equals(seven))
			res=res.concat("7");
else
		if(comando.equals(eight))
			res=res.concat("8");
else
		if(comando.equals(nine))
			res=res.concat("9");
		
		
		e1.setText(res);
		
		recognized_word.setText(comando);
    	
		
		
		///conteo +=1;
		if( count<10){
			//conteo = 0;
			//Log.d("Res", comando);
			
		recognizer.stop();
		//Timer();
	
		}else{
			recognizer.stop();
		}
		
	}
	public void Timer(){
		 new Thread(new Runnable() {
		        @Override
		        public void run() {
		                try {
		                    Thread.sleep(600);
		                    
		                    a.post(new Runnable() {
		                        @Override
		                        public void run() {
		                        	
		                        	FireRecognition();
		                        }
		                    });
		                } catch (Exception e) {
		                }
		            }
		        
		    }).start();
	}


}
