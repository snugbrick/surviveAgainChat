package cn.miracle.swingUR.entity.player;

import cn.miracle.swingUR.entity.Entity;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 23:28
 */
public interface Player extends Entity {
    int health();
    int speed();
    int attack();
}
