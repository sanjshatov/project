public class Skeleton extends FantasyCharacter {
    public Skeleton(String name, int health, int power, int dexterity, int experience, int gold) {
        super(name, health, power, dexterity, experience, gold);
    }
}
// В теории мы могли бы обойтись и одним классом, а при создании просто давать разные имена и устанавливать
// разные параметры, но в будущем мы можем дать разным монстрам разные специальные атаки и особенности.