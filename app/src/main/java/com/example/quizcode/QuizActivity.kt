package com.example.quizcode

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.quizcode.databinding.ActivityQuizBinding
import com.example.quizcode.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), OnClickListener {

    companion object{
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizBinding

    var currentQuestionIndex = 0; //soru indexi
    var selectedAnswer = ""
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        loadQuestion()
        startTimer()
        binding.apply {//butonlar için click listener
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btnNext.setOnClickListener(this@QuizActivity)

        }
        setContentView(binding.root)
    }

    private fun startTimer(){ //timer başlatan fonksiyon
        val totalTimeInMilliS = time.toInt() * 60 * 1000L
        object: CountDownTimer(totalTimeInMilliS, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                //süre bittiğinde yapılacak işlem
                TODO("Not yet implemented")
            }

        }.start()
    }

    private fun loadQuestion(){ //soru bilgilerini getiren fonksiyon
        selectedAnswer = ""

        //seçilen buton rengini sıfırlamak için
        binding.btn0.setBackgroundColor(getColor(R.color.orange))
        binding.btn1.setBackgroundColor(getColor(R.color.orange))
        binding.btn2.setBackgroundColor(getColor(R.color.orange))
        binding.btn3.setBackgroundColor(getColor(R.color.orange))

        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text = "Soru ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question //soru textini getirir
            //seçenekler
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(v: View?) {
        //HATA

        val clickedBtn = v as Button
        if(clickedBtn.id == R.id.btn_next){
            //ileri butonuna basıldığında
            if(selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                //skor çalışıyor mu kontrol
                Log.i("Quiz skoru:", score.toString())
            }
            currentQuestionIndex++
            loadQuestion()
        }
        else{
            //ayarlar butonu eklenecek

            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.black))
        }
    }

    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat())*100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage"
            if(percentage>60){
                scoreTitle.text = "Tebrikler! Quiz'i Başarıyla GEÇTİNİZ!"
                scoreTitle.setTextColor(getColor(R.color.blue))

            }
            else{
                scoreTitle.text = "Başarısız..."
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$totalQuestions sorudan $score adet doğru"
            finishBtn.setOnClickListener {
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}