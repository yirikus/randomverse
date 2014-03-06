package cz.terrmith.randomverse.core.ai.movement.formation;

import cz.terrmith.randomverse.core.ai.movement.pattern.MovementPattern;
import cz.terrmith.randomverse.core.geometry.Position;
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
    private MovementPattern mp;

    public SimpleSpriteContainer(Sprite sprite) {
       this(sprite, null);
    }

    public SimpleSpriteContainer(List<Sprite> sprites) {
        this(sprites, null);
    }

    public SimpleSpriteContainer(Sprite sprite, MovementPattern mp) {
        if (sprite == null) {
            throw new IllegalArgumentException("Sprite must not be null");
        }
        this.sprites = new ArrayList<Sprite>();
        this.sprites.add(sprite);
        this.mp = mp;
    }

    public SimpleSpriteContainer(List<Sprite> sprites, MovementPattern mp) {
        if (sprites == null || sprites.isEmpty()) {
            throw new IllegalArgumentException("Sprites must not be null or empty");
        }
        this.sprites = sprites;
        this.mp = mp;
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
        for (Sprite s : sprites) {
            s.updateSprite();
            if (mp != null && s.isActive()) {
                Position newPosition = mp.nextPosition(s);
                s.setPosition(newPosition.getX(), newPosition.getY());
            }
        }
        if (!notified && !isActive()) {
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

    @Override
    public boolean isActive() {
        boolean active = false;
        for (Sprite s : sprites) {
            active = active || s.isActive();
        }
        return active;
    }


}
