package gui;

import filters.KeyInputHandler;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import math.WeightMath;



public class guiTotes {

    private final JFrame frame = new JFrame();
    private final JPanel contentPane = new JPanel();
    private final Container contentPaneNums = new JPanel();
    private final JFormattedTextField toteEntryField = new  JFormattedTextField(new NumberFormatter());
    private final JLabel entryToteLabel = new JLabel("Weight on Scale:");
    private final JLabel endToteWeightLabel = new JLabel("Manifest Weight:");
    private final JLabel endToteWeight = new JLabel("0.00");
    private final JLabel errorLabel = new JLabel("ENTER WEIGHT FROM SCALE HEAD!");
    private final JLabel background = new JLabel(new ImageIcon(getIcon().getImage().getScaledInstance(750/2,376/2,100)));
    private final JPanel productPanel = new JPanel();
    private final JPanel materialPanel = new JPanel();


    private final String[] buttons_material = new String[] {"Cardboard"," Plastic"};
    private final String[] buttons_product = new String[] {"BSB","Tender","Wing","BS Thigh","Leg Meat"};
    private final ButtonGroup productRadioGroup = new ButtonGroup();
    private final ButtonGroup matRadioGroup = new ButtonGroup();

    private static WeightMath.Product currentProduct = WeightMath.Product.BSB;
    public static boolean isCardboard = true, isPlastic = false;


/**
 * add components
 * add buttons
 * auto-clear/add clear button
 * */
    public guiTotes(){
        showGUI(frame);
    }


    private void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400,350));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Short-Weight Calculator");
        // Create an ImageIcon from the loaded image
        ImageIcon icon = new ImageIcon(getIcon().getImage());
        frame.setIconImage(icon.getImage().getScaledInstance(750,376,100));
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
        background.setSize(350,310);

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
                                isPlastic = false;
                                break;
                            case "plastic":
                                isPlastic = true;
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
        BufferedImage webpImage = null;
        try {
            webpImage = ImageIO.read(new File("src/main/resources/icon/simmonspf.webp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(webpImage);
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
