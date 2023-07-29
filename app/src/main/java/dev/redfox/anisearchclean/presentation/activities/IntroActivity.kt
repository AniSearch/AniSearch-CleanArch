package dev.redfox.anisearchclean.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.redfox.anisearchclean.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    var prevStarted = "yes"
    override fun onResume() {
        super.onResume()
        val sharedpreferences =
            getSharedPreferences("AniSearch", Context.MODE_PRIVATE)
        if(!sharedpreferences.getBoolean(prevStarted, false)){
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, true);
            editor.apply()
        } else {
            moveToMain()
        }
    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}