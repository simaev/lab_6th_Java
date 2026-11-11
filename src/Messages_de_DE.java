import java.util.ListResourceBundle;

public class Messages_de_DE extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"window.title", "siegerehrung der olympiade"},
                {"label.locale", "sprache & region:"},
                {"label.date.short", "datum (kurz):"},
                {"label.date.long", "datum (lang):"},
                {"label.pozdravitel", "gratulant:"},
                {"pozdravitel.rector", "rektor"},
                {"pozdravitel.dean", "dekan"},
                {"pozdravitel.sponsor", "sponsor"},
                {"label.podarok", "geschenke:"},
                {"button.gifts.none", "geschenke wählen ▼"},
                {"button.gifts.selected", "ausgewählt:"},
                {"gift.diploma", "diplom"},
                {"gift.cup", "pokal"},
                {"gift.certificate", "urkunde"},
                {"gift.book", "buch"},
                {"gift.flash", "usb-stick"},
                {"gift.tablet", "tablet"},
                {"gift.headphones", "kopfhörer"},
                {"gift.merch", "merch"},
                {"gift.voucher", "gutschein"},
                {"label.concert", "konzert (+10000):"},
                {"label.client", "stammkunde (-10%):"},
                {"radio.yes", "ja"},
                {"radio.no", "nein"},
                {"label.total", "gesamt:"},
                {"currency", "eur"},
                {"button.save", "in datei speichern"},
                {"dialog.saved.title", "fertig"},
                {"dialog.saved.message", "in order.txt gespeichert"},
                {"dialog.error.title", "fehler"},
                {"order.header", "bestellung"},
                {"order.date", "datum:"},
                {"order.pozdravitel", "gratulant:"},
                {"order.gifts", "geschenke:"},
                {"order.gifts.none", "keine"},
                {"order.concert", "konzert:"},
                {"order.client", "stammkunde:"},
                {"order.total", "gesamt:"}
        };
    }
}