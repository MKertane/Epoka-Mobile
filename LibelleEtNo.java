package com.example.epokamission;

public class LibelleEtNo {
    public String libelle;
    public int no;
    public String codePostal;

    public LibelleEtNo (String unLibelle, int unNo, String unCodePostal) {
        libelle = unLibelle;
        no = unNo;
        codePostal = unCodePostal;
    }

    @Override
    public String toString() {
        return no + " " + libelle + " (" + codePostal + ")";

    }
}


