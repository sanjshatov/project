public interface Seller {
    String sell(Dealer.Goods goods);
}
// Так как с торговцем мы драться не планируем,
// то мы не наделяем его статами,
// вот единственный интерфейс, который он реализует
