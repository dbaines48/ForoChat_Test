package com.uninorte.googleauth;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by ftorr on 1/11/2016.
 */
@IgnoreExtraProperties
public class Foro {
    public String name_foro;

    public Foro() {
    }

    public Foro(String name_foro) {
        this.name_foro = name_foro;
    }

    public String getName_foro() {
        return name_foro;
    }
}
