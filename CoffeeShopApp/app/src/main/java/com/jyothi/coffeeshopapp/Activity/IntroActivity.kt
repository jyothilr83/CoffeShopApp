package com.jyothi.coffeeshopapp.Activity

import android.content.Intent
import android.os.Bundle
import com.jyothi.coffeeshopapp.databinding.ActivityIntroBinding



class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the binding
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the button click listener
        binding.startBtn.setOnClickListener {
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
        }
    }
}