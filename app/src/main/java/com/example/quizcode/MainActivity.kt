package com.example.quizcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizcode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
    private fun getDataFromFirebase(){
        //dummy data - deneme dataları

        val listQuestionModel = mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("Android nedir?", mutableListOf("Programlama Dili", "İşletim Sistemi", "Veri Tabanı","Hiçbiri"), "İşletim Sistemi"))
        listQuestionModel.add(QuestionModel("Kotlin dilinde ekran çıktısı nasıl alınır?", mutableListOf("Console.Writeline()", "System.out.println()", "println()","printf()"), "println()"))
        listQuestionModel.add(QuestionModel("Kotlin dilinde değişkenler nasıl tanımlanır?", mutableListOf("var, let, const gibi anahtar kelimelerle", "val, var, const gibi anahtar kelimelerle", "variable, value, constant gibi anahtar kelimelerle","int, float, string gibi veri türleriyle"), "val, var, const gibi anahtar kelimelerle"))

        quizModelList.add(QuizModel("1", "Android", "Kolay-Orta Seviye Android Soruları", "10", listQuestionModel))
        //quizModelList.add(QuizModel("2", "SQL", "Tüm SQL Soruları", "10"))
        //quizModelList.add(QuizModel("3", "Python", "Tüm Python Soruları", "15"))
        setupRecyclerView()
    }
}