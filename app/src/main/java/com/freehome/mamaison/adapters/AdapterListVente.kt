package com.freehome.mamaison.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freehome.mamaison.R
import com.freehome.mamaison.models.listFolder.ListLog
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class AdapterListVente(private val listener: ClickListener): RecyclerView.Adapter<AdapterListVente.ViewHolder>(){

    var itemList:List<ListLog> = listOf()

    fun adapterListVente(itemList:List<ListLog>){
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vente_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemSale = itemList.get(position)
        val refMedias = itemSale.getReferenceMedias()
        holder.bind(itemSale, listener)

        with(holder){
            prix.setText(itemList.get(position).getPrixLogements()+" Fcfa")
            superficie.text = itemList.get(position).getSuperficieLogements()
            desc_type_maison.text = itemList.get(position).getDescriptionTypeLogement()
            desc_type_offre.text = itemList.get(position).getDescriptionTypeOffres()
            nombre_pieces.setText(itemList.get(position).getNombreDePiecesLogements()+" pièces(s)")
            nombre_chambre.setText(itemList.get(position).getNombreDeChambresLogements()+" chambre(s)")
            lieu.setText(itemList.get(position).getDescriptionCommune()+" - "+itemList.get(position).getDescriptionQuartier())
        }
        Picasso.get()
            .load(refMedias)
            .resize(50,50)
            .into(holder.imageview, object: Callback {
                override fun onError(e: Exception?) {
                    holder.itemProgress.visibility = View.GONE
                }

                override fun onSuccess() {
                    holder.itemProgress.visibility = View.GONE
                }
            })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(itemSale: ListLog, listener: ClickListener) {
            cardview.setOnClickListener{
                listener.onItemClick(itemSale)
            }
        }

        val cardview = itemView.findViewById<MaterialCardView>(R.id.cardView)
        val imageview = cardview.findViewById<ImageView>(R.id.imageView)
        val prix = cardview.findViewById<TextView>(R.id.prix)
        val superficie = cardview.findViewById<TextView>(R.id.superficie)
        val desc_type_maison = cardview.findViewById<TextView>(R.id.desc_type_maison)
        val desc_type_offre = cardview.findViewById<TextView>(R.id.desc_type_offre)
        val lieu = cardview.findViewById<TextView>(R.id.lieu)
        val nombre_chambre = cardview.findViewById<TextView>(R.id.nombre_chambre)
        val nombre_pieces = cardview.findViewById<TextView>(R.id.nombre_de_piece)
        val itemProgress = itemView.findViewById<ProgressBar>(R.id.item_progress)

    }

    interface ClickListener {
        fun onItemClick(itemSale: ListLog)

    }


}