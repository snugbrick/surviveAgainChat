package cn.miracle.chat.server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author MiracleUR -> github.com/snugbrick
 * @version 1.0.0 2023.12.29 23:34
 */
public class SocketFrameServer extends JFrame {
    private final JTextArea textArea;
    private final JTextField textField;
    private int port = 80;
    private ServerSocket connect;
    private final JPanel southPanel;

    public SocketFrameServer() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int w = screenSize.width;
        int h = screenSize.height;
        setSize(w / 2, h / 2);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/cn/miracle/Image/totem.png").getImage());
        setLocation(w / 4, h / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Survive Again Server");

        textArea = new JTextArea();
        textArea.setForeground(Color.RED);
        textArea.setEditable(false);
        add(new JScrollPane(textArea));

        JButton changePortButton = new JButton("change port");
        JButton startCloseRoom = new JButton("start&close");
        textField = new JTextField();
        southPanel = new JPanel(new BorderLayout());
        southPanel.add(textField, BorderLayout.CENTER);
        southPanel.add(changePortButton, BorderLayout.EAST);
        southPanel.add(startCloseRoom, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);

        changePortButton.addActionListener(event -> {

        });
        startCloseRoom.addActionListener(event -> {
            try {
                connect = new ServerSocket(port);
                textArea.append("[ log ] started server on: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Thread thread = new Thread(() -> {
                try {
                    Socket clientSocket = connect.accept();
                    InputStream in = clientSocket.getInputStream();//接受
                    PrintWriter outP = new PrintWriter(clientSocket.getOutputStream(), true);//发送

                    if (clientSocket.isConnected()) {
                        outP.write("you already connect, welcome to " + InetAddress.getLocalHost().getHostAddress());
                    }
                    byte[] messageFromClient;
                    while ((messageFromClient = in.readAllBytes()) != null) {
                        String[] messageClient = new String[2];
                        if (messageFromClient.length > 0) {
                            messageClient[0] = String.valueOf(messageFromClient[0]);
                            outP.write(messageClient[0] + ":" + messageClient[1] + "\n");//写入流
                            break;
                        }
                    }
                    /*
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    */
                } catch (IOException e) {
                    textArea.append("[ log ] started has a IOException\n");
                }
            });
            thread.start();
        });
        setVisible(true);
    }
}