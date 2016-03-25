package grafika.gimp.filtry.histogramy;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class HistogramFilter extends FilterWindow {

    private JRadioButton widenRadio;
    private JRadioButton equalizeRadio;

    public HistogramFilter(ImageWindow imageWindow) {
        super("Histogram Filters", imageWindow);
    }

    @Override
    protected void customInit() {
        addComponents();
    }

    private void addComponents() {
        

        widenRadio = new JRadioButton("Widen");
        equalizeRadio = new JRadioButton("Equalize");


        ButtonGroup group = new ButtonGroup();
        group.add(widenRadio);
        group.add(equalizeRadio);

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 5, 2, 5);
        c.anchor = GridBagConstraints.LINE_START;
        add(widenRadio, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(2, 5, 10, 5);
        add(equalizeRadio, c);

    }


    @Override
    public void closeMe() {
        getWindow().setHistogramFilterWindow(null);
    }
}
