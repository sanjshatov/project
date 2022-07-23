import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RolePlayingGame {
    //В этом классе у нас будут переменные, которые мы выносим на уровне класса, чтобы к ним был доступ из других методов
// Класс для чтения введенных строк из консоли
    private static BufferedReader bufferedreader;
    //Игрок должен храниться на протяжении всей игры
    private static FantasyCharacter player = null;
    //Класс для битвы можно не создавать каждый раз, а переиспользовать
    private static BattleScene battleScene = null;

    // Как видно, поля класса статичные. Чтобы нам не создавать этот класс в методе main,
    // так как без этого класса мы все равно не сможем, методы этого класса будут тоже статичными.
    //Теперь нам нужно описать метод, с которого все и начнется, то есть с метода main:

    public static void main(String[] args) {
        //Инициализируем BufferedReader
        bufferedreader = new BufferedReader(new InputStreamReader(System.in));
        //Инициализируем класс для боя
        battleScene = new BattleScene();
        //Первое, что нужно сделать при запуске игры, это создать персонажа, поэтому мы предлагаем ввести его имя
        System.out.println("Введите имя персонажа:");
        //Далее ждем ввод от пользователя
        try {
            command(bufferedreader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Как мы видим, у нас тут нет никаких циклов и потоков тоже не создается, но тем не менее игра не завершиться,
    // так как в методе command у нас будет реализована рекурсия, то есть повторный вызов метода. Давайте тогда и
    // переместимся к сердцу нашей игры, методу command, в параметры которого мы передаем считанное значение из консоли:

    private static void command(String string) throws IOException {
        //Если это первый запуск, то мы должны создать игрока, именем будет служить первая введенная строка из консоли
        if (player == null) {
            player = new Hero(
                    string,
                    80,
                    20,
                    30,
                    10,
                    10
            );
            System.out.println(String.format("Спасти наш мир от драконов вызвался %s! Да будет его броня крепка и бицепс кругл!", player.getName()));
            //Метод для вывода меню
            printNavigation();
        }
        //Варианты для команд
        switch (string) {
            case "1": {
                System.out.println("Торговец еще не приехал");
                command(bufferedreader.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(bufferedreader.readLine());
            }
        }
        //Снова ждем команды от пользователя
        command(bufferedreader.readLine());
    }


    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s победил! Теперь у вас %d опыта и %d золота, а также осталось %d единиц здоровья.", player.getName(), player.getExperience(), player.getGold(), player.getHealth()));
                System.out.println("Желаете продолжить поход или вернуться в город? (да/нет)");
                try {
                    command(bufferedreader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }
// Также стоит разобрать метод createMonster, так как из него мы возвращаем монстра для драки. Как вы помните,
// у нас есть гоблины и скелеты, мы их будем возвращать рандомно, чтобы были интересней:
private static FantasyCharacter createMonster() {
    //Рандомайзер
    int random = (int) (Math.random() * 10);
    //С вероятностью 50% создается или скелет, или гоблин
    if (random % 2 == 0) return new Goblin(
            "Гоблин",
            50,
            10,
            10,
            100,
            20
    );
    else return new Skeleton(
            "Скелет",
            25,
            20,
            20,
            100,
            10
    );
}
    // Здесь мы запускаем наш бой, передав в метод fight у переменной battleScene нашего героя — player, а также
    // монстра. И коллбэк, который мы тут и реализуем. Как вы помните, в классе BattleScene мы будем вызывать методы
    // FightCallback, так вот, когда мы их вызываем там, они будут отрабатываться тут, где мы их создали изначально.
    // Как видите мы здесь анонимно реализуем класс из интерфейса FightCallback, вот сам интерфейс:

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти?");
        System.out.println("1. К Торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    public interface FightCallback {
        void fightWin();
        void fightLost();
    }
}
