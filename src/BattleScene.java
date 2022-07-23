public class BattleScene {
    //Метод, который вызывается при начале боя, сюда мы передаем ссылки на нашего героя и монстра, который встал у него на пути
    public void fight(FantasyCharacter hero, FantasyCharacter monster, RolePlayingGame.FightCallback fightCallback) {
        //Ходы будут идти в отдельном потоке
        Runnable runnable = () -> {
            //Сюда будем записывать, какой сейчас ход по счету
            int turn = 1;
            //Когда бой будет закончен мы
            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("----Ход: " + turn + "----");
                //Воины бьют по очереди, поэтому здесь мы описываем логику смены сторон
                if (turn++ % 2 != 0) {
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {
                    //Чтобы бой не проходил за секунду, сделаем имитацию работы, как если бы
                    //у нас была анимация
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //Запускаем новый поток
        Thread thread = new Thread(runnable);
        thread.start();
    }
    //Метод для совершения удара
    private Boolean makeHit(FantasyCharacter defender, FantasyCharacter attacker, RolePlayingGame.FightCallback fightCallback) {
        //Получаем силу удара
        int hit = attacker.attack();
        //Отнимаем количество урона из здоровья защищающегося
        int defenderHealth = defender.getHealth() - hit;
        //Если атака прошла, выводим в консоль сообщение об этом
        if (hit != 0) {
            System.out.println(String.format("%s Нанес удар в %d единиц!", attacker.getName(), hit));
            System.out.println(String.format("У %s осталось %d единиц здоровья...", defender.getName(), defenderHealth));
        } else {
            //Если атакующий промахнулся (то есть урон не 0), выводим это сообщение
            System.out.println(String.format("%s промахнулся!", attacker.getName()));
        }
        if (defenderHealth <= 0 && defender instanceof Hero) {
            //Если здоровье меньше 0 и если защищающейся был героем, то игра заканчивается
            System.out.println("Извините, вы пали в бою...");
            //Вызываем коллбэк, что мы проиграли
            fightCallback.fightLost();
            return true;
        } else if(defenderHealth <= 0) {
            //Если здоровья больше нет и защищающийся – это монстр, то мы забираем от монстра его опыт и золото
            System.out.println(String.format("Враг повержен! Вы получаете %d опыт и %d золота", defender.getExperience(), defender.getGold()));
            attacker.setExperience(attacker.getExperience() + defender.getExperience());
            attacker.setGold(attacker.getGold() + defender.getGold());
            //вызываем коллбэк, что мы победили
            fightCallback.fightWin();
            return true;
        } else {
            //если защищающийся не повержен, то мы устанавливаем ему новый уровень здоровья
            defender.setHealth(defenderHealth);
            return false;
        }
    }
}
// Комментарии везде расставлены, но давайте разберем два важных момента.
//Первый момент. Весь наш бой мы ведем в отдельном потоке, в теории это излишне, но допустим, что мы хотим делать еще
// какую-то работу параллельно. Так как у нас там есть блокирующий код, это обосновано.
//И также здесь у нас открывается возможность для рефакторинга. Сейчас у нас каждый раз создается новый поток при
// вызове метода fight, но это не есть хорошо, так как создание потока — штука дорогостоящая, лучше создать один раз
// поток и его переиспользовать.
//Второй момент. Мы в методе fight, помимо участников схватки, передаем объект RolePlayingGame.FightCallback fightCallback,
// у которого мы будем вызывать методы в зависимости от исхода боя. Если победил герой, то fightWin, если он проиграл,
// то fightLost. Зачем так сделано? Потому что бой у нас идет в отдельном потоке, и нам нужно вернуть результат этой
// работы обратно в основной поток. То есть мы создаем объект, который будет создан и реализован в одном потоке.
// Передаем на него ссылку в другой поток, и как только в другом потоке будет какое-то событие, мы у переданной ссылки
// вызываем её метод, и этот метод срабатывает в потоке, в котором был создан этот объект. Как это реализовано, мы
// посмотрим уже в следующей главе.