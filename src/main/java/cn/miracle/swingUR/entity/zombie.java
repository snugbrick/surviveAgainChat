package cn.miracle.swingUR.entity;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 23:29
 */
public class zombie implements Entity {
    @Override
    public int health() {
        return 10;
    }

    @Override
    public int speed() {
        return 2;
    }

    @Override
    public int attack() {
        return 1;
    }
}
