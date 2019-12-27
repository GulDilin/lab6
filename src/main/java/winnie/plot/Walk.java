package winnie.plot;

public class Walk implements Randomasable {
    private walkWeather walkW;
    private int time;
    private Iteration iteration;

    public Walk() {
        walkW = new walkWeather();
        time = getRand(70);
        iteration = new Iteration();
    }

    /**
     * Класс погоды, позвонялет устанавливать и получать температуру и погоду
     */
    public class walkWeather {
        private Weather weather;
        private int temperature;

        walkWeather() {
            weather = Weather.getById(getRand(4));
            changeTemperature();
        }

        Weather getWeather() { return weather; }
        int getTemperature() { return temperature; }
        void changeTemperature() { temperature = -(getRand(20)); }
        void printTemperature() {
            walkW.changeTemperature();
            System.out.println("На улице:" + walkW.getTemperature());
        }
    }

    /**
     * Метод проверки состояния персонажей
     * @param person массив из 2 персонажей
     * @return true если температура больше 15 и если у персонажей не отрицательный статус
     */
    private boolean validate(Character[] person) {
        for (Character i : person) {
            if (i.getStatus() < 0)
                return false;
        }
        return walkW.getTemperature() >= -16;
    }

    /**
     * Основной метот прогулки, отправляет персонажей не прогулку
     * @param W Винни
     * @param P Пятачок
     */
    public void talk(Winnie W, Piglet P) {
        Character[] person = {W, P};
        System.out.println(W.getName() + " и " + P.getName() + " сидят дома");
        System.out.println("На улице " + walkW.weather.getText() + ", температура:  " + walkW.getTemperature());
        Iteration firsIteration = new Iteration() {
            @Override
            public void changeOfCharacteristics(Character[] person) {
                System.out.println("--------------------------------------------");
                System.out.println("Characteristic:");
                for (Character i : person) {
                    i.weatherMood(walkW.getWeather());
                    i.talkMood(i);
                    i.talkStatus(i);
                }
                System.out.println("============================================");
            }
        };
        firsIteration.changeOfCharacteristics(person);
        if (((W.getMood() == Mood.SAD) && (W.getMood() == P.getMood())) || (walkW.getTemperature() < -18))
            System.out.println("Обоим не нравится погода, они решили остаться дома и есть плюшки");
        else {
            System.out.println(W.getName() + " и " + P.getName() + "пошли гулять");
            System.out.println("Они шли " + time + " минут");
            walkW.printTemperature();
            iteration.changeOfCharacteristics(person, time, false);
            if (!validate(person)) {
                System.out.println(W.getName() + ": пора идти домой");
                return;
            }
            while (W.getStatus() > 20) {
                W.sing();
                walkW.printTemperature();
                if (walkW.getTemperature() < -13)
                    iteration.changeOfCharacteristics(person, 30, false);
                else {
                    iteration.changeOfCharacteristics(person, 20, true);
                }
                if (!validate(person)) {
                    System.out.println(W.getName() + ": пора идти домой");
                    return;
                }
            }
            P.support(W);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Walk other = (Walk) obj;
        return walkW.weather.equals(other.walkW.weather);
    }

    @Override
    public int hashCode() {
        return walkW.weather.hashCode();
    }

    @Override
    public String toString() {
        return "погода: " + walkW.weather;
    }
}

