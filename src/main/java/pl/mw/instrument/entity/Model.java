package pl.mw.instrument.entity;

import java.io.Serializable;

/**
 * <p>Created by mwiesiolek on 02/05/2015.</p>
 */
public abstract class Model implements Serializable {

    @Deprecated
    public Model() {
        //required by jpa
    }

    public abstract Long getId();

    public abstract void setId(final Long id);
}
