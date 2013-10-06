package cz.terrmith.randomverse.core.sprite;

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

    public SpriteCollection(){
        this.sprites = new HashMap<SpriteLayer, List<Sprite>>();
        for (SpriteLayer layer : SpriteLayer.values()) {
            this.sprites.put(layer, new ArrayList<Sprite>());
        }
    }

    public void put(SpriteLayer layer, Sprite sprite){
        sprites.get(layer).add(sprite);
    }

   public List<Sprite> getSprites(SpriteLayer layer){
        return this.sprites.get(layer);
   }

   public void drawLayer(SpriteLayer layer, Graphics g, ImageLoader iml){
        for (Sprite sprite : sprites.get(layer)){
            sprite.drawSprite(g, iml);
        }
   }
}
