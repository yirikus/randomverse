package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.sprite.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple container to hold sprites
 */
public class SimpleSpriteContainer implements SpriteContainer {

    private List<Sprite> sprites;
    private Set<SpriteContainerObserver> observers = new HashSet<SpriteContainerObserver>();
    private String activationKey;
    private boolean notified = false;

    public SimpleSpriteContainer(Sprite sprite) {
        if (sprite == null) {
            throw new IllegalArgumentException("Sprite must not be null");
        }
        this.sprites = new ArrayList<Sprite>();
        this.sprites.add(sprite);
    }

    public SimpleSpriteContainer(List<Sprite> sprites) {
        if (sprites == null || sprites.isEmpty()) {
            throw new IllegalArgumentException("Sprites must not be null or empty");
        }
        this.sprites = sprites;
    }

    @Override
    public void registerObserver(SpriteContainerObserver obs, String activationKey) {
        if (obs == null) {
            throw new IllegalArgumentException("observer must not be null");
        }
        observers.add(obs);
        this.activationKey = activationKey;
    }

    @Override
    public void updateSprites() {
        boolean inactive = true;
        for (Sprite s : sprites) {
            s.updateSprite();
            inactive &= !s.isActive();
        }
        if (!notified && inactive) {
            for (SpriteContainerObserver obs : observers) {
                obs.waveDestroyedNotification(activationKey);
            }
            notified = true;
        }
    }

    @Override
    public List<Sprite> getSprites() {
        return Collections.unmodifiableList(sprites);
    }
}
