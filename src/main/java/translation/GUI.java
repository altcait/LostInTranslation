package translation;

import javax.swing.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();

            // COUNTRY PANEL
            JPanel countryPanel = new JPanel();
            countryPanel.add(new JLabel("Country:"));

            String[] countries = new String[translator.getCountryCodes().size()];
            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                String country = countryCodeConverter.fromCountryCode(countryCode);
                countries[i++] = country;
            }

            JList<String> countryList = new JList<>(countries);
            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane countryScrollPane = new JScrollPane(countryList);

            countryPanel.add(countryScrollPane);

            // LANGUAGE PANEL
            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));

            String[] languages = new String[translator.getLanguageCodes().size()];
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            int j = 0;
            for(String languageCode : translator.getLanguageCodes()) {
                String lang = languageCodeConverter.fromLanguageCode(languageCode);
                languages[j++] = lang;
            }

            JList<String> languageList = new JList<>(languages);
            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane languageScrollPane = new JScrollPane(languageList);
            languagePanel.add(languageScrollPane, 1);

            // BUTTON PANEL
            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageList.getSelectedValue();
                    String country = countryList.getSelectedValue();

                    Translator translator = new JSONTranslator();

                    CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
                    String convertedCountry = countryCodeConverter.fromCountry(country);
                    LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
                    String convertedLanguage = languageCodeConverter.fromLanguage(language);

                    String result = translator.translate(convertedCountry, convertedLanguage);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
