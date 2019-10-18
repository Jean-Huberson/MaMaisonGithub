package com.freehome.mamaison.activities.authentification

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.freehome.mamaison.R
import com.freehome.mamaison.activities.mains.MainActivity
import com.freehome.mamaison.mamaisonapi.ApiServices
import com.freehome.mamaison.models.responseFolder.AuthResponse
import com.freehome.mamaison.models.User
import com.freehome.mamaison.retrofitClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class AuthFragment: Fragment() {
    private val REQUEST_SIGNUP = 0
    lateinit var mService:ApiServices
    lateinit var dialog:ProgressDialog

     var fragmentmanager: FragmentManager? = null
     var fragmenttransaction: FragmentTransaction? = null

    var email:EditText? = null
    var password:EditText? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.auth_fragment, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnlog = view.findViewById<Button>(R.id.btn_login)
         email = view.findViewById<EditText>(R.id.email)
         password = view.findViewById<EditText>(R.id.password)
        val signupLink = view.findViewById<TextView>(R.id.signupLink)

        signupLink.setOnClickListener(View.OnClickListener {
            onClickSignupLink(view)
        })

        btnlog.setOnClickListener(View.OnClickListener {
            onLogin(view)
        })

        dialog = ProgressDialog(context, R.style.ThemeDialog)
        dialog.setMessage("Veuillez patienter svp...")
        dialog.setCanceledOnTouchOutside(false)
        dialog.setTitle("Authentification")
        dialog.isIndeterminate()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          mService = RetrofitClient.getClient()
    }

    private fun onClickSignupLink(view:View){
        fragmentmanager = fragmentManager
        fragmenttransaction = fragmentmanager?.beginTransaction()
        fragmenttransaction?.replace(
            R.id.frameLayout,
            SignActivity()
        )?.commit()
    }

    fun isConnected():Boolean{
        var checkedConnect:Boolean = true
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        if(activeNetwork?.isConnectedOrConnecting == true){
            checkedConnect = true
        }
        if(activeNetwork?.type == ConnectivityManager.TYPE_WIFI){
            checkedConnect = true
        }
        if(activeNetwork?.type == ConnectivityManager.TYPE_MOBILE){
            checkedConnect = true
        }
        return checkedConnect
    }

    // onLogin Methode lorsqu'on clique sur le bouton se connecter

    fun onLogin(btn:View){
        val emailClient= email?.getText().toString().trim()
        val mot_de_passClient = password?.getText().toString().trim()
        if(isConnected()){
            if(validation(emailClient,mot_de_passClient)){

                dialog.show()
                loginSendToApi(emailClient, mot_de_passClient)

            }

        }

    }


    private fun loginSendToApi(emailClient: String, mot_de_passClient: String){

        val requestBody: User = User()
            requestBody.emailClient = emailClient
            requestBody.mot_de_passClient = mot_de_passClient

        mService.loginClient(requestBody)
            .enqueue(object:Callback<AuthResponse>{

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    dialog.dismiss()
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                    if (response.body() != null) {
                        if (response.code() == 200) {
                            if(response.body()!!.result){

                                dialog.dismiss()
                                val sharedPreferences = context?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                                val editor = sharedPreferences?.edit()

                                editor?.putInt("id", response.body()!!.idClients)
                                editor?.putString("nomClient", response.body()!!.nomClients)
                                editor?.putString("prenomClient", response.body()!!.prenomClients)
                                editor?.putString("emailClient", response.body()!!.emailClients)
                                editor?.putString("telephoneClient", response.body()!!.telephoneClients)
                                editor?.apply()

                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)
                                finishActivity()
                            }
                            else{
                                dialog.dismiss()
                                email?.setError(response.body()!!.iserror)

                                password?.setError(response.body()!!.iserror)
                                password?.setText(null)

                            }
                        }
                    }
                    else if (response.code() == 400) {
                        dialog.dismiss()
                        Toast.makeText(context,"Impossible de verifier l'utilisateur. Les données sont incompletes.", Toast.LENGTH_SHORT).show()
                    }

                }

            })
    }


        fun validation(mail:String, passw:String):Boolean{
            var VALIDE:Boolean = true

            if(mail.isEmpty() || !isEmailValid(mail) || mail.trim().length == 0){
                email?.setText(null)
                email?.setError("Entrez une adresse valide!")
                VALIDE = false
            }
            else{
                email?.setError(null)
            }

            if(passw.isEmpty() || !isPasswordValid(passw) || passw.trim().length == 0){
                password?.setText(null)
                password?.setError("Mot de passe trop court!")
                VALIDE = false
            }
            else{
                password?.setError(null)
            }

            return VALIDE
        }


        fun isEmailValid(email: String): Boolean {

            return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(email).matches()
        }


        fun isPasswordValid(password: String):Boolean{

            return password.trimmedLength()>4
        }

    private fun finishActivity(){
        if(activity!= null){
            activity?.finish()
        }
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
    }


}



