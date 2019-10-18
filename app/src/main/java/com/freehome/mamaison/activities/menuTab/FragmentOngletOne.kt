package com.freehome.mamaison.activities.menuTab

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.freehome.mamaison.adapters.AdapterListVente
import com.freehome.mamaison.R
import com.freehome.mamaison.activities.activitiesDetails.VenteDetail
import com.freehome.mamaison.mamaisonapi.ApiServices
import com.freehome.mamaison.models.listFolder.ListCommune
import com.freehome.mamaison.models.listFolder.ListLog
import com.freehome.mamaison.models.responseFolder.SalesResponse
import com.freehome.mamaison.retrofitClient.RetrofitClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentOngletOne: Fragment(), AdapterListVente.ClickListener, SearchView.OnQueryTextListener{

    lateinit var mService: ApiServices
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: AdapterListVente
    lateinit var list_loge:List<ListLog>

    var progressbar:ProgressBar? = null
    var swipe:SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_onglet_one, container, false) as ViewGroup

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list_loge = listOf<ListLog>()
        mService = RetrofitClient.getClient()
        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        val layoutManager = LinearLayoutManager(context)
        mAdapter = AdapterListVente(this, context)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(mAdapter)

        progressbar = view.findViewById<ProgressBar>(R.id.progressBar)
        swipe = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        progressbar?.isVisible = true
        swipe?.setOnRefreshListener {
            getSalesToApi()
            swipe?.isRefreshing = false
        }

        getSalesToApi()
    }

    private fun getSalesToApi(){
        mService.getSales().enqueue(object: Callback<SalesResponse> {

            override fun onFailure(call: Call<SalesResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SalesResponse>, response: Response<SalesResponse>) {
               if(response.body() != null){
                   if(response.code() == 200){
                       if(response.body()!!.error){
                           list_loge = response.body()!!.list_Log
                           itemCallBack(list_loge)
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

    override fun onItemClick(itemSale: ListLog) {
        showDetailsOfSale(itemSale)
    }

    fun itemCallBack(listLog: List<ListLog>){
        mAdapter.adapterListVente(listLog)
        mAdapter.notifyDataSetChanged()
    }

    fun showDetailsOfSale(itemSale: ListLog){
        val intent:Intent = Intent(context, VenteDetail::class.java)
            intent.putExtra("IMAGE", itemSale.getReferenceMedias())
            intent.putExtra("DESC_LOG", itemSale.getDescriptionTypeLogement())
            intent.putExtra("TYPE_OFFRE", itemSale.getDescriptionTypeOffres())
            intent.putExtra("DESC_COMMUNE", itemSale.getDescriptionCommune())
            intent.putExtra("DESC_QUARTIER", itemSale.getDescriptionQuartier())
            intent.putExtra("EMAIL_PROPRIETAIRE", itemSale.getEmailProprietaires())
            intent.putExtra("NBRE_CHAMBRE", itemSale.getNombreDeChambresLogements())
            intent.putExtra("NBRE_PIECE", itemSale.getNombreDePiecesLogements())
            intent.putExtra("SUPERFICIE", itemSale.getSuperficieLogements())
            intent.putExtra("TEL_PROPRIETAIRE", itemSale.getTelephoneProprietaires())
            startActivity(intent)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main, menu)
        val menuItem = menu!!.findItem(R.id.action_search)
        val action_search = menuItem
            action_search.actionView
        val searchView = action_search as SearchView
            searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)

    }*/

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(search_text: String): Boolean {
        val filteredList:ArrayList<ListLog> = arrayListOf<ListLog>()

        list_loge.filterTo(filteredList){
            it.getDescriptionCommune()!!.toLowerCase().contains(search_text.toLowerCase())
        }
        mAdapter.adapterListVente(filteredList)
        return true
    }


}