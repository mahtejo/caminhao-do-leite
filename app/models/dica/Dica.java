package models.dica;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="Dica")
public abstract class Dica {
    @Id
    @GeneratedValue
    private Long id;

    public Dica(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String toString();
}
