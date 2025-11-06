package gui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import filters.KeyInputHandler;
import math.WeightMath;



public class guiTotes {

    private JFrame frame;
    private JMenuBar menuBar;
    private final JPanel contentPane = new JPanel();
    private final RoundedNumberField toteEntryField = new RoundedNumberField(new NumberFormatter(),20);
    private final JLabel entryToteLabel = new JLabel("Weight on Scale:");
    private final JLabel endToteWeightLabel = new JLabel("Manifest Weight:");
    private final JLabel endToteWeight = new JLabel("0.00");
    private final JLabel errorLabel = new JLabel("ENTER WEIGHT FROM SCALE HEAD!");
    private final ImageIcon bgImage =  getIcon();
    private final JLabel background = new JLabel(new ImageIcon(transparentImageIcon(bgImage,0.5f).getImage().getScaledInstance(Math.round(750*1.35f),Math.round(376*1.35f),100)));
    private final JPanel productPanel = new JPanel();
    private final JPanel materialPanel = new JPanel();



    private final String[] buttons_material = new String[] {"Cardboard","Plastic"};
    private final String[] buttons_product = new String[] {"BSB","Tender","Wing","BS Thigh","WL"};
    private final ButtonGroup productRadioGroup = new ButtonGroup();
    private final ButtonGroup matRadioGroup = new ButtonGroup();

    private static WeightMath.Product currentProduct = WeightMath.Product.BSB;
    public static boolean isCardboard = true;


/**
 * add components
 * add buttons
 * auto-clear/add clear button
 * */
    public guiTotes(JFrame frame){
        this.frame = frame;
        showGUI(frame);
    }


    private void init() {


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800,700));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Manifest Calculator");
        //Add menu bar
        this.menuBar = new MenuBar(frame);
        frame.setJMenuBar(menuBar);
        // Create an ImageIcon from the loaded image
        frame.setIconImage(bgImage.getImage().getScaledInstance(750,376,100));
        // Add the document filter to text field for numeric and length check.

        }


    private void showGUI(JFrame frame) {
        init();
        buildGUI();
        frame.pack();
        frame.setResizable(false);
        frame.setSize(800,700);
        frame.setVisible(true);
        System.out.println("Frame size: " + frame.getSize());
        System.out.println("Content pane size: " + frame.getContentPane().getSize());
        System.out.println("Background size: " + background.getSize());
    }

    private void buildGUI(){
        JPanel eastContainer = new JPanel();

        // --- WEST PANEL (Input & Error Section) ---
        JPanel westContainer = new JPanel();
        westContainer.setLayout(new BoxLayout(westContainer, BoxLayout.Y_AXIS));
        westContainer.setPreferredSize(new Dimension(250, 600));  // fixed width
        westContainer.setOpaque(false);

// ---------- North section (Entry field) ----------
        JPanel northContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        northContainer.setOpaque(false);
        entryToteLabel.setFont(new Font("Arial",Font.BOLD,24));
        toteEntryField.addKeyListener(new KeyInputHandler(endToteWeight,toteEntryField,currentProduct,isCardboard));
        toteEntryField.setPreferredSize(new Dimension(300,50));
        northContainer.add(entryToteLabel);
        northContainer.add(toteEntryField);

// ---------- South section (End weight field, centered) ----------
        JPanel southContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        southContainer.setOpaque(false);
        endToteWeightLabel.setFont(new Font("Arial",Font.BOLD,24));
        endToteWeight.setFont(new Font("Arial",Font.BOLD,24));
        southContainer.add(endToteWeightLabel);
        southContainer.add(endToteWeight);

// ---------- Error label ----------
        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        errorPanel.setOpaque(false);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorPanel.add(errorLabel);

// ---------- Add panels vertically with spacing ----------
        westContainer.add(Box.createVerticalStrut(10));   // top margin
        westContainer.add(northContainer);
        westContainer.add(Box.createVerticalStrut(2));   // space
        westContainer.add(southContainer);                // centered
        westContainer.add(Box.createVerticalStrut(10));   // space
        westContainer.add(errorPanel);
        westContainer.add(Box.createVerticalGlue());      // pushes everything up neatly


        northContainer.setBackground(new Color(0,0,0));
        northContainer.setOpaque(false);
        southContainer.setBackground(new Color(0,0,0));
        southContainer.setOpaque(false);
        westContainer.setBackground(new Color(0,0,0));
        westContainer.setOpaque(false);
        eastContainer.setBackground(new Color(0,0,0));
        eastContainer.setOpaque(false);



        //Center Label to BorderLayout
        JLabel matLabel = new JLabel("-Materials-");
        JPanel matPan = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        matPan.add(matLabel);
        //Center Label to BorderLayout
        JLabel prodLabel = new JLabel("-Products-");
        JPanel prodPan = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        prodPan.add(prodLabel);

        productPanel.add(prodPan, BorderLayout.NORTH);
        materialPanel.add(matPan,BorderLayout.NORTH);
        //create radio buttons
        buildRadioButtons(buttons_product, productPanel,productRadioGroup);
        buildRadioButtons(buttons_material,materialPanel,matRadioGroup);

        productPanel.setLayout(new GridLayout(6,1,10,0));
        materialPanel.setLayout(new GridLayout(5,0,10,0));

        materialPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        eastContainer.setLayout(new GridLayout(2,1,5,10));



        eastContainer.add(productPanel,BorderLayout.NORTH);
        eastContainer.add(materialPanel,BorderLayout.SOUTH);

        background.setLayout(new BorderLayout());
        westContainer.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        eastContainer.setBorder(BorderFactory.createLineBorder(Color.cyan));

        contentPane.setLayout(new BorderLayout(5,10));

        background.add(westContainer);
        contentPane.add(eastContainer, BorderLayout.WEST);
        contentPane.add(background, BorderLayout.CENTER);
        contentPane.setOpaque(false);
        contentPane.setBackground(new Color(0,0,0));
        System.out.println(contentPane.getBounds());

        System.out.println(background.getBounds());
        frame.add(contentPane);

    }

    private void buildRadioButtons(String[] buttons, JPanel panel,ButtonGroup group){
        int i = 0;
        for ( String button : buttons ){
            JRadioButton b = new JRadioButton(button);
            b.setOpaque(false);
            b.setBackground(new Color(0,0,0));
            if(i == 0)
                b.setSelected(true);
            b.addActionListener(e -> {
                    String tote = e.paramString().substring(e.paramString().indexOf("=")+1);
                    String toteType = tote.substring(0,tote.indexOf(","));
                        switch (toteType.toLowerCase()) {
                            case "bsb":
                            case "tender":
                            case "wing":
                            case "bs thigh":
                            case "wl":
                                String t = toteType;
                                if (toteType.contains(" "))
                                    t = toteType.replace(" ","_");
                                setCurrentProduct(WeightMath.Product.valueOf(t.toUpperCase()));
                                break;
                            case "cardboard":
                                isCardboard = true;
                                break;
                            case "plastic":
                                isCardboard = false;
                                break;
                        }
            });

            group.add(b);
            panel.add(b);
            i++;
        }

        //invis background
        panel.setOpaque(false);
        panel.setBackground(new Color(0,0,0));

    }

    private ImageIcon getIcon(){
        String imagePath = "/icon/simmonspf.png";
        BufferedImage image = null;

        // Try-with-resources automatically closes the InputStream
        try (InputStream inputStream = getClass().getResourceAsStream(imagePath)) {

            // Step 1: Check if the resource was found
            if (inputStream == null) {
                System.err.println("Resource not found: " + imagePath);
                return null;
            }

            // Step 2: Attempt to read the image
            image = ImageIO.read(inputStream);

            // Step 3: Check if ImageIO.read() was successful
            if (image == null) {
                System.err.println("ImageIO.read failed to decode the stream. Is the file corrupted or an unsupported format?");
            }

        } catch (IOException e) {
            System.err.println("An IOException occurred while reading the image.");
            e.printStackTrace();
        }

        return new ImageIcon(image);
    }

    private ImageIcon transparentImageIcon(ImageIcon originalIcon, float opacity) {
        // Ensure opacity is within the valid range [0.0, 1.0]
        if (opacity < 0.0f) opacity = 0.0f;
        if (opacity > 1.0f) opacity = 1.0f;
        
        // Get dimensions from the original icon
        int w = originalIcon.getIconWidth();
        int h = originalIcon.getIconHeight();

        // Create a new BufferedImage with an alpha channel
        BufferedImage newIconImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        // Get the Graphics2D object to draw on the new image
        Graphics2D g = newIconImg.createGraphics();

        // Set the composite with the specified opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        // Draw the original icon onto the new, transparent image
        originalIcon.paintIcon(null, g, 0, 0);
        
        // Dispose of the graphics object
        g.dispose();

        // Set this class's image to the new, transparent image
        return new ImageIcon(newIconImg);
    }

    public static WeightMath.Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(WeightMath.Product currProdct) {
        currentProduct = currProdct;
    }
    public static boolean getIsCardboard(){
        return isCardboard;
    }
}
