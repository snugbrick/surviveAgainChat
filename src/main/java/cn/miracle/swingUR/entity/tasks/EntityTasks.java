package cn.miracle.swingUR.entity.tasks;

import cn.miracle.swingUR.entity.Entity;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 23:33
 */
public class EntityTasks {
    public static int Health;
    public static int Attack;
    public static int Speed;

    public static void registerEntity(Entity entity) {
        Health = entity.getHealth();
        Attack = entity.getAttack();
        Speed = entity.getSpeed();
    }
}
