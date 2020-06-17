package model;

/**
 * Entities implementing this interface are capable of changing their x and y coordinates and have a method changing
 * both at once
 */
public interface Mobile {
    void incrementX(float x);
    void incrementY(float y);
    void advance();
}
