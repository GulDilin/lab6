package server;

import com.sun.jmx.remote.internal.ArrayQueue;
import server.plot.Mood;
import server.plot.Song;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;

import static java.lang.System.exit;


public class DataBaseManager {
    private String user = "postgres";
    private String password = "000";
    private Connection connection;
    private Statement stmt = null;
    private transient PrintStream out;
    private HashSet<String> user_logins = new HashSet<>();


    /**
     * @param dbName database name
     * @param lPort  local port
     * @throws SQLException throws if cant connect database
     */
    public DataBaseManager(String dbName, int lPort) throws SQLException {
        try {
            this.out = System.out;
            Class.forName("org.postgresql.Driver");

            try {
                System.out.println("Try to connect database...");

                connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:" + lPort + "/" + dbName,
                                user, "");
                this.stmt = connection.createStatement();
                System.out.println("Database connected!");
            } catch (NullPointerException ex) {

                System.out.println("Database connection error");
                exit(1);

            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Driver loading error");
            exit(1);
        }
    }

    /**
     * Get MD5 hash from string
     *
     * @param st string to get hash from
     * @return MD5 hash in HEX format
     */
    public static String getMD5(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 31) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex.substring(0, 31);
    }

    /**
     * @return
     */
    public ArrayQueue<Song> getSongs() {
        ArrayQueue<Song> songs = new ArrayQueue<Song>(10);
        try {

            ResultSet rs = stmt.executeQuery("SELECT * FROM songs");
            while (rs.next()) {
                String str = "{\"mood\": " + "\"" + rs.getString("mood").toLowerCase() + "\"," +
                        " \"text\": " + "\"" + rs.getString("text") + "\"" + "}";
                System.out.println(str);
                Song song = new Song(Mood.getMoodByString(rs.getString("mood")),
                        rs.getString("text"),
                        Integer.parseInt(rs.getString("id")));
                song.setOwnerId(Integer.parseInt(rs.getString("owner_id")));
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    /**
     * @param hash
     * @param userID
     * @return
     */
    public boolean checkUser(String hash, int userID) {
        System.out.println();
        System.out.println(hash);
        System.out.println(userID);
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                if (Integer.valueOf(rs.getInt("user_id")).equals(userID)) {
                    if (hash.equals(rs.getString("password")))
                        return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Database connection error");
//            exit(1);
            return false;
        }
        return false;
    }

    /**
     * @param login
     * @param password
     * @return
     */
    public boolean checkPass(String login, String password) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                if (login.equals(rs.getString("login"))) {
                    if (DataBaseManager.getMD5(password)
                            .equals(rs.getString("password")))
                        return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database connection error");
            return false;
        }
        return false;
    }

    /**
     * @param song
     * @param Id
     * @param hash
     * @return
     */
    public boolean deleteSong(Song song, int Id, String hash) {
        if (!checkUser(hash, Id)) {
            this.out.println("Wrong user and password");
            return false;
        }
        if (!(Id == song.getOwnerId())) {
            this.out.println("Wrong user");
            return false;
        }
        String mood = "";
        String text = "";
//        Song removableSong = new Song(mood, text, );
        System.out.println("Try to remove " + song.toString());
        String deleteQuery = "DELETE FROM songs" +
                " WHERE mood LIKE \'" + song.getMood() + "\'"
                + " AND  text LIKE \'" + song.getText() + "\';";
        out.println(deleteQuery);
        try {
            stmt.execute(deleteQuery);
            System.out.println("Database remove success");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database delete failed");
            ex.printStackTrace();
            return false;
        }
    }


    public boolean deleteByOwnerId(int id, String hash) {
        if (!checkUser(hash, id)) {
            this.out.println("Wrong user and password");
            return false;
        }
        String deleteQuery = "DELETE FROM songs" +
                " WHERE owner_id =" + id + ";";
        out.println(deleteQuery);
        try {
            stmt.execute(deleteQuery);
            System.out.println("Database remove success");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database delete failed");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int id, int ownerId, String hash) {
        if (!checkUser(hash, ownerId)) {
            this.out.println("Wrong user and password");
            return false;
        }
        String deleteQuery = "DELETE FROM songs" +
                " WHERE id =" + id + ";";
        out.println(deleteQuery);
        try {
            stmt.execute(deleteQuery);
            System.out.println("Database remove success");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database delete failed");
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * @param song
     * @param Id
     * @return
     */
    public boolean addSong(Song song, int Id) {
        song.setOwnerId(Id);
        String values = "";
        values += "\'" + song.getMood() + "\' ,";
        values += "\'" + song.getText() + "\' ,";
        values += Id;
        System.out.println(values);
        try {
            stmt.execute("INSERT INTO songs(mood, text, owner_id)" +
                    "VALUES (" + values + ")");
            return true;
        } catch (SQLException ex) {
            System.err.println("Database add failed");
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * @param login
     * @param email
     * @param password
     * @return
     */
    public boolean registerUser(String login, String email, String password) {
        if (!user_logins.contains(login)) {
            try {
                stmt.execute("INSERT INTO users(EMAIL, LOGIN, PASSWORD)" +
                        "VALUES (\'" + email + "\' , \'" + login + "\' , \'" + getMD5(password) + "\' )");
                user_logins.add(login);
            } catch (SQLException ex) {
                System.err.println("Database add failed");
                ex.printStackTrace();
                return false;
            }
            return true;
        } else return false;
    }

    /**
     * @param len
     * @return
     */
    public static String getRandomPassword(int len) {
        String password = "";
        for (int i = 0; i < len; i++) {
            password += (char) ((int) (Math.random() * 20) + 65);
        }
        return password;
    }

    /**
     * @return
     */
    public HashSet<String> getUserLogins() {
        HashSet<String> logins = new HashSet<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                logins.add(rs.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logins;
    }

    public int getUserID(String login) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT login, user_id FROM users");
            while (rs.next()) {
                if (rs.getString("login").equals(login)) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }
}