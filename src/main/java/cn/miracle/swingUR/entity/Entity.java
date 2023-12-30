package cn.miracle.swingUR.entity;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 23:24
 */
public interface Entity {
    int health();

    int speed();

    int attack();

    default int getHealth() {
        return health();
    }

    default int getSpeed() {
        return speed();
    }

    default int getAttack() {
        return attack();
    }
}
