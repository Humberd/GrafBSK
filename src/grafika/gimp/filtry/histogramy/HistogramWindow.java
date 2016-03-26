package grafika.gimp.filtry.histogramy;

import grafika.gimp.ImageWindow;
import grafika.gimp.filtry.FilterWindow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class HistogramWindow extends FilterWindow {

    private JCheckBox redCheckBox;
    private JCheckBox greenCheckBox;
    private JCheckBox blueCheckBox;

    private JPanel histogramPanel;

    private XYSeries redSeries;
    private XYSeries greenSeries;
    private XYSeries blueSeries;
    private XYSeriesCollection collection;

    private JFreeChart histogram;

    public HistogramWindow(ImageWindow imageWindow) {
        super("Histogram", imageWindow, false);
    }

    @Override
    protected void customInit() {
        addComponents();
        setShowButtons(false);;
        setShowPreviewImageCheckBox(false);
    }

    private void addComponents() {
        redCheckBox = new JCheckBox("Red");
        redCheckBox.setSelected(true);
        redCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHistogram();
            }
        });
        greenCheckBox = new JCheckBox("Green");
        greenCheckBox.setSelected(true);
        greenCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHistogram();
            }
        });
        blueCheckBox = new JCheckBox("Blue");
        blueCheckBox.setSelected(true);
        blueCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHistogram();
            }
        });

        histogramPanel = new JPanel(new BorderLayout());

        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridx = 0;
        c.gridy = 0;
        add(histogramPanel, c);

        JPanel checkBoxPanel = new JPanel();
        c.gridx = 0;
        c.gridy = 1;
        add(checkBoxPanel, c);
        checkBoxPanel.add(redCheckBox);
        checkBoxPanel.add(greenCheckBox);
        checkBoxPanel.add(blueCheckBox);

        setHistogram();
    }

    private void setHistogram() {
        redSeries = new XYSeries("Red", true, false);
        greenSeries = new XYSeries("Green", true, false);
        blueSeries = new XYSeries("Blue", true, false);
        updateHistogram();
        collection = new XYSeriesCollection();
        collection.addSeries(redSeries);
        collection.addSeries(greenSeries);
        collection.addSeries(blueSeries);
        String plotTitle = "Histogram";
        String xAxisTitle = "Color values";
        String yAxisTitle = "Amount";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean legend = true;
        boolean toolTips = false;
        boolean urls = false;
        histogram = ChartFactory.createXYLineChart(plotTitle, xAxisTitle, yAxisTitle, collection, orientation, legend, toolTips, urls);
        ChartPanel chartPanel = new ChartPanel(histogram);
        chartPanel.setPreferredSize(new Dimension(400, 200));
        
        XYPlot plot = histogram.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        plot.setRenderer(renderer);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);

        histogramPanel.removeAll();
        histogramPanel.add(chartPanel, BorderLayout.CENTER);

    }

    public void updateHistogram() {
        BufferedImage histogramImage = null;
        if (getOuterPreviewImage() != null) {
            histogramImage = getOuterPreviewImage();
        } else if (getImage() != null) {
            histogramImage = getImage();
        } else {
            return;
        }

        boolean redChecked = redCheckBox.isSelected();
        boolean greenChecked = greenCheckBox.isSelected();
        boolean blueChecked = blueCheckBox.isSelected();

        int[] reds = new int[256];
        int[] greens = new int[256];
        int[] blues = new int[256];

        for (int y = 0; y < histogramImage.getHeight(); y++) {
            for (int x = 0; x < histogramImage.getWidth(); x++) {
                Color pixelColor = new Color(histogramImage.getRGB(x, y));
                int red = pixelColor.getRed();
                int green = pixelColor.getGreen();
                int blue = pixelColor.getBlue();
                reds[red]++;
                greens[green]++;
                blues[blue]++;
            }
        }

        redSeries.clear();
        greenSeries.clear();
        blueSeries.clear();
        if (redChecked) {
            for (int i = 0; i < reds.length; i++) {
                redSeries.addOrUpdate(i, reds[i]);
            }
        }
        if (greenChecked) {
            for (int i = 0; i < greens.length; i++) {
                greenSeries.addOrUpdate(i, greens[i]);
            }
        }
        if (blueChecked) {
            for (int i = 0; i < blues.length; i++) {
                blueSeries.addOrUpdate(i, blues[i]);
            }
        }
    }

    @Override
    public void closeMe() {
        getWindow().getImageEditor().setHistogramWindow(null);
    }
}
