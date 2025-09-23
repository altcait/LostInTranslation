package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


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

            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();

            // create combobox, add country codes into it, and add it to our panel
            JComboBox<String> countryComboBox = new JComboBox<>();
            for(String countryCode : translator.getCountryCodes()) {
                String country = countryCodeConverter.fromCountryCode(countryCode);
                countryComboBox.addItem(country);
            }
            countryPanel.add(countryComboBox);

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

            // RESULT PANEL
            JPanel resultPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            resultPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabel);

            // add listener for when an item is selected.
            countryComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String country = countryComboBox.getSelectedItem().toString();
                        String language = languageList.getSelectedValue();

                        Translator translator = new JSONTranslator();

                        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
                        String convertedCountry = countryCodeConverter.fromCountry(country);
                        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
                        String convertedLanguage = languageCodeConverter.fromLanguage(language);

                        String result = translator.translate(convertedCountry, convertedLanguage);

                        resultLabel.setText(result);

                    }

                }
            });


            languageList.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String country = countryComboBox.getSelectedItem().toString();
                    String language = languageList.getSelectedValue();

                    Translator translator = new JSONTranslator();

                    CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
                    String convertedCountry = countryCodeConverter.fromCountry(country);
                    LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
                    String convertedLanguage = languageCodeConverter.fromLanguage(language);

                    String result = translator.translate(convertedCountry, convertedLanguage);

                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
