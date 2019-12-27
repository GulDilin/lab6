package winnie.plot;
import java.util.Random;

public class Piglet extends Character implements Talkable{
    public Piglet(String n, Mood m, int st){
        super(n,m,st);
        setLovelyWeather(Weather.CLOUDY);
    }

    /**
     * Помогает Винни преодолеть усталось и спеть еще одну шумелку
     * @param W Винни
     */
    void support(Winnie W) {
        System.out.println(W.getName()+" устал и не будет петь шумелку");
        System.out.println(this.getName()+": Спой шумелку, пожалуйста");
        Random rand = new Random();
        if (rand.nextInt() < 0.7)
        {
            W.changeStatus(-20);
            System.out.println(W.getName()+": Ладно, спою");
            W.sing();
        }
        System.out.println(W.getName()+": Пойдем домой, я устал");
    }
}
