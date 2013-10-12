package cz.terrmith.randomverse.core.sprite.creator;

import cz.terrmith.randomverse.core.sprite.SpriteCollection;

/**
 * Factory for ship part items
 */
public class SpriteCreatorFactory {

    private SpriteCollection spriteCollection;

    public SpriteCreatorFactory(SpriteCollection spriteCollection){
        this.spriteCollection = spriteCollection;
    }

    SpriteCreator createSimpleProjectileCreator(){
        return new SimpleProjectileCreator(spriteCollection);
    }
}
