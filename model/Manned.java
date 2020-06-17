package model;

/**
 * Enemies implementing this interface instantiate a land enemy the moment they are destroyed
 */
public interface Manned {
    void ejectOperator();
}
