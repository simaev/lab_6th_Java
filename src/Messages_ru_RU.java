import java.util.ListResourceBundle;

public class Messages_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"window.title", "награждение победителей олимпиады"},
                {"label.locale", "язык и регион:"},
                {"label.date.short", "дата (кратко):"},
                {"label.date.long", "дата (подробно):"},
                {"label.pozdravitel", "поздравитель:"},
                {"pozdravitel.rector", "ректор"},
                {"pozdravitel.dean", "декан"},
                {"pozdravitel.sponsor", "спонсор"},
                {"label.podarok", "подарки:"},
                {"button.gifts.none", "выбрать подарки ▼"},
                {"button.gifts.selected", "выбрано:"},
                {"gift.diploma", "диплом"},
                {"gift.cup", "кубок"},
                {"gift.certificate", "грамота"},
                {"gift.book", "книга"},
                {"gift.flash", "флешка"},
                {"gift.tablet", "планшет"},
                {"gift.headphones", "наушники"},
                {"gift.merch", "мерч"},
                {"gift.voucher", "сертификат"},
                {"label.concert", "концерт (+10000):"},
                {"label.client", "постоянный клиент (-10%):"},
                {"radio.yes", "да"},
                {"radio.no", "нет"},
                {"label.total", "итого:"},
                {"currency", "руб."},
                {"button.save", "сохранить в файл"},
                {"dialog.saved.title", "готово"},
                {"dialog.saved.message", "заказ записан в order.txt"},
                {"dialog.error.title", "ошибка"},
                {"order.header", "заказ на награждение"},
                {"order.date", "дата:"},
                {"order.pozdravitel", "поздравитель:"},
                {"order.gifts", "подарки:"},
                {"order.gifts.none", "нет"},
                {"order.concert", "концерт:"},
                {"order.client", "постоянный клиент:"},
                {"order.total", "итого:"}
        };
    }
}