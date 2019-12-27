package winnie.plot;

/**
 * Enum погоды
 */
public enum Weather {
        SNOWING("снежно"),
        COLDY("холодно"),
        SUNNY("солнечно"),
        CLOUDY("облачно"),
        FOGGY("туманно");
        private String text;
        Weather(String text){
            this.text = text;
        }
        public String getText(){
            return text; 
        }
    public static Weather getById(int id){
        if ((id < 0)||(id > 4)) return null;
        else return Weather.values()[id];
    }
}
