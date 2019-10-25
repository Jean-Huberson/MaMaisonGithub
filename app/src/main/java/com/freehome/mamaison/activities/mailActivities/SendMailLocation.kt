package com.freehome.mamaison.activities.mailActivities


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.freehome.mamaison.R
import com.freehome.mamaison.activities.activitiesDetails.LocationDetails
import com.freehome.mamaison.activities.activitiesDetails.VenteDetail


class SendMailLocation: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send_email_location)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.icon_back)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar

        actionbar!!.title = "Contacter le propriétaire"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, LocationDetails::class.java)
            startActivity(intent)
            finish()
        })
    }


}