package worms.ai.evolution;

/**
 * @author Václav Blažej
 */
public interface Individual {

    int fitness();

    Individual mutate();

    Individual cross(Individual a);
}
