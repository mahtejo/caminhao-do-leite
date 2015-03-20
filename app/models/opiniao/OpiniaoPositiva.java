package models.opiniao;

import javax.persistence.Entity;

/**
 * Created by orion on 20/03/15.
 */
@Entity(name="OpiniaoPositiva")
public class OpiniaoPositiva extends Opiniao {
    public OpiniaoPositiva() throws Exception{
        super(true);
    }
}
