package cn.miracle.swingUR;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class aSwing {
    public void swingStart() {
        EventQueue.invokeLater(() -> {
            JFrame jFrame = new JFrame();

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            int w = screenSize.width;
            int h = screenSize.height;
            jFrame.setSize(w / 2, h / 2);

            jFrame.setLocationRelativeTo(null);

            jFrame.setIconImage(new ImageIcon("src/main/java/cn/miracle/Image/totem.png").getImage());

            jFrame.setLocation(w / 4, h / 4);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setTitle("Survive Again");

            JPanel panel = new JPanel(new BorderLayout());
            jFrame.getContentPane().add(panel);

            JLabel label = new JLabel("a?");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label, BorderLayout.CENTER);

            JButton AButton = new JButton("a?");
            panel.add(AButton, BorderLayout.SOUTH);
            AButton.addActionListener((e -> {
                JOptionPane.showMessageDialog(null, "点击上面那个");
            }));

            JButton jButton = new addButton().add("fir");
            jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            jButton.setAlignmentY(Component.CENTER_ALIGNMENT);

            jButton.addActionListener((event) -> {
                Long l = event.getWhen();

                JOptionPane.showMessageDialog(null, l);
            });

            // 在面板中添加图片标签和按钮
            panel.add(jButton, BorderLayout.NORTH); // 将按钮添加到面板的北部位置

            ImageIcon image = new ImageIcon("src/main/java/cn/miracle/Image/pack.png");
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(image);
            panel.add(imageLabel, BorderLayout.CENTER); // 将图片标签添加到面板的中央位置

            Logger.getGlobal().info("界面加载成功");
            jFrame.setVisible(true);
        });
    }
}

