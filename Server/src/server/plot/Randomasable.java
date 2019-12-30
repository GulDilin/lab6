package server.plot;
import java.util.Random;

interface Randomasable{
    /**
     * Получение случайного числа
     * @param value от 0 до value
     * @return случайное число
     */
    default int getRand(int value){
        Random random = new Random();
        return random.nextInt(value);
    }
}
