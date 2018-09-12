package edmt.dev.androidcollapsingtoolbar;

import android.graphics.Bitmap;
import android.location.Location;

/**
 * Created by badr on 02/01/18.
 */

public class Prof {
    private String prenom,nom,niveau,matiere,tel,prix,mail;
    private Bitmap image;
    private Double latitude,longitude;

    public Prof(){}

    public String getPrix() {
        return prix;
    }

    public String getMail() {
        return mail;
    }

    public Bitmap getImage() {
        return image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getMatiere() {
        return matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public Location getLocation(){
        Location location = new Location("");
        location.setLatitude(getLatitude());
        location.setLongitude(getLongitude());

        return location;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
