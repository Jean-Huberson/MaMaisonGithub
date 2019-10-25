package com.freehome.mamaison.activities.menuTab

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.freehome.mamaison.adapters.AdapterListLocation
import com.freehome.mamaison.R
import com.freehome.mamaison.activities.activitiesDetails.LocationDetails
import com.freehome.mamaison.mamaisonapi.ApiServices
import com.freehome.mamaison.models.listFolder.ListLocation
import com.freehome.mamaison.models.responseFolder.LocationResponse
import com.freehome.mamaison.retrofitClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOngletTwo: Fragment(), AdapterListLocation.ClickListener{
    
    lateinit var mService: ApiServices
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: AdapterListLocation
    var progressbar:ProgressBar? = null
    var swipe:SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_onglet_two, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val splash_time_out:Long = 3000
        mService = RetrofitClient.getClient()
        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(context)
        mAdapter = AdapterListLocation(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(mAdapter)

        progressbar = view.findViewById<ProgressBar>(R.id.progressBar)
        swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        progressbar?.isVisible = true
        swipe?.setOnRefreshListener {
            getLocationsToApi()

            Handler().postDelayed({
                swipe?.isRefreshing = false
            }, splash_time_out)

        }

        getLocationsToApi()

    }

    private fun getLocationsToApi(){
        mService.getLocations().enqueue(object: Callback<LocationResponse> {

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                if(response.body() != null){
                    if(response.code() == 200){
                        if(response.body()!!.error){
                            var list_location = listOf<ListLocation>()
                            list_location = response.body()!!.list_Location
                            mAdapter.adapterListLocation(list_location)
                            mAdapter.notifyDataSetChanged()
                            progressbar?.isVisible = false

                        }
                        else{
                            Toast.makeText(context, response.body()!!.texterror, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })
    }

    override fun onClick(item: ListLocation) {
        val intent = Intent(context, LocationDetails::class.java)
        intent.putExtra("IMAGE", item.getReferenceMedias())
        intent.putExtra("DESC_LOG", item.getDescriptionTypeLogement())
        intent.putExtra("TYPE_OFFRE", item.getDescriptionTypeOffres())
        intent.putExtra("DESC_COMMUNE", item.getDescriptionCommune())
        intent.putExtra("DESC_QUARTIER", item.getDescriptionQuartier())
        intent.putExtra("EMAIL_PROPRIETAIRE", item.getEmailProprietaires())
        intent.putExtra("NBRE_CHAMBRE", item.getNombreDeChambresLogements())
        intent.putExtra("NBRE_PIECE", item.getNombreDePiecesLogements())
        intent.putExtra("SUPERFICIE", item.getSuperficieLogements())
        intent.putExtra("TEL_PROPRIETAIRE", item.getTelephoneProprietaires())
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        getLocationsToApi()
    }
}