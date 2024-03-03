import org.json.simple.JSONObject;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;

public class WeatherApplicationGUI extends JFrame {
    private JSONObject weatherData;

    public WeatherApplicationGUI(){
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 570);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents(){
        // search field
        JTextField searchTextField = new JTextField();

        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/Images/Cloud.png"));
        weatherConditionImage.setBounds(0, 10, 450, 217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("--");
        temperatureText.setBounds(0, 250, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 300, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // humidity image
        JLabel humidityImage = new JLabel(loadImage("src/Images/WaterDrop.png"));
        humidityImage.setBounds(15, 390, 74, 66);
        add(humidityImage);

        // humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> --%</html>");
        humidityText.setBounds(90, 390, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/Images/WindSpeed.png"));
        windspeedImage.setBounds(220, 390, 74, 66);
        add(windspeedImage);

        // windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> --km/h</html>");
        windspeedText.setBounds(310, 390, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // set the location and size of our component
        searchTextField.setBounds(15, 480, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        // search button
        JButton searchButton = new JButton(loadImage("src/Images/Search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 480, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                weatherData = WeatherApplicationAPI.getWeatherData(userInput);

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/Images/Sun.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/Images/Cloud.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/Images/Rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/Images/Snow.png"));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath){
        try{
            // read the image file from the path given
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}











