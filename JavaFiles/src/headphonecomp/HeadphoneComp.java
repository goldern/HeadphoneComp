package headphonecomp;
/**
 *
 * @author Gabriel Alves
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



class ImagePanel extends JComponent {
    private Image image;
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        /*g.setColor(Color.RED);
        int x = 1;
        int y = 1;
        g.drawLine(x,y, x,y);*/
    }
    public void painter(Graphics g, int x, int y, Color cor){
        super.paintComponent(g);
        g.setColor(cor);
        g.drawLine(x,y, x,y);
    }
}

public class HeadphoneComp{
    int width = 280;
    int height = 100;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Headphone Comparation"); //GUI TITLE
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //GUI EXIT WHEN CLOSED
        frame.setSize(900,520); //GUI WIDTH HEIGTH
       
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILES");
        mb.add(m1);
       
        String[] headphones = null;
        headphones = new File("C:\\HP").list();
        //int f = new File("C:\\HP").list().length;
        JPanel Images = new JPanel();
        JComboBox Img1 = new JComboBox(headphones);
        JComboBox Img2 = new JComboBox(headphones);
        Images.add(Img1);
        Images.add(Img2);

        JPanel Buttons = new JPanel();
        JButton b1 = new JButton("Menu");
        JButton b2 = new JButton("Press");
        Buttons.add(b1);
        Buttons.add(b2);
       
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, Images);
        frame.getContentPane().add(BorderLayout.SOUTH, Buttons);

        b1.addActionListener((ActionEvent e) -> {
            System.out.print("A");
        });
        b2.addActionListener((ActionEvent e) -> {
            int counter = 0;
            JFrame imgLoc = new JFrame();
            //imgLoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            imgLoc.setSize(900, 550);
            imgLoc.setResizable(false);
            JTextField Source = new JTextField();
            imgLoc.add(Source);
            BufferedImage background = null;
            try {
                background = ImageIO.read(new File("C:\\HP\\blank.png"));
                //BufferedImage Imagem = ImageIO.read(new File(Source.getText()));
            } catch (IOException ex) {
                Logger.getLogger(HeadphoneComp.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImagePanel bgr = new ImagePanel(background);
            imgLoc.setContentPane(bgr);
            Graphics g = null;
            if(background.getGraphics() != null){
                g = background.getGraphics();
            }else{
                System.out.println("No background defined");
            }
            
            try {
                //Config
                String DefaultLoc = "C:\\HP\\";
                String hp1 = Img1.getSelectedItem().toString();//"superlux_HD668B";
                String hp2 = Img2.getSelectedItem().toString();//"sennheiser_HD600";
                imgLoc.setTitle(hp1 + "  VS  " + hp2);
                
                BufferedImage Imagem1 = ImageIO.read(new File(DefaultLoc + hp1));
                BufferedImage Imagem2 = ImageIO.read(new File(DefaultLoc + hp2));
                //BufferedImage Imagem2 = ImageIO.read(new File("C:\\HP\\sennheiser_HD600.png"));
                
                Color cores2;
                int red2, green2, blue2;
                
                for(int x = 0; x < 900; x++){
                    for(int y = 50; y < 520; y++){
                        Color cores1 = new Color( Imagem1.getRGB(x, y) );
                        int red1 = cores1.getRed();
                        int green1 = cores1.getGreen();
                        int blue1 = cores1.getBlue(); 
                        /*if(red2 == 79 && green2 == 129 && blue2 == 189){
                            bgr.painter(g, x, y, Color.CYAN);
                        }*/
                        if(red1 == 79 && green1 == 129 && blue1 == 189){
                            counter++;
                            bgr.painter(g, x, y, Color.BLACK);
                            for(int y2 = 50; y2 < 520; y2++){
                                cores2 = new Color( Imagem2.getRGB(x, y2) );
                                red2 = cores2.getRed();
                                green2 = cores2.getGreen();
                                blue2 = cores2.getBlue();
                                if(red2 == 79 && green2 == 129 && blue2 == 189){
                                    if(y2 < y){
                                        bgr.painter(g, x, y2, Color.GREEN);
                                    }else if(y2 >= y){
                                        bgr.painter(g, x, y2, Color.RED);
                                    }
                                    //bgr.painter(g, x, y2, Color.CYAN);
                                }
                            } 
                        }
                        
                    }
                }
                System.out.println("Counter: " + counter);
                //System.out.print("RED: " + red1 + "GREEN: " + green1 + "BLUE: " + blue1);
            } catch (IOException ex) {
                Logger.getLogger(HeadphoneComp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            imgLoc.setLocation(dim.width/2-imgLoc.getSize().width/2, dim.height/2-imgLoc.getSize().height/2);
            imgLoc.setVisible(true);
        });
       
        
       frame.setVisible(true);
    }
}
