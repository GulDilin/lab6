package server.plot;

/**
 * Enum настроений
 */
public enum Mood{
    SAD("грустное (0%)"),
    GOOD("нормальное (50%)"),
    HAPPY("веселое (100%)");
    private String text;
    Mood(String text){
        this.text = text;
    }
    public String getText(){
        return text;
    }
    public static Mood getMoodByString(String text) {
           switch (text){
               case "SAD":
                   return Mood.SAD;
               case "HAPPY":
                   return Mood.HAPPY;
               case "GOOD":
                   return Mood.GOOD;
           }
        return null;
    }
    public static Mood getById(int id){
        if ((id < 0)||(id > 3)) return null;
        else return Mood.values()[id];
    }
}
