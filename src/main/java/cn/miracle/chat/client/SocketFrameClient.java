package cn.miracle.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

//客户端的外观与联机(发送消息&获取已有消息)
public class SocketFrameClient extends JFrame {
    private Scanner putIn;
    private final JTextArea textArea;
    private final JTextField textField;
    private final String userIp = InetAddress.getLocalHost().getHostAddress();
    private String port;
    private Socket server;
    private final JPanel southPanel;

    public SocketFrameClient() throws UnknownHostException {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int w = screenSize.width;
        int h = screenSize.height;
        setSize(w / 2, h / 2);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/cn/miracle/Image/totem.png").getImage());
        setLocation(w / 4, h / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Survive Again Chat");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 在窗口关闭时关闭 Socket
                try {
                    if (server != null) {
                        server.close();
                        Logger.getGlobal().info(userIp + " Socket closed");
                        appendLog("[ log ] Socket closed");
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    Logger.getGlobal().warning(userIp + " Socket close exception");
                    appendLog("[ log ] Socket close exception");
                }
            }
        });

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
        textArea.append(userIp + " Welcome to Survive Again Chat !\n");

        JButton sendButton = new JButton("     send     ");
        JButton ChangeChatRoom = new JButton("ChatRoom");
        textField = new JTextField();

        southPanel = new JPanel(new BorderLayout());

        southPanel.add(textField, BorderLayout.CENTER);
        southPanel.add(sendButton, BorderLayout.EAST);
        southPanel.add(ChangeChatRoom, BorderLayout.WEST);

        sendButton.addActionListener(event -> {//send action
            String message = textField.getText();
            if (server != null) {
                try (OutputStream toServer = server.getOutputStream()) {
                    byte[] packageClient = new byte[2];
                    packageClient[0] = userIp.getBytes(StandardCharsets.UTF_8)[0];//发送ip
                    packageClient[1] = message.getBytes(StandardCharsets.UTF_8)[0];//发送信息
                    //向服务端写入数据包
                    toServer.write(packageClient);

                    if (message.isEmpty()) {
                        Logger.getGlobal().info(userIp + " has chatted: " + message);
                        appendLog("[ log ]" + userIp + " has chatted: " + message);
                    }
                } catch (IOException e) {
                    Logger.getGlobal().warning(userIp + " client send chat exception");
                    appendLog("[ log ]" + userIp + " client send chat exception");
                }
            }
            textField.setText("");
        });

        ChangeChatRoom.addActionListener(event -> {//change chat room action
            JPanel panel = new JPanel();
            JTextField ipField = new JTextField(10);
            JTextField portField = new JTextField(10);
            panel.add(new JLabel("IP:"));
            panel.add(ipField);
            panel.add(Box.createHorizontalStrut(15));
            panel.add(new JLabel("port:"));
            panel.add(portField);

            while (true) {
                int result = JOptionPane.showConfirmDialog(SocketFrameClient.this, panel, "input IP and port", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.CANCEL_OPTION) {
                    break;
                }//判断按键

                String ip = ipField.getText();
                this.port = portField.getText();

                if (ip.isEmpty() || port.isEmpty()) {//ip和port非空
                    JOptionPane.showMessageDialog(SocketFrameClient.this, "IP and port can not be null");
                } else {
                    try {
                        InetAddress inetAddress = InetAddress.getByName(ip);
                        String uip = inetAddress.getHostAddress();

                        Logger.getGlobal().info("Change Room Request , user: " + userIp + " to: " + ip + ":" + this.port);
                        appendLog("[ log ]" + "Change Room Request , user: " + userIp + " to: " + ip + ":" + this.port);
                        try {
                            //连接目标服务器
                            Thread thread = new Thread(() -> {
                                try {
                                    server = new Socket(uip, Integer.parseInt(this.port));
                                    putIn = new Scanner(server.getInputStream(), StandardCharsets.UTF_8);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (putIn.hasNext()) {
                                    String line = putIn.nextLine();
                                    //从服务器读入
                                    textArea.append(line);
                                    textArea.append("\n");//打印服务器的output  TODO
                                }
                            });
                            thread.start();
                        } catch (IllegalArgumentException exception) {//非法就意味着找不到
                            textArea.append("UnknownHostException :" + ip + ":" + this.port + "\n");
                            Logger.getGlobal().warning(userIp + " The user's change to the void IP");
                            appendLog("[ log ]" + userIp + " The user's change to the void IP");
                        }

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(SocketFrameClient.this, "port must be int");
                        Logger.getGlobal().warning(userIp + " user's port is not a int");
                        appendLog("[ log ]" + userIp + " user's port is not a int");
                        continue;
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                    JOptionPane.showMessageDialog(SocketFrameClient.this, "done");
                    break;
                }
            }
        });
        add(southPanel, BorderLayout.SOUTH);

        Logger.getGlobal().info(userIp + " UI loaded");
        setVisible(true);
    }

    public void appendLog(String logMessage) {
        textArea.setForeground(Color.RED);
        textArea.append(logMessage + "\n");
        textArea.setForeground(Color.BLACK);
        textArea.setCaretPosition(textArea.getDocument().getLength()); //滚动到底部
    }
}

