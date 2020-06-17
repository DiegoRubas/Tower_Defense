package model;

/**
 * Enemies implementing this interface can disappear before getting hit by a projectile for a brief moment and reappear
 * a short distance further down their path
 */
public interface Phasing {
    boolean canDisappear();
    void disappear();
    void reappear();
}
