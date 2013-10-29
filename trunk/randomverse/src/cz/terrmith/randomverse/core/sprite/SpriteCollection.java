package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection of sprites in layers
 */
public class SpriteCollection {

    private Map<SpriteLayer,List<Sprite>> sprites;
    private Map<SpriteLayer, Boundary> layerBoundaries;

    /**
     * Creates new sprite collection with given boundaries
     * By default projectiles are given screen boundary, extended boundary otherwise
     *
     * @param screenBoundary screen boundary
     * @param extendedBoundary extended boundary
     */
    public SpriteCollection(Boundary screenBoundary, Boundary extendedBoundary){
        this(screenBoundary, extendedBoundary,null);
    }

    /**
     * Creates new sprite collection with given boundaries
     *
     * @param screenBoundary screen boundary
     * @param extendedBoundary extended boundary
     * @param layerBoundaries define specific boundaries for each layer
     */
    public SpriteCollection(Boundary screenBoundary, Boundary extendedBoundary, Map<SpriteLayer, Boundary> layerBoundaries){
        if(screenBoundary == null || extendedBoundary == null) {
            throw new IllegalArgumentException("screen and extended boundaries are mandatory for sprite collection constructor, but was: " +screenBoundary + ", " + extendedBoundary);
        }

        this.sprites = new HashMap<SpriteLayer, List<Sprite>>();
        if(layerBoundaries != null) {
            this.layerBoundaries = layerBoundaries;
        } else {
            this.layerBoundaries = new HashMap<SpriteLayer, Boundary>();
        }

        for (SpriteLayer layer : SpriteLayer.values()) {
            this.sprites.put(layer, new ArrayList<Sprite>());
             if (!this.layerBoundaries.containsKey(layer)) {
                 if (SpriteLayer.PROJECTILE.equals(layer)) {
                     this.layerBoundaries.put(layer,screenBoundary);
                 } else {
                     this.layerBoundaries.put(layer,extendedBoundary);
                 }
             }
        }

    }

    public void put(SpriteLayer layer, Sprite sprite){
        if (sprite != null) {
	        sprites.get(layer).add(sprite);
        }
    }

   public List<Sprite> getSprites(SpriteLayer layer){
        return this.sprites.get(layer);
   }

   public void drawLayer(SpriteLayer layer, Graphics g, ImageLoader iml){
        for (Sprite sprite : sprites.get(layer)){
            sprite.drawSprite(g, iml);
        }
   }

   public Boundary getBoundary(SpriteLayer layer) {
    return layerBoundaries.get(layer);
   }

    public void clear(){
        for (Map.Entry<SpriteLayer, List<Sprite>> entry : sprites.entrySet()) {
            entry.setValue(new ArrayList<Sprite>());
        }
    }
}
