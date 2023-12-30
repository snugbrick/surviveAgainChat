package cn.miracle.swingUR.entity.player;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 23:40
 */
public class PlayerHandler implements Player{
    @Override
    public int health() {
        return 20;
    }

    @Override
    public int speed() {
        return 5;
    }

    @Override
    public int attack() {
        return 2;
    }
}
