package com.uninorte.googleauth;
import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by ftorr on 1/11/2016.
 */
@IgnoreExtraProperties
public class Foro {
    public String id;
    public String name;

    public Foro() {
    }

    public Foro(String name_foro) {
        this.name = name_foro;
    }

    public String getName_foro() {
        return name;
    }

    public Foro(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
