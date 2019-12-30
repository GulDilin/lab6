package server.plot;

import static java.util.Objects.*;

public abstract class Character implements Talkable{
    private String name;
    private Mood mood;
    private int status;
    private Weather lovelyWeather = Weather.SUNNY;

    Character(String n, Mood m, int st){
        setName(n);
        setMood(m);
        setStatus(st);
    }
    String getName(){
        return name;
    }
    Mood getMood(){
        return mood;
    }
    int getStatus(){
        return status;
    }

    /**
     * Устанавливает персанажу любимую погоду
     * @param livelyWeather погода которую необходимо установить
     */
    void setLovelyWeather(Weather livelyWeather) { this.lovelyWeather = livelyWeather; }
    private void setName(String n){ this.name = n; }
    private void setMood(Mood md){ this.mood = md; }
    private void setStatus(int st){ this.status = st; }

    /**
     * Изменение статуса персовнажей
     * @param t насалько изменить сататус
     */
    void changeStatus(int t)  { this.status -= t; }

    /**
     * Изменение настроения персонажа
     * @param flag Обозначает как изменилось настроение (поднялось = true или ухудшилось = false)
     */
    void changeMood(boolean flag){
        if (flag){
            switch (this.getMood()){
                case SAD:
                    setMood(Mood.GOOD);
                    break;
                case GOOD:
                    setMood(Mood.HAPPY);
                    break;
            }
        }else {
            switch (this.getMood()){
                case GOOD:
                    setMood(Mood.SAD);
                    break;
                case HAPPY:
                    setMood(Mood.GOOD);
                    break;
            }
        }
    }

    void weatherMood(Weather weather) {
        if (weather ==  lovelyWeather)
            setMood(Mood.HAPPY);
        else if (weather == Weather.SNOWING)
            setMood(Mood.SAD);
        else {
            setMood(Mood.GOOD);
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        Character other = (Character) obj;
        return name.equals(other.name);
    }
    @Override
    public int hashCode(){
        return hash(name,mood,status);
    }
    @Override
    public String toString(){
        return getClass().getName()
        + "name=" + name
        + ",mood=" + mood
        + ",status" + status;
    }
}
