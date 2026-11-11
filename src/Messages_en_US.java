import java.util.ListResourceBundle;

public class Messages_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"window.title", "olympiad winners awarding"},
                {"label.locale", "language & region:"},
                {"label.date.short", "date (short):"},
                {"label.date.long", "date (long):"},
                {"label.pozdravitel", "congratulator:"},
                {"pozdravitel.rector", "rector"},
                {"pozdravitel.dean", "dean"},
                {"pozdravitel.sponsor", "sponsor"},
                {"label.podarok", "gifts:"},
                {"button.gifts.none", "select gifts ▼"},
                {"button.gifts.selected", "selected:"},
                {"gift.diploma", "diploma"},
                {"gift.cup", "cup"},
                {"gift.certificate", "certificate"},
                {"gift.book", "book"},
                {"gift.flash", "usb flash"},
                {"gift.tablet", "tablet"},
                {"gift.headphones", "headphones"},
                {"gift.merch", "merch"},
                {"gift.voucher", "voucher"},
                {"label.concert", "concert (+10000):"},
                {"label.client", "regular client (-10%):"},
                {"radio.yes", "yes"},
                {"radio.no", "no"},
                {"label.total", "total:"},
                {"currency", "usd"},
                {"button.save", "save to file"},
                {"dialog.saved.title", "done"},
                {"dialog.saved.message", "saved to order.txt"},
                {"dialog.error.title", "error"},
                {"order.header", "awarding order"},
                {"order.date", "date:"},
                {"order.pozdravitel", "congratulator:"},
                {"order.gifts", "gifts:"},
                {"order.gifts.none", "none"},
                {"order.concert", "concert:"},
                {"order.client", "regular client:"},
                {"order.total", "total:"}
        };
    }
}