package com.freehome.mamaison.activities.menuTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.freehome.mamaison.adapters.AdapterListResidence
import com.freehome.mamaison.R
import com.freehome.mamaison.mamaisonapi.ApiServices
import com.freehome.mamaison.models.listFolder.ListResidence
import com.freehome.mamaison.models.responseFolder.ResidenceResponse
import com.freehome.mamaison.retrofitClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOngletThree: Fragment(), View.OnClickListener {

    lateinit var mService: ApiServices
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: AdapterListResidence

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_onglet_three, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mService = RetrofitClient.getClient()
        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(context)
        mAdapter = AdapterListResidence(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(mAdapter)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)


        getResidencesToApi()

    }

    private fun getResidencesToApi(){
        mService.getResidences().enqueue(object: Callback<ResidenceResponse> {

            override fun onFailure(call: Call<ResidenceResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResidenceResponse>, response: Response<ResidenceResponse>) {
                if(response.body() != null){
                    if(response.code() == 200){
                        if(response.body()!!.error){
                            var list_residence = listOf<ListResidence>()
                            list_residence= response.body()!!.list_Residence
                            mAdapter.adapterListResidence(list_residence)
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

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()
        getResidencesToApi()
    }

}