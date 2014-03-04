package cz.terrmith.randomverse.core.sprite;

import cz.terrmith.randomverse.core.geometry.Boundary;
import cz.terrmith.randomverse.core.image.ImageLoader;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TODO singleton
 * Collection of sprites in layers
 * SpriteCollection does not remove inactive sprites! Game must handle removal itself.
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

    /**
     * Returns unmodifible collection of sprites of given layer
     * @param layer
     * @return
     */
   public Collection<Sprite> getSprites(SpriteLayer layer){
       List<Sprite> spritesToReturn = new ArrayList<Sprite>();
       spritesToReturn.addAll(this.sprites.get(layer));
        return Collections.unmodifiableCollection(spritesToReturn);
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

    /**
     * Removes given reference from sprite layer
     * @param layer layer from which the sprite will be removed
     * @param sprite sprite to remove
     */
    public void remove(SpriteLayer layer, Sprite sprite) {
        List<Sprite> spriteLayer = sprites.get(layer);
        Iterator<Sprite> it = spriteLayer.iterator();
        while (it.hasNext()) {
            Sprite next = it.next();
            if (sprite == next) {
                it.remove();
            }
        }
    }

    /**
     * Removes sprites that are not active
     * @param layer layer from which the sprite will be removed
     */
    public void removeInactive(SpriteLayer layer) {
        List<Sprite> spriteLayer = sprites.get(layer);
        Iterator<Sprite> it = spriteLayer.iterator();
        while (it.hasNext()) {
            Sprite next = it.next();
            if (!next.isActive()) {
                it.remove();
            }
        }
    }
}
