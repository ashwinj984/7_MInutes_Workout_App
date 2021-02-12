package eu.tutorials.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import eu.tutorials.a7minutesworkout.R.drawable
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{
    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null

    private var media : MediaPlayer? = null

    private var exerxiseAdapter : Exercise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogCalller()
        }

        tts = TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseLst()
        setUpRestView()


        setupExerciseStatusRecyclerView()
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(media != null){
            media!!.stop()
        }

        super.onDestroy()

    }

    private fun setRestProgressBar(){
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntillFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerxiseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun setUpRestView(){

//        try{
//            //val soundUri = Uri.parse("android:resource://eu.tutorials.a7minutesworkout/" + SubUrbanCradles.mp3)
////        media = MediaPlayer.create(
////            applicationContext,
////            R.raw.SubUrbanCradles.mp3
////        )
//
////        media!!.isLooping = false
//
////        media!!.start()
//        }catch (e:Exception){
//            e.printStackTrace()
//        }



        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntillFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerxiseAdapter!!.notifyDataSetChanged()
                if(currentExercisePosition < 11) {

                    setUpRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
//                    Toast.makeText(
//                        this@ExerciseActivity,
//                        "Completed",
//                        Toast.LENGTH_LONG
//                    ).show()

                }

            }
        }.start()
    }

    private fun setUpExerciseView(){
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        ivimage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.UK)
            if(result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","The Language Specified is not supported !")
            }
        }else{
            Log.e("TTS","Initialisation Failed !")
        }
    }

    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
                                            LinearLayoutManager.HORIZONTAL,false)
        exerxiseAdapter = Exercise(exerciseList!!,this)
        rvExerciseStatus.adapter = exerxiseAdapter
    }

    private fun customDialogCalller(){
        val custonDialog = Dialog(this)
        custonDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        custonDialog.tvYes.setOnClickListener {
            finish()
            custonDialog.dismiss()
        }

        custonDialog.tvNo.setOnClickListener {
            custonDialog.dismiss()
        }

        custonDialog.show()
    }

}