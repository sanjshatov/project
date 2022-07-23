public abstract class FantasyCharacter implements Fighter{
    //Имя персонажа
    private String name;
    //Статы персонажа
    private int health;
    private int power;
    private int dexterity;
    //Опыт и золото
    private int experience;
    private int gold;
    //Конструктор
    public FantasyCharacter(String name, int health, int power, int dexterity, int experience, int gold) {
        this.name = name;
        this.health = health;
        this.power = power;
        this.dexterity = dexterity;
        this.experience = experience;
        this.gold = gold;
    }
    //Метод для ведения боя
    @Override
    public int attack() {
        if (dexterity * 3 > getRandomValue()) return experience;
        else return  0;
    }

    public String getName() {
        return name;
    }
    //Геттеры и сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public int getDexterity() {
        return dexterity;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    private int getRandomValue() {
        return (int) (Math.random() * 100);
    }
    //Переопределяем вывод в консоль, чтобы выводилось имя и очки здоровья
    @Override
    public String toString() {
        return String.format("%s здоровье:%d", name, health);
    }
}