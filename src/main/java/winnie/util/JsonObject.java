package winnie.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import winnie.plot.*;

public class JsonObject {
    public Song getJson(String elem) throws NullPointerException {
        Song song;
        Mood mood = null;
        String md;
        String text;
        JSONParser parser=new JSONParser();
        JSONObject jsonOb = null;
        String s="{\"mood\":\"GOOD\",\"text\":\"в голове моей опилки\"}";
        try{
            jsonOb = (JSONObject) parser.parse(elem);
            md = (String)jsonOb.get("mood");
            md = md.toUpperCase();
            text = (String) jsonOb.get("text");
            if (text.equals(null) || md.equals(null)){
                throw new NullPointerException();
            }
            mood = Mood.getMoodByString(md);
            song = new Song(mood,text);
            return song;
        }catch (ParseException | NullPointerException e){
            return null;
        }
    }
}
