// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private MainPanel() {
    super(new BorderLayout(5, 5));
    System.out.println(UIManager.getLookAndFeelDefaults().get("TabbedPane.selectedLabelShift"));
    System.out.println(UIManager.getLookAndFeelDefaults().get("TabbedPane.labelShift"));

    int slsiv = UIManager.getLookAndFeelDefaults().getInt("TabbedPane.selectedLabelShift");
    SpinnerNumberModel slsModel = new SpinnerNumberModel(slsiv, -5, 5, 1);
    slsModel.addChangeListener(e -> {
      SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
      Integer offset = source.getNumber().intValue();
      UIManager.put("TabbedPane.selectedLabelShift", offset);
      SwingUtilities.updateComponentTreeUI(getTopLevelAncestor());
    });

    int lsiv = UIManager.getLookAndFeelDefaults().getInt("TabbedPane.labelShift");
    SpinnerNumberModel lsModel = new SpinnerNumberModel(lsiv, -5, 5, 1);
    lsModel.addChangeListener(e -> {
      SpinnerNumberModel source = (SpinnerNumberModel) e.getSource();
      Integer offset = source.getNumber().intValue();
      UIManager.put("TabbedPane.labelShift", offset);
      SwingUtilities.updateComponentTreeUI(getTopLevelAncestor());
    });

    Box box1 = Box.createHorizontalBox();
    box1.setBorder(BorderFactory.createTitledBorder("UIManager.put(\"TabbedPane.selectedLabelShift\", offset)"));
    box1.add(new JLabel("offset = "));
    box1.add(new JSpinner(slsModel));
    box1.add(Box.createHorizontalGlue());

    Box box2 = Box.createHorizontalBox();
    box2.setBorder(BorderFactory.createTitledBorder("UIManager.put(\"TabbedPane.labelShift\", offset)"));
    box2.add(new JLabel("offset = "));
    box2.add(new JSpinner(lsModel));
    box2.add(Box.createHorizontalGlue());

    JPanel p = new JPanel(new GridLayout(2, 1));
    p.add(box1);
    p.add(box2);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("title 0", new ColorIcon(Color.RED), new JScrollPane(new JTree()));
    tabbedPane.addTab("title 1", new ColorIcon(Color.GREEN), new JButton("button"));
    tabbedPane.addTab("title 2", new ColorIcon(Color.BLUE), new JLabel("label"));
    tabbedPane.addTab("title 3", new JPanel());
    tabbedPane.setTabComponentAt(3, new JLabel("label", new ColorIcon(Color.ORANGE), SwingConstants.LEFT));

    add(p, BorderLayout.NORTH);
    add(tabbedPane);
    setPreferredSize(new Dimension(320, 240));
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
      Toolkit.getDefaultToolkit().beep();
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

class ColorIcon implements Icon {
  private final Color color;

  protected ColorIcon(Color color) {
    this.color = color;
  }

  @Override public void paintIcon(Component c, Graphics g, int x, int y) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.translate(x, y);
    g2.setPaint(color);
    g2.fillRect(0, 0, getIconWidth(), getIconHeight());
    g2.dispose();
  }

  @Override public int getIconWidth() {
    return 16;
  }

  @Override public int getIconHeight() {
    return 16;
  }
}
