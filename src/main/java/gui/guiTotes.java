package gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import filters.KeyInputHandler;
import math.WeightMath;



public class guiTotes {

    private JFrame frame;
    private JMenuBar menuBar;
    private final JPanel contentPane = new JPanel();
    private final JFormattedTextField toteEntryField = new  JFormattedTextField(new NumberFormatter());
    private final JLabel entryToteLabel = new JLabel("Weight on Scale:");
    private final JLabel endToteWeightLabel = new JLabel("Manifest Weight:");
    private final JLabel endToteWeight = new JLabel("0.00");
    private final JLabel errorLabel = new JLabel("ENTER WEIGHT FROM SCALE HEAD!");
    private final ImageIcon bgImage =  getIcon();
    private final JLabel background = new JLabel(new ImageIcon(transparentImageIcon(bgImage,0.5f).getImage().getScaledInstance(750/2,376/2,100)));
    private final JPanel productPanel = new JPanel();
    private final JPanel materialPanel = new JPanel();



    private final String[] buttons_material = new String[] {"Cardboard"," Plastic"};
    private final String[] buttons_product = new String[] {"BSB","Tender","Wing","BS Thigh","Leg Meat"};
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
        frame.setMinimumSize(new Dimension(400,350));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Manifest Calculator");
        //Add menu bar
        this.menuBar = new MenuBar(frame);
        //frame.setJMenuBar(menuBar);
        // Create an ImageIcon from the loaded image
        frame.setIconImage(bgImage.getImage().getScaledInstance(750,376,100));
        frame.add(background);
        // Add the document filter to text field for numeric and length check.

        }


    private void showGUI(JFrame frame) {
        init();
        buildGUI();
        frame.setVisible(true);
        frame.pack();
    }

    private void buildGUI(){
        Container northContainer = new Container();
        Container southContainer = new Container();
        Container westContainer = new Container();
        Container eastContainer = new Container();


        toteEntryField.addKeyListener(new KeyInputHandler(endToteWeight,toteEntryField,currentProduct,isCardboard));

        productPanel.add(new JLabel("-Products-"), BorderLayout.CENTER);
        materialPanel.add(new JLabel("-Materials-"), BorderLayout.CENTER);
        //create radio buttons
        buildRadioButtons(buttons_product, productPanel,productRadioGroup);
        buildRadioButtons(buttons_material,materialPanel,matRadioGroup);

        productPanel.setLayout(new GridLayout(6,1));
        materialPanel.setLayout(new GridLayout(5,0));

        materialPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        northContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        southContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        westContainer.setLayout(new GridLayout(3,1,15,15));
        eastContainer.setLayout(new GridLayout(2,1,5,0));

        northContainer.add(entryToteLabel);
        northContainer.add(toteEntryField);

        southContainer.add(endToteWeightLabel);
        southContainer.add(endToteWeight);

        westContainer.add(northContainer,BorderLayout.NORTH);
        westContainer.add(southContainer,BorderLayout.SOUTH);


        eastContainer.add(productPanel,BorderLayout.NORTH);
        eastContainer.add(materialPanel,BorderLayout.SOUTH);

        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial",Font.BOLD,15));
        errorLabel.setOpaque(false);
        errorLabel.setBackground(new Color(0,0,0));

        westContainer.add(errorLabel,BorderLayout.AFTER_LAST_LINE);

        background.setLayout(new FlowLayout(FlowLayout.CENTER));

        buildJField(toteEntryField, true);

        contentPane.setLayout(new BorderLayout(5,10));

        contentPane.setSize(350,310);
        contentPane.setMinimumSize(new Dimension(350,390));
        contentPane.add(eastContainer, BorderLayout.WEST);
        contentPane.add(westContainer, BorderLayout.EAST);

        contentPane.setOpaque(false);
        contentPane.setBackground(new Color(0,0,0));

        background.add(contentPane, BorderLayout.CENTER);

    }
    private void buildRadioButtons(String[] buttons, JPanel panel,ButtonGroup group){
        int i = 0;
        for ( String button : buttons ){
            JRadioButton b = new JRadioButton(button);
            b.setOpaque(false);
            b.setBackground(new Color(0,0,0));
            //b.setSize(new Dimension(20,20));
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
                            case "leg meat":
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

    private void buildJField(JFormattedTextField field, boolean isEditable){
        field.setColumns(10);
        field.setEditable(isEditable);
        field.setMinimumSize(new Dimension(200,30));
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
