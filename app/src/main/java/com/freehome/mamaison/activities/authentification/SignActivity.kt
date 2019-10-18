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
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.fragment.app.Fragment
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


class SignActivity : Fragment() {
    private val REQUEST_SIGNUP = 0
    lateinit var mService: ApiServices
    lateinit var dialog: ProgressDialog

    var nom:EditText? = null
    var prenom:EditText? = null
    var email:EditText? = null
    var telephone:EditText? = null
    var password:EditText? = null
    var password2:EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_sign, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val btnSignup = view.findViewById<Button>(R.id.btn_signup)
         nom = view.findViewById<EditText>(R.id.input_name)
         prenom = view.findViewById<EditText>(R.id.input_first_name)
         telephone = view.findViewById<EditText>(R.id.input_telephone)
         email = view.findViewById<EditText>(R.id.input_email)
         password = view.findViewById<EditText>(R.id.input_password)
         password2 = view.findViewById<EditText>(R.id.input_reEnterPassword)

        dialog = ProgressDialog(context, R.style.ThemeDialog)
        dialog.setMessage("Veuillez patienter svp...")
        dialog.setCanceledOnTouchOutside(false)
        dialog.setTitle("Enregistrement")
        dialog.isIndeterminate()

        btnSignup.setOnClickListener(View.OnClickListener {
            onSignup(view)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mService = RetrofitClient.getClient()
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

    fun onSignup(btn:View){
        val nomClient = nom?.getText().toString().trim()
        val prenomClient = prenom?.getText().toString().trim()
        val emailClient = email?.getText().toString().trim()
        val telephoneClient = telephone?.getText().toString().trim()
        val mot_de_passClient = password?.getText().toString().trim()
        val mot_de_passClient2 = password2?.getText().toString().trim()

        if(isConnected()){
            if(validation(nomClient,prenomClient,emailClient,telephoneClient,mot_de_passClient,mot_de_passClient2)){

                dialog.show()
                signupSendToApi(nomClient,prenomClient,emailClient,telephoneClient,mot_de_passClient)

            }
            else{
                dialog.dismiss()
            }

        }

    }


    private fun signupSendToApi(nomClient:String,prenomClient:String,emailClient: String,telephoneClient:String, mot_de_passClient: String){

        val requestBody: User = User()
        requestBody.nomClient = nomClient
        requestBody.prenomClient = prenomClient
        requestBody.emailClient = emailClient
        requestBody.telephoneClient = telephoneClient
        requestBody.mot_de_passClient = mot_de_passClient

        mService.createClient(requestBody)
            .enqueue(object: Callback<AuthResponse> {

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

                        }
                        else if(response.code() == 503){
                            dialog.dismiss()
                            email?.setError(response.body()!!.iserror)
                        }
                    }
                    else if (response.code() == 400) {
                        dialog.dismiss()
                        Toast.makeText(context,"Impossible d'effectuer l'inscription. Vos données sont incompletes.", Toast.LENGTH_SHORT).show()
                    }

                }

            })
    }


    fun validation(name:String,first_name:String,mail:String,tel:String, passw:String, passw2:String):Boolean{
        var VALIDE:Boolean = true

        if(name.isEmpty() || name.trim().length <= 2){
            nom?.setText(null)
            nom?.setError("Entrez un nom valide!")
            VALIDE = false
        }
        else{
            nom?.setError(null)
        }

        if(first_name.isEmpty() || first_name.trim().length <= 2){
            prenom?.setText(null)
            prenom?.setError("Entrez un prenom valide!")
            VALIDE = false
        }
        else{

            prenom?.setError(null)

        }

        if(mail.isEmpty() || !isEmailValid(mail)){
            email?.setText(null)
            email?.setError("Entrez une adresse valide!")
            VALIDE = false
        }
        else{

            email?.setError(null)

        }

        if(tel.isEmpty()|| !isPhoneNumberValide(tel) || tel.trim().length <= 7){
            telephone?.setText(null)
            telephone?.setError("Entrez un numéro valide!")
            VALIDE = false
        }
        else{
            telephone?.setError(null)
        }

        if(passw.isEmpty() || !isPasswordValid(passw) || passw.trim().length <= 3){
            password?.setText(null)
            password?.setError("Mot de passe trop court!")
            VALIDE = false

        }
        else{
            password?.setError(null)

        }

        if(passw2.isEmpty() || !isPasswordValid(passw2) || passw2.trim().length <= 3 ){
            password2?.setText(null)
            password2?.setError("Mot de passe trop court!")
            VALIDE = false
        }
        else{
            password2?.setError(null)
        }

        if(passw != passw2){
            password2?.setText(null)
            password2?.setError("Entrez un mot de pass identique")
            VALIDE = false
        }
        else{
            password2?.setError(null)
        }

        return VALIDE
    }

    fun isPhoneNumberValide(tel:String):Boolean{

        return Pattern.compile(
            "^[0-9]{8,20}\$"
        ).matcher(tel).matches()
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
