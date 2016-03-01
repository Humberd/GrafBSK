package grafikakomputerowa;

import bsk.szyfrowanie1.MatrixTransposition1;
import bsk.szyfrowanie1.MatrixTransposition2;
import bsk.szyfrowanie1.RailFence;
import grafika.zad1.Prymitywy;
import grafika.zad2.ImageEditor;
import bsk.szyfrowanie1.TemplatePanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.*;

public class MyFrame extends JFrame {

    private JPanel mainPanel = new JPanel();
    private Container frameContainer;
    private Map<JMenuItem, JPanel> programsMap = new LinkedHashMap<>();
    private Map<JMenuItem, JPanel> bskZad1Map = new LinkedHashMap<>();

    public MyFrame() {
        super("Grafika Komputerowa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 600));
        addComponents();
        addKeyListenerEscape();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponents() {
        frameContainer = getContentPane();

        addMenuBar();
        //dodaje glowny ekran
        frameContainer.add(mainPanel);
    }

    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Computer Graphics");
        JMenuItem menuItem = addMenuItem(true, new JMenuItem("1. Prymitywy"), new Prymitywy(), programsMap, new Runnable() {
            @Override
            public void run() {
                System.out.println("11111");
            }

        });
        menuItem = addMenuItem(true, new JMenuItem("2. PPM"), new ImageEditor(), programsMap, new Runnable() {
            @Override
            public void run() {
                System.out.println("22222");
            }

        });
        //////////////////////////////////
        JMenu menu2 = new JMenu("Web Security");
        JMenu menu2Zad1 = new JMenu("1. Transposition");

        JMenuItem menuItem2 = addMenuItem(false, new JMenuItem("1. Rail Fence"), new TemplatePanel(new RailFence()), bskZad1Map, new Runnable() {
            @Override
            public void run() {
                System.out.println("BSK: Transposition: Rail Fence");
            }
        });

        menuItem2 = addMenuItem(false, new JMenuItem("2. Matrix Transposition1"), new TemplatePanel(new MatrixTransposition1()), bskZad1Map, new Runnable() {
            @Override
            public void run() {
                System.out.println("BSK: Transposition: Matrix Transposition 1");
            }

        });
        
        menuItem2 = addMenuItem(false, new JMenuItem("3. Matrix Transposition2"), new TemplatePanel(new MatrixTransposition2()), bskZad1Map, new Runnable() {
            @Override
            public void run() {
                System.out.println("BSK: Transposition: Matrix Transposition 2");
            }
        });

        menu2.add(menu2Zad1);
        //////////////////////////////////
        //dolaczam wrzystkie rzeczy do menu
        attachMenuItems(programsMap, menu);
        attachMenuItems(bskZad1Map, menu2Zad1);
        //dolaczam menu do menuBar
        menuBar.add(menu);
        menuBar.add(menu2);
        //ustawiam menuBar
        setJMenuBar(menuBar);
    }

    //co,co,gdzie,listener
    private JMenuItem addMenuItem(boolean defaultScreen, JMenuItem menuItem, JPanel panel, Map<JMenuItem, JPanel> map, Runnable runnable) {
        map.put(menuItem, panel);
        if (defaultScreen) {
            setMainPanel(panel);
        }
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runnable.run();
                        setMainPanel(panel);
                        getFrameContainer().removeAll();
                        getFrameContainer().add(getMainPanel());
                        getFrameContainer().revalidate();
                        getFrameContainer().repaint();
                        System.out.println(getFrameContainer().getComponent(0).getName());
                    }

                }).start();
            }
        });
        return menuItem;
    }

    private void attachMenuItems(Map<JMenuItem, JPanel> map, JMenu menu) {
        for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
            Entry<JMenuItem, JPanel> itemSet = (Entry<JMenuItem, JPanel>) iter.next();
            JMenuItem item = itemSet.getKey();
            menu.add(item);
        }
    }

    private void addKeyListenerEscape() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                return false;
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public Container getFrameContainer() {
        return frameContainer;
    }

    public void setFrameContainer(Container frameContainer) {
        this.frameContainer = frameContainer;
    }

    public Map<JMenuItem, JPanel> getProgramsMap() {
        return programsMap;
    }

    public void setProgramsMap(Map<JMenuItem, JPanel> programsMap) {
        this.programsMap = programsMap;
    }

    public Map<JMenuItem, JPanel> getBskZad1Map() {
        return bskZad1Map;
    }

    public void setBskZad1Map(Map<JMenuItem, JPanel> bskZad1Map) {
        this.bskZad1Map = bskZad1Map;
    }

}
