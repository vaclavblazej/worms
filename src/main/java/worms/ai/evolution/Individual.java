package worms.ai.evolution;

import java.io.Serializable;

/**
 * @author Václav Blažej
 */
public interface Individual extends Serializable {

    int fitness();

    Individual mutate();

    Individual cross(Individual a);

    double distance(Individual individual);
}
