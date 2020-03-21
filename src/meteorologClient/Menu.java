package meteorologClient;

import javax.swing.*;

public class Menu extends JFrame {
    static JMenuBar menuBar;
    public Menu(){
        JMenuItem file = new JMenuItem("File");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About");

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");

        fileMenu.add(file);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        aboutMenu.add(about);
        menuBar.add(aboutMenu);
    }
}
