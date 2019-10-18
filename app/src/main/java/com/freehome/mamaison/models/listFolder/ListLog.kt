package com.freehome.mamaison.models.listFolder

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListLog{

    @SerializedName("id_logements")
    @Expose
    private var idLogements:String? = null

    @SerializedName("adresse_logements")
    @Expose
    private var adresseLogements:String? = null

    @SerializedName("superficie_logements")
    @Expose
    private var superficieLogements:String? = null

    @SerializedName("prix_logements")
    @Expose
    private var prixLogements:String? = null

    @SerializedName("nombre_de_pieces_logements")
    @Expose
    private var nombreDePiecesLogements:String? = null

    @SerializedName("nombre_de_chambres_logements")
    @Expose
    private var nombreDeChambresLogements:String? = null

    @SerializedName("description_logements")
    @Expose
    private var descriptionLogements:String? = null

    @SerializedName("description_quartier")
    @Expose
    private var descriptionQuartier:String? = null

    @SerializedName("description_commune")
    @Expose
    private var descriptionCommune:String? = null

    @SerializedName("description_type_offres")
    @Expose
    private var descriptionTypeOffres:String? = null

    @SerializedName("email_proprietaires")
    @Expose
    private var emailProprietaires:String? = null

    @SerializedName("telephone_proprietaires")
    @Expose
    private var telephoneProprietaires:String? = null

    @SerializedName("description_type_logement")
    @Expose
    private var descriptionTypeLogement:String? = null

    @SerializedName("reference_medias")
    @Expose
    private var referenceMedias:String? = null

    fun getIdLogements():String? {
        return idLogements
    }

    fun setIdLogements(idLogements:String) {
        this.idLogements = idLogements
    }

    fun getAdresseLogements():String? {
        return adresseLogements
    }

    fun setAdresseLogements(adresseLogements:String) {
        this.adresseLogements = adresseLogements
    }

    fun getSuperficieLogements():String? {
        return superficieLogements
    }

    fun setSuperficieLogements(superficieLogements:String) {
        this.superficieLogements = superficieLogements
    }

    fun getPrixLogements():String? {
        return prixLogements
    }

    fun setPrixLogements(prixLogements:String?) {
        this.prixLogements = prixLogements
    }

    fun getNombreDePiecesLogements():String? {
        return nombreDePiecesLogements
    }

    fun setNombreDePiecesLogements(nombreDePiecesLogements:String) {
        this.nombreDePiecesLogements = nombreDePiecesLogements
    }

    fun getNombreDeChambresLogements():String? {
        return nombreDeChambresLogements;
    }

    fun setNombreDeChambresLogements(nombreDeChambresLogements:String) {
        this.nombreDeChambresLogements = nombreDeChambresLogements
    }

    fun getDescriptionLogements():String? {
        return descriptionLogements
    }

    fun setDescriptionLogements(descriptionLogements:String) {
        this.descriptionLogements = descriptionLogements
    }

    fun getDescriptionQuartier():String? {
        return descriptionQuartier
    }

    fun setDescriptionQuartier(descriptionQuartier:String) {
        this.descriptionQuartier = descriptionQuartier
    }

    fun getDescriptionCommune():String? {
        return descriptionCommune
    }

    fun setDescriptionCommune(descriptionCommune:String) {
        this.descriptionCommune = descriptionCommune
    }

    fun getDescriptionTypeOffres():String? {
        return descriptionTypeOffres
    }

    fun setDescriptionTypeOffres(descriptionTypeOffres:String) {
        this.descriptionTypeOffres = descriptionTypeOffres
    }

    fun getEmailProprietaires():String? {
        return emailProprietaires
    }

    fun setEmailProprietaires(emailProprietaires:String) {
        this.emailProprietaires = emailProprietaires
    }

    fun getTelephoneProprietaires():String? {
        return telephoneProprietaires
    }

    fun setTelephoneProprietaires(telephoneProprietaires:String) {
        this.telephoneProprietaires = telephoneProprietaires
    }

    fun getDescriptionTypeLogement():String? {
        return descriptionTypeLogement
    }

    fun setDescriptionTypeLogement(descriptionTypeLogement:String) {
        this.descriptionTypeLogement = descriptionTypeLogement
    }

    fun getReferenceMedias():String? {
        return referenceMedias
    }

    fun  setReferenceMedias(referenceMedias:String) {
        this.referenceMedias = referenceMedias
    }
}