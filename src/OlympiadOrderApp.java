import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class OlympiadOrderApp extends JFrame {

    private static class LocaleItem {
        String title;
        Locale locale;

        LocaleItem(String title, Locale locale) {
            this.title = title;
            this.locale = locale;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    private static class PodarokItem {
        String key;
        int price;

        PodarokItem(String key, int price) {
            this.key = key;
            this.price = price;
        }
    }

    private static final int CONCERT_PRICE = 10000;

    private final String[] pozdravitelKeys = {
            "pozdravitel.rector",
            "pozdravitel.dean",
            "pozdravitel.sponsor"
    };

    private final int[] pozdravitelPrices = {5000, 3000, 7000};

    private final PodarokItem[][] podarkiByPozdravitel = {
            {
                    new PodarokItem("gift.diploma", 1000),
                    new PodarokItem("gift.cup", 2500)
            },
            {
                    new PodarokItem("gift.certificate", 500),
                    new PodarokItem("gift.book", 1200),
                    new PodarokItem("gift.flash", 1500)
            },
            {
                    new PodarokItem("gift.tablet", 15000),
                    new PodarokItem("gift.headphones", 6000),
                    new PodarokItem("gift.merch", 2000),
                    new PodarokItem("gift.voucher", 4000)
            }
    };

    private Locale currentLocale;
    private ResourceBundle bundle;
    private int selectedPozdravitelIndex = 0;

    private JComboBox<LocaleItem> localeComboBox;
    private JLabel localeLabel;
    private JLabel shortDateLabel;
    private JLabel shortDateValueLabel;
    private JLabel longDateLabel;
    private JLabel longDateValueLabel;

    private JLabel pozdravitelLabel;
    private final JCheckBox[] pozdravitelBoxes = new JCheckBox[3];

    private JLabel podarokLabel;
    private JButton giftsButton;
    private JPopupMenu giftsPopup;
    private JPanel giftsPopupPanel;
    private final List<JCheckBox> giftCheckBoxes = new ArrayList<>();

    private JLabel concertLabel;
    private JRadioButton concertYesRadio;
    private JRadioButton concertNoRadio;

    private JLabel clientLabel;
    private JRadioButton clientYesRadio;
    private JRadioButton clientNoRadio;

    private JLabel totalLabel;
    private JButton saveButton;

    public OlympiadOrderApp() {
        currentLocale = new Locale("ru", "RU");
        bundle = ResourceBundle.getBundle("Messages", currentLocale);

        initUI();
        rebuildGiftsMenu();
        updateTexts();
        updateDateTimeLabels();
        recalculateTotal();

        Timer timer = new Timer(1000, e -> updateDateTimeLabels());
        timer.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(660, 440);
        setMinimumSize(new Dimension(580, 400));
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 6));

        localeLabel = new JLabel();
        localeComboBox = new JComboBox<>(new LocaleItem[] {
                new LocaleItem("русский (ru)", new Locale("ru", "RU")),
                new LocaleItem("english (us)", new Locale("en", "US")),
                new LocaleItem("deutsch (de)", new Locale("de", "DE"))
        });
        localeComboBox.addActionListener(e -> onLocaleChanged());

        shortDateLabel = new JLabel();
        shortDateValueLabel = new JLabel();
        longDateLabel = new JLabel();
        longDateValueLabel = new JLabel();

        topPanel.add(localeLabel);
        topPanel.add(localeComboBox);
        topPanel.add(shortDateLabel);
        topPanel.add(shortDateValueLabel);
        topPanel.add(longDateLabel);
        topPanel.add(longDateValueLabel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        pozdravitelLabel = new JLabel();
        JPanel pozdravitelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        ButtonGroup pozdravitelGroup = new ButtonGroup();
        for (int i = 0; i < pozdravitelBoxes.length; i++) {
            int index = i;
            pozdravitelBoxes[i] = new JCheckBox();
            pozdravitelBoxes[i].addActionListener(e -> {
                if (selectedPozdravitelIndex != index) {
                    selectedPozdravitelIndex = index;
                    rebuildGiftsMenu();
                    updateGiftTexts();
                    updateGiftsButtonText();
                    recalculateTotal();
                }
            });
            pozdravitelGroup.add(pozdravitelBoxes[i]);
            pozdravitelPanel.add(pozdravitelBoxes[i]);
        }
        pozdravitelBoxes[0].setSelected(true);
        addFormRow(centerPanel, gbc, 0, pozdravitelLabel, pozdravitelPanel);

        podarokLabel = new JLabel();
        giftsButton = new JButton();
        giftsPopup = new JPopupMenu();
        giftsPopupPanel = new JPanel();
        giftsPopupPanel.setLayout(new BoxLayout(giftsPopupPanel, BoxLayout.Y_AXIS));
        giftsPopupPanel.setBorder(new EmptyBorder(5, 8, 5, 8));
        giftsPopup.add(giftsPopupPanel);
        giftsButton.addActionListener(e -> giftsPopup.show(giftsButton, 0, giftsButton.getHeight()));
        addFormRow(centerPanel, gbc, 1, podarokLabel, giftsButton);

        concertLabel = new JLabel();
        JPanel concertPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        concertYesRadio = new JRadioButton();
        concertNoRadio = new JRadioButton();
        ButtonGroup concertGroup = new ButtonGroup();
        concertGroup.add(concertYesRadio);
        concertGroup.add(concertNoRadio);
        concertNoRadio.setSelected(true);
        concertYesRadio.addActionListener(e -> recalculateTotal());
        concertNoRadio.addActionListener(e -> recalculateTotal());
        concertPanel.add(concertYesRadio);
        concertPanel.add(concertNoRadio);
        addFormRow(centerPanel, gbc, 2, concertLabel, concertPanel);

        clientLabel = new JLabel();
        JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        clientYesRadio = new JRadioButton();
        clientNoRadio = new JRadioButton();
        ButtonGroup clientGroup = new ButtonGroup();
        clientGroup.add(clientYesRadio);
        clientGroup.add(clientNoRadio);
        clientNoRadio.setSelected(true);
        clientYesRadio.addActionListener(e -> recalculateTotal());
        clientNoRadio.addActionListener(e -> recalculateTotal());
        clientPanel.add(clientYesRadio);
        clientPanel.add(clientNoRadio);
        addFormRow(centerPanel, gbc, 3, clientLabel, clientPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        totalLabel = new JLabel();
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 16f));
        saveButton = new JButton();
        saveButton.addActionListener(e -> saveOrderToFile());

        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(saveButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, JComponent left, JComponent right) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;
        panel.add(left, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(right, gbc);
    }

    private void rebuildGiftsMenu() {
        giftsPopupPanel.removeAll();
        giftCheckBoxes.clear();

        PodarokItem[] items = podarkiByPozdravitel[selectedPozdravitelIndex];
        for (int i = 0; i < items.length; i++) {
            JCheckBox cb = new JCheckBox();
            cb.addActionListener(e -> {
                updateGiftsButtonText();
                recalculateTotal();
            });
            giftCheckBoxes.add(cb);
            giftsPopupPanel.add(cb);
        }
        giftsPopup.pack();
    }

    private void onLocaleChanged() {
        LocaleItem selected = (LocaleItem) localeComboBox.getSelectedItem();
        if (selected != null) {
            currentLocale = selected.locale;
            bundle = ResourceBundle.getBundle("Messages", currentLocale);
            updateTexts();
            updateDateTimeLabels();
            recalculateTotal();
        }
    }

    private void updateTexts() {
        setTitle(bundle.getString("window.title"));
        localeLabel.setText(bundle.getString("label.locale"));
        shortDateLabel.setText(bundle.getString("label.date.short"));
        longDateLabel.setText(bundle.getString("label.date.long"));

        pozdravitelLabel.setText(bundle.getString("label.pozdravitel"));
        for (int i = 0; i < pozdravitelBoxes.length; i++) {
            String name = bundle.getString(pozdravitelKeys[i]);
            pozdravitelBoxes[i].setText(name + " (" + pozdravitelPrices[i] + ")");
        }

        podarokLabel.setText(bundle.getString("label.podarok"));
        updateGiftTexts();
        updateGiftsButtonText();

        concertLabel.setText(bundle.getString("label.concert"));
        concertYesRadio.setText(bundle.getString("radio.yes"));
        concertNoRadio.setText(bundle.getString("radio.no"));

        clientLabel.setText(bundle.getString("label.client"));
        clientYesRadio.setText(bundle.getString("radio.yes"));
        clientNoRadio.setText(bundle.getString("radio.no"));

        saveButton.setText(bundle.getString("button.save"));
    }

    private void updateGiftTexts() {
        PodarokItem[] items = podarkiByPozdravitel[selectedPozdravitelIndex];
        for (int i = 0; i < giftCheckBoxes.size(); i++) {
            String giftName = bundle.getString(items[i].key);
            giftCheckBoxes.get(i).setText(giftName + " (" + items[i].price + ")");
        }
        giftsPopup.pack();
    }

    private void updateGiftsButtonText() {
        PodarokItem[] items = podarkiByPozdravitel[selectedPozdravitelIndex];
        List<String> selectedNames = new ArrayList<>();
        for (int i = 0; i < giftCheckBoxes.size(); i++) {
            if (giftCheckBoxes.get(i).isSelected()) {
                selectedNames.add(bundle.getString(items[i].key));
            }
        }

        if (selectedNames.isEmpty()) {
            giftsButton.setText(bundle.getString("button.gifts.none"));
            giftsButton.setToolTipText(null);
        } else {
            giftsButton.setText(bundle.getString("button.gifts.selected") + " " + selectedNames.size() + " ▼");
            giftsButton.setToolTipText(String.join(", ", selectedNames));
        }
    }

    private void updateDateTimeLabels() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(currentLocale);
        DateTimeFormatter longFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(currentLocale);

        shortDateValueLabel.setText(now.format(shortFormatter));
        longDateValueLabel.setText(now.format(longFormatter));
    }

    private double calculateTotal() {
        double total = pozdravitelPrices[selectedPozdravitelIndex];
        PodarokItem[] items = podarkiByPozdravitel[selectedPozdravitelIndex];

        for (int i = 0; i < giftCheckBoxes.size(); i++) {
            if (giftCheckBoxes.get(i).isSelected()) {
                total += items[i].price;
            }
        }

        if (concertYesRadio.isSelected()) {
            total += CONCERT_PRICE;
        }

        if (clientYesRadio.isSelected()) {
            total *= 0.9;
        }

        return total;
    }

    private void recalculateTotal() {
        double total = calculateTotal();
        totalLabel.setText(bundle.getString("label.total") + " " + String.format("%.2f", total) + " " + bundle.getString("currency"));
    }

    private void saveOrderToFile() {
        PodarokItem[] items = podarkiByPozdravitel[selectedPozdravitelIndex];
        List<String> selectedGifts = new ArrayList<>();
        for (int i = 0; i < giftCheckBoxes.size(); i++) {
            if (giftCheckBoxes.get(i).isSelected()) {
                selectedGifts.add(bundle.getString(items[i].key) + " (" + items[i].price + ")");
            }
        }

        String giftsText = selectedGifts.isEmpty()
                ? bundle.getString("order.gifts.none")
                : String.join(", ", selectedGifts);

        String concertAnswer = concertYesRadio.isSelected()
                ? bundle.getString("radio.yes")
                : bundle.getString("radio.no");

        String clientAnswer = clientYesRadio.isSelected()
                ? bundle.getString("radio.yes")
                : bundle.getString("radio.no");

        double total = calculateTotal();

        try (PrintWriter writer = new PrintWriter("order.txt", "UTF-8")) {
            writer.println(bundle.getString("order.header"));
            writer.println(bundle.getString("order.date") + " " + longDateValueLabel.getText());
            writer.println(bundle.getString("order.pozdravitel") + " " + bundle.getString(pozdravitelKeys[selectedPozdravitelIndex]) + " (" + pozdravitelPrices[selectedPozdravitelIndex] + ")");
            writer.println(bundle.getString("order.gifts") + " " + giftsText);
            writer.println(bundle.getString("order.concert") + " " + concertAnswer);
            writer.println(bundle.getString("order.client") + " " + clientAnswer);
            writer.println(bundle.getString("order.total") + " " + String.format("%.2f", total) + " " + bundle.getString("currency"));

            JOptionPane.showMessageDialog(
                    this,
                    bundle.getString("dialog.saved.message"),
                    bundle.getString("dialog.saved.title"),
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    bundle.getString("dialog.error.title"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OlympiadOrderApp app = new OlympiadOrderApp();
            app.setVisible(true);
        });
    }
}