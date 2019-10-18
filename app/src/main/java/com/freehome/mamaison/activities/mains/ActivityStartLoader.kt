package com.freehome.mamaison.activities.mains

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.freehome.mamaison.R

class ActivityStartLoader : AppCompatActivity() {
    private val splash_time_out:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_loader)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splash_time_out)
    }
}
