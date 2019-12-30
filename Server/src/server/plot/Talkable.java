package server.plot;

interface Talkable {
    /**
     * Позволяет вывести на экран информацию о статусе персонажа
     * @param C персонаж
     */
    default void talkStatus(Character C){ System.out.println("У " + C.getName() + " осталось сил:"+ C.getStatus()); }
    /**
     * Позволяет вывести на экран информацию о настроении персонажа
     * @param C персонаж
     */
    default void talkMood(Character C) { System.out.println("Настроение " + C.getName()+ " " + C.getMood().getText()); }
}
