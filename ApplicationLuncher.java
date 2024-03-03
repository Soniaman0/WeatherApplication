import javax.swing.*;

public class ApplicationLuncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                // display our weather app gui
                new WeatherApplicationGUI().setVisible(true);
                
            }
        });
    }
}
