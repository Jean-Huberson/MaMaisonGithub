package com.freehome.mamaison.activities.activitiesDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.freehome.mamaison.R
import com.squareup.picasso.Picasso
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.freehome.mamaison.activities.mailActivities.SendMail
import com.freehome.mamaison.activities.mains.MainActivity
import com.freehome.mamaison.adapters.AdapterListResidence
import com.freehome.mamaison.models.listFolder.ListResidence
import com.squareup.picasso.Callback
import java.lang.Exception

class ResidenceDetails: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.residence_details)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.icon_back)
        setSupportActionBar(toolbar)

        val actionbar = supportActionBar

        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        })


        val intent = intent
        val imageUrl = intent.getStringExtra("IMAGE")
        val desc_log = intent.getStringExtra("DESC_LOG")
        val type_offre = intent.getStringExtra("TYPE_OFFRE")
        val num_proprietaire = intent.getStringExtra("TEL_PROPRIETAIRE")
        val imageV = findViewById<ImageView>(R.id.imageSlider)
        val tv = findViewById<TextView>(R.id.description)
        val progress = findViewById<ProgressBar>(R.id.progres_details)
        progress.visibility

        actionbar?.setTitle("Résidence")

        //tv.setText(desc_log)
        Picasso.get()
            .load(imageUrl)
            .resize(800,600)
            .into(imageV, object: Callback {
                override fun onError(e: Exception?) {
                    progress.visibility = View.GONE
                }

                override fun onSuccess() {
                    progress.visibility = View.GONE
                }
            })

        /*Glide.with(imageV.context)
            .load(imageUrl)
            .listener(object : RequestListener<String, GlideDrawable> {
                override fun onResourceReady(
                    resource: GlideDrawable?,
                    model: String?,
                    target: com.bumptech.glide.request.target.Target<GlideDrawable>?,
                    isFromMemoryCache: Boolean,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }

                override fun onException(
                    e: Exception?,
                    model: String?,
                    target: com.bumptech.glide.request.target.Target<GlideDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }
            }).into(imageV)*/

        val fab_main = findViewById<FloatingActionButton>(R.id.fab)
        val fab1_mail = findViewById<FloatingActionButton>(R.id.fab1)
        val fab2_call = findViewById<FloatingActionButton>(R.id.fab2)

        val fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        val fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        val fab_clock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_clock)
        val fab_anticlock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_anticlock)
        var isOpen: Boolean = false

        val textview_mail = findViewById<TextView>(R.id.textview_mail)
        val textview_call = findViewById<TextView>(R.id.textview_call)

        fab_main.setOnClickListener(View.OnClickListener {

                if (isOpen) {

                    textview_mail.setVisibility(View.INVISIBLE)
                    textview_call.setVisibility(View.INVISIBLE)
                    fab2_call.startAnimation(fab_close)
                    fab1_mail.startAnimation(fab_close)
                    fab_main.startAnimation(fab_anticlock)
                    fab2_call.isClickable = false
                    fab1_mail.isClickable = false
                    isOpen = false

                } else {
                    textview_mail.setVisibility(View.VISIBLE)
                    textview_call.setVisibility(View.VISIBLE)
                    fab2_call.startAnimation(fab_open)
                    fab1_mail.startAnimation(fab_open)
                    fab_main.startAnimation(fab_clock)
                    fab2_call.isClickable = true
                    fab1_mail.isClickable = true
                    isOpen = true
                }

        })


        fab2_call.setOnClickListener( View.OnClickListener {
                val number = Uri.parse("tel: ${num_proprietaire}")
                val callIntent = Intent(Intent.ACTION_DIAL, number)
                startActivity(callIntent)
        })


        fab1_mail.setOnClickListener(View.OnClickListener {
            val mailIntent = Intent(this, SendMail::class.java)
            startActivity(mailIntent)

        })


    }


}