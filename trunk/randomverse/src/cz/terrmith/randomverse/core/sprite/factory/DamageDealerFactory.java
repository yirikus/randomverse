package cz.terrmith.randomverse.core.sprite.factory;

import cz.terrmith.randomverse.core.sprite.properties.Damage;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 5.3.14
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public interface DamageDealerFactory extends SpriteFactory {

    Damage getDamage();
}
