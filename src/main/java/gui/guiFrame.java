package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class guiFrame extends JFrame {

    private JFrame frame;
    private JMenuBar menuBar;
    private final Container contentPane = new JPanel();
    private final Container contentPaneNums = new JPanel();
    private final JLabel numberLabel = new JLabel("0");
    private final JLabel numberLabel_2 = new JLabel("0");
    private final JLabel storedValueLabel = new JLabel("0");
    private final String[] numButtons = new String[] {"9","8","7","6","5","4","3","2","1","+/-","0","."};
    private final String[] endButtons = new String[] {"/","*","+","-","="};

    public guiFrame(JFrame frame) {
        this.frame = frame;
        showGUI(frame);
    }

    private void init() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 550);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Remainder Calculator");
        this.menuBar = new MenuBar(frame);
        frame.setJMenuBar(menuBar);
    }

    private void showGUI(JFrame frame) {

        init();
        buildGUI();
        frame.setVisible(true);
    }

    private void buildGUI() {
        Container numContainer = new Container();
        Container endContainer = new Container();

        for(String str : numButtons) {
            JButton numberButton = getJButton(str,75,75);
            numContainer.add(numberButton);
        }
        for(String str : endButtons) {
            JButton endButton = getJButton(str,50,50);
            endContainer.add(endButton);
        }
        numContainer.setLayout(new  GridLayout(4,3,5,5));
        numContainer.setSize(75, 400);
        endContainer.setLayout(new  GridLayout(6,1,5,5));
        endContainer.setSize(50, 400);
        /**
         * Label inits
         * */
        numberLabel.setHorizontalAlignment(JLabel.RIGHT);
        numberLabel.setPreferredSize(new Dimension(150,50));
        numberLabel.setFont(new Font("Arial",Font.BOLD,25));
        numberLabel.setOpaque(false);
        //Label 2
        numberLabel_2.setHorizontalAlignment(JLabel.RIGHT);
        numberLabel_2.setPreferredSize(new Dimension(150,50));
        numberLabel_2.setFont(new Font("Arial",Font.BOLD,25));
        numberLabel_2.setOpaque(false);

        numberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        numberLabel_2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        contentPane.setLayout(new BorderLayout(5,1));
        contentPaneNums.add(numberLabel_2,BorderLayout.WEST);
        contentPaneNums.add(numberLabel,BorderLayout.EAST);
        contentPane.add(contentPaneNums,BorderLayout.NORTH);

        contentPane.add(numContainer, BorderLayout.WEST);
        contentPane.add(endContainer, BorderLayout.CENTER);
        frame.add(contentPane,BorderLayout.CENTER);
    }


    private JButton getJButton(String str, int width, int height) {
        JButton numberButton = new JButton(str);
        //numberButton.setSize(75,75);
        numberButton.setOpaque(true);
        numberButton.setPreferredSize(new Dimension(width,height));
        numberButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        numberButton.addActionListener(aEvent -> {
            switch (str){
                case "+/-":
                    if(numberLabel.getText().contains("-")){
                        numberLabel.setText(numberLabel.getText().replace("-",""));
                    }else {
                        numberLabel.setText("-"+numberLabel.getText());
                    }
                    break;
                case "=":
                    boolean isAdd = storedValueLabel.getText().contains("+");
                    boolean isSub = storedValueLabel.getText().contains("-");
                    boolean isMult = storedValueLabel.getText().contains("*");
                    boolean isDiv = storedValueLabel.getText().contains("/");
                    String storedValue = storedValueLabel.getText().replace(storedValueLabel.getText().substring(storedValueLabel.getText().length()-2),"");
                    System.out.println(storedValue);
                    double res = 0;
                    if(isAdd)
                       res= Math.addExact(Integer.parseInt(storedValue),Integer.parseInt(numberLabel.getText()));
                    if(isSub)
                        res =Math.subtractExact(Integer.parseInt(storedValue),Integer.parseInt(numberLabel.getText()));
                    if(isMult)
                        res =Math.multiplyExact(Integer.parseInt(storedValue),Integer.parseInt(numberLabel.getText()));
                    if(isDiv)
                       res = Math.floorDiv(Integer.parseInt(storedValue),Integer.parseInt(numberLabel.getText()));

                    numberLabel.setText(String.valueOf(res));
                    storedValueLabel.setText(String.valueOf(res));
                    break;
                case "-":
                case "+":
                case "/":
                case "*":
                    if(numberLabel.getText().equals("0")){
                        break;
                    }
                    storedValueLabel.setText(numberLabel.getText()+" "+str);
                    numberLabel.setText("0");
                    break;
                default:
                    if(numberLabel.getText().equals("0")){
                        numberLabel.setText(str);
                        break;
                    }
                        numberLabel.setText(numberLabel.getText() + str);
                        break;

            }

        });
        return numberButton;
    }
}
