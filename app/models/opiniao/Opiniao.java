package models.opiniao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by orion on 20/03/15.
 */
@Entity(name="Opiniao")
public abstract class Opiniao {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean concorda;

    public Opiniao(){}

    public Opiniao(boolean concorda) throws Exception{
        setConcorda(concorda);
    }

    public boolean isConcorda() {
        return concorda;
    }

    public void setConcorda(boolean concorda) {
        this.concorda = concorda;
    }
}
