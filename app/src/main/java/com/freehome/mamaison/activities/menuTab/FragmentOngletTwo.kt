package com.freehome.mamaison.activities.menuTab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.freehome.mamaison.adapters.AdapterListLocation
import com.freehome.mamaison.R
import com.freehome.mamaison.mamaisonapi.ApiServices
import com.freehome.mamaison.models.listFolder.ListLocation
import com.freehome.mamaison.models.responseFolder.LocationResponse
import com.freehome.mamaison.retrofitClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOngletTwo: Fragment(), View.OnClickListener {

    lateinit var mService: ApiServices
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: AdapterListLocation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_onglet_two, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mService = RetrofitClient.getClient()
        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(context)
        mAdapter = AdapterListLocation(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(mAdapter)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)


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

                        }
                        else{
                            Toast.makeText(context, response.body()!!.texterror, Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })
    }

    override fun onClick(view: View?) {
        if(view?.tag != null){
            Log.d("TAG", "click!")
        }
        else{
            Log.d("TAG", "no click!")
        }
    }

    override fun onStart() {
        super.onStart()
        getLocationsToApi()
    }

}