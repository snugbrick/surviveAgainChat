package cn.miracle;

import cn.miracle.chat.client.SocketFrameClient;
import cn.miracle.chat.server.SocketFrameServer;
import cn.miracle.swingUR.entity.player.PlayerHandler;
import cn.miracle.swingUR.entity.tasks.EntityTasks;
import cn.miracle.swingUR.entity.zombie;

import java.net.UnknownHostException;


/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.13 22:39
 * <p>
 * 已支持kotlin
 */
public class Main {
    public static void main(String[] args) {
        //aSwing s = new aSwing();
        //s.swingStart();
        try {
            new SocketFrameClient();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        new SocketFrameServer();

        EntityTasks.registerEntity(new zombie());
        EntityTasks.registerEntity(new PlayerHandler());
    }
}
