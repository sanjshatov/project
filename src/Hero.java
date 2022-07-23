public class Hero extends FantasyCharacter {

    public Hero(String name, int health, int power, int dexterity, int experience, int gold) {
        super(name, health, power, dexterity, experience, gold);
    }
}
// Тут мы просто наследуемся от FantasyCharacter и создаем конструктор, в параметрах которого мы пробрасываем
// значения в родительский класс. Также вы можете добавить в будущем особые черты для этого класса,
// например, умение разговаривать.