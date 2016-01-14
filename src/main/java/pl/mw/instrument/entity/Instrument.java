package pl.mw.instrument.entity;

import javax.persistence.*;

/**
 * <p>Created by mwiesiolek on 12/01/2016.</p>
 */
@Entity
@Table(name = "INSTRUMENT_PRICE_MODIFIER")
public class Instrument extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MULTIPLIER", nullable = false)
    private Double multiplier;

    @Deprecated
    public Instrument() {
        //don't use it
    }

    public boolean isPersistedInDb(){
        return id > 0;
    }

    public boolean isNotPersistedInDb(){
        return id <= 0;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public Instrument(Long id, final String name, final Double multiplier) {
        this.id = id;
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(final Double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Instrument that = (Instrument) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", multiplier=" + multiplier +
                '}';
    }
}
