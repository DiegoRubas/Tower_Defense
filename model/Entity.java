package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private float x, y;
    private final String imageURL;
    private boolean displayed = false;
    private transient ImageView imageView;

    public Entity(float x, float y, String imageURL) {
        this.x = x;
        this.y = y;
        this.imageURL = imageURL;
    }

    public Entity(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Returns the distance between two entities
     */
    public double getDistance(Entity entity) {
        return Math.sqrt(Math.pow(entity.getX() - x, 2) + Math.pow(entity.getY() - y, 2));
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public boolean isDisplayed() { return displayed; }
    public void setDisplayed(boolean b) { displayed = b; }

    /**
     * Returns its image view if necessary, thereby decoupling this class from graphics
     */
    public ImageView getImageView() {
        if (imageView == null) {
            startImageView();
        }
        return this.imageView;
    }

    public void startImageView() {
        this.imageView = new ImageView(new Image(imageURL, 64, 64, false, false));
    }

}
