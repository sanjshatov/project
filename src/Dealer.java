// метод для продажи
public class Dealer implements Seller {
    //Метод для продажи
    @Override
    public String sell(Goods goods) {
        String result = "";
        if (goods == Goods.POTION) {
            result = "potion";
        }
        return result;
    }
    //Энам для товаров
    public enum Goods {
        POTION
    }
}
// Здесь у нас при помощи интерфейса Seller реализован метод sell. Можно еще дописать логику проверки
// достаточности денег для покупки, а также добавить товары и метод для покупки вещей, которые будут давать
// за победу, но это уже на ваше усмотрение.