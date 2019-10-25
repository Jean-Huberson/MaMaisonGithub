package com.freehome.mamaison.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.freehome.mamaison.R
import com.freehome.mamaison.models.listFolder.ListResidence
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class AdapterListResidence(val listener: ClickListener): RecyclerView.Adapter<AdapterListResidence.ViewHolder>() {

    var itemList:List<ListResidence> = listOf()

    fun adapterListResidence(itemList:List<ListResidence>){
        this.itemList = itemList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.residence_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val checkItem = itemList.get(position)
        val refMedias = checkItem.getReferenceMedias()
        holder.onBind(checkItem, listener)

        holder.cardview.tag = position
        holder.prix.setText(itemList.get(position).getPrixLogements()+" Fcfa")
        holder.superficie.text = itemList.get(position).getSuperficieLogements()
        holder.desc_type_maison.text = itemList.get(position).getDescriptionTypeLogement()
        holder.desc_type_offre.text = itemList.get(position).getDescriptionTypeOffres()
        holder.nombre_pieces.setText(itemList.get(position).getNombreDePiecesLogements()+" pièces(s)")
        holder.nombre_chambre.setText(itemList.get(position).getNombreDeChambresLogements()+" chambre(s)")
        holder.lieu.setText(itemList.get(position).getDescriptionCommune()+" - "+itemList.get(position).getDescriptionQuartier())

        Picasso.get()
            .load(refMedias)
            .resize(50,50)
            .into(holder.imageView, object: Callback {
                override fun onError(e: Exception?) {
                    holder.itemProgress.visibility = View.GONE
                }

                override fun onSuccess() {
                    holder.itemProgress.visibility = View.GONE
                }
            })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun onBind(item: ListResidence, listener: ClickListener){
            cardview.setOnClickListener{
                listener.onClick(item)
            }
        }

        val cardview = itemView.findViewById<MaterialCardView>(R.id.cardView)
        val imageView = cardview.findViewById<ImageView>(R.id.imageView)
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
        fun onClick(item: ListResidence)

    }

}