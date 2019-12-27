package winnie.plot;

public class Iteration {
    /**
     * Выводин информацию о изменении характеристик персонажей за какой-то период
     * @param person Массив из персонажей
     * @param num Как изменился статуус
     * @param flag В какую сторону изменилось настроение
     */
    void changeOfCharacteristics(Character[] person, int num, boolean flag) {
        System.out.println("--------------------------------------------");
        System.out.println("Characteristic:");
        for (Character i : person) {
            i.changeMood(flag);
            i.talkMood(i);
            i.changeStatus(num);
            i.talkStatus(i);
        }
        System.out.println("============================================");
    }

    public void changeOfCharacteristics(Character[] person) { }
}
