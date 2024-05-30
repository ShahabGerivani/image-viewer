import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

// Resources:
// https://stackoverflow.com/questions/13547361/how-to-use-margins-and-paddings-with-java-gridlayout
// https://stackoverflow.com/questions/3495926/can-i-catch-multiple-java-exceptions-in-the-same-catch-clause

public class ImageViewerGUI extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = ".\\";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel() {
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(null);
        mainPanel.setLayout(new GridLayout(2, 1));

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));

        // The app's label
        mainPanel.add(new JLabel("Image viewer", SwingConstants.CENTER));

        // Padding
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Defining all the buttons
        selectFileButton = new JButton("Choose image");
        showImageButton = new JButton("Show image");
        brightnessButton = new JButton("Brightness");
        grayscaleButton = new JButton("Gray scale");
        resizeButton = new JButton("Resize");
        closeButton = new JButton("Exit");

        // Adding action listeners to all the buttons
        selectFileButton.addActionListener(this);
        showImageButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        resizeButton.addActionListener(this);
        closeButton.addActionListener(this);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel() {
        JPanel resizePanel = new JPanel();
//        resizePanel.setLayout(null);
        resizePanel.setLayout(new GridLayout(4, 2));
        // Padding
        resizePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Creating all the necessary components
        if (widthTextField == null) {
            widthTextField = new JTextField();
        }
        if (heightTextField == null) {
            heightTextField = new JTextField();
        }
        if (backButton == null) {
            backButton = new JButton("Back");
            backButton.addActionListener(this);
        }
        if (showResizeButton == null) {
            showResizeButton = new JButton("Result");
            showResizeButton.addActionListener(this);
        }

        // Adding all the components to the panel
        resizePanel.add(new JLabel("Resize ", SwingConstants.RIGHT));
        resizePanel.add(new JLabel("section", SwingConstants.LEFT));
        resizePanel.add(new JLabel("Width:", SwingConstants.CENTER));
        resizePanel.add(widthTextField);
        resizePanel.add(new JLabel("Height:", SwingConstants.CENTER));
        resizePanel.add(heightTextField);
        resizePanel.add(backButton);
        resizePanel.add(showResizeButton);

        this.add(resizePanel);
    }

    public void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
//        brightnessPanel.setLayout(null);
        brightnessPanel.setLayout(new GridLayout(2, 2));
        // Padding
        brightnessPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Creating all the necessary components
        if (brightnessTextField == null) {
            brightnessTextField = new JTextField();
        }
        if (backButton == null) {
            backButton = new JButton("Back");
            backButton.addActionListener(this);
        }
        if (showBrightnessButton == null) {
            showBrightnessButton = new JButton("Result");
            showBrightnessButton.addActionListener(this);
        }

        // Adding all the components to the panel
        brightnessPanel.add(new JLabel("Enter f (must be between 0 and 1)", SwingConstants.CENTER));
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(backButton);
        brightnessPanel.add(showBrightnessButton);

        this.add(brightnessPanel);
    }

    public void chooseFileImage() {
        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
    }

    public void showOriginalImage() {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        JLabel pictureLabel = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            pictureLabel.setIcon(new ImageIcon(bufferedImage));
        }  catch (IOException | IllegalArgumentException | NullPointerException e) {
            pictureLabel.setText("Unable to read the image!");
        }
        tempPanel.add(pictureLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage() {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        JLabel pictureLabel = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);

            // Applying the gray scale effect
            ImageFilter grayFilter = new GrayFilter(true, 50);
            ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), grayFilter);
            Image grayImage = Toolkit.getDefaultToolkit().createImage(producer);

            pictureLabel.setIcon(new ImageIcon(grayImage));
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            pictureLabel.setText("Unable to read the image!");
        }
        tempPanel.add(pictureLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showResizeImage(int w, int h) {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        JLabel pictureLabel = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (h > 0 && w > 0) {
                pictureLabel.setIcon(new ImageIcon(bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT)));
            } else {
                pictureLabel.setText("Please enter positive integers for width and height.");
            }
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            pictureLabel.setText("Unable to read the image");
        }
        tempPanel.add(pictureLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showBrightnessImage(float f) {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        JLabel pictureLabel = new JLabel();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (f >= 0 && f <= 1) {
                // Changing the brightness of the image & displaying it
                RescaleOp op = new RescaleOp(f, 0, null);
                pictureLabel.setIcon(new ImageIcon(op.filter(bufferedImage, bufferedImage)));
            } else {
                pictureLabel.setText("Please enter a number between 0 and 1");
            }
        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            pictureLabel.setText("Unable to read the image");
        }
        tempPanel.add(pictureLabel);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resizeButton) {
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource() == showImageButton) {
            this.showOriginalImage();
        } else if (e.getSource() == grayscaleButton) {
            this.grayScaleImage();
        } else if (e.getSource() == showResizeButton) {
            try {
                w = Integer.parseInt(widthTextField.getText());
                h = Integer.parseInt(heightTextField.getText());
            } catch (NumberFormatException exp) {
                w = h = -1; // This will get handled in the showResizeImage method
            } finally {
                this.showResizeImage(w, h);
            }
        } else if (e.getSource() == brightnessButton) {
            this.getContentPane().removeAll();
            this.brightnessPanel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource() == showBrightnessButton) {
            try {
                brightenFactor = Float.parseFloat(brightnessTextField.getText());
            } catch (NumberFormatException exp) {
                brightenFactor = -1; // This will get handled in the showBrightnessButton method
            } finally {
                this.showBrightnessImage(brightenFactor);
            }
        } else if (e.getSource() == selectFileButton) {
            this.chooseFileImage();
        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}
