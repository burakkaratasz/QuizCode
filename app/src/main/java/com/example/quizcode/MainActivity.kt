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
        quizModelList.add(QuizModel("1", "Algoritmalar", "Kolay-Orta Seviye Algoritma Soruları", "20"))
        quizModelList.add(QuizModel("2", "SQL", "Tüm SQL Soruları", "10"))
        quizModelList.add(QuizModel("3", "Python", "Tüm Python Soruları", "15"))
        setupRecyclerView()
    }
}