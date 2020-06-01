package com.va181.handika;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.constraintlayout.solver.ArrayRow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_musik";
    private final static String TABLE_MUSIK = "t_musik";
    private final static String KEY_ID_MUSIK = "ID_musik";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_PENYANYI= "Penyanyi";
    private final static String KEY_LIRIK = "Lirik";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFromat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MUSIK = "CREATE TABLE " + TABLE_MUSIK
                + "(" + KEY_ID_MUSIK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_PENYANYI + " TEXT, "
                + KEY_LIRIK + " TEXT, " + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_MUSIK);
        inisialisasiMusikAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MUSIK;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahMusik(Musik dataMusik) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataMusik.getJudul());
        cv.put(KEY_TGL, sdFromat.format(dataMusik.getTanggal()));
        cv.put(KEY_GAMBAR, dataMusik.getGambar());
        cv.put(KEY_PENYANYI, dataMusik.getPenyanyi());
        cv.put(KEY_LIRIK, dataMusik.getLirik());
        cv.put(KEY_LINK, dataMusik.getLink());

        db.insert(TABLE_MUSIK, null, cv);
        db.close();
    }

        public void tambahMusik(Musik dataMusik, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataMusik.getJudul());
        cv.put(KEY_TGL, sdFromat.format(dataMusik.getTanggal()));
        cv.put(KEY_GAMBAR, dataMusik.getGambar());
        cv.put(KEY_PENYANYI, dataMusik.getPenyanyi ());
        cv.put(KEY_LIRIK, dataMusik.getLirik());
        cv.put(KEY_LINK, dataMusik.getLink());
        db.insert(TABLE_MUSIK, null, cv);
    }

    public void editMusik(Musik dataMusik) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL,  dataMusik.getJudul());
        cv.put(KEY_TGL, sdFromat.format( dataMusik.getTanggal()));
        cv.put(KEY_GAMBAR, dataMusik.getGambar());
        cv.put(KEY_PENYANYI,  dataMusik.getPenyanyi());
        cv.put(KEY_LIRIK,  dataMusik.getLirik());
        cv.put(KEY_LINK,  dataMusik.getLink());

        db.update(TABLE_MUSIK, cv, KEY_ID_MUSIK + "=?", new String[]{String.valueOf(dataMusik.getIdMusik ())});

        db.close();
    }

    public void  hapusMusik(int idMusik) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MUSIK, KEY_ID_MUSIK + "=?", new String[]{String.valueOf(idMusik)});
        db.close();
    }

    public ArrayList<Musik> getAllMusik() {
        ArrayList<Musik> dataMusik = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MUSIK;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do{
                Date tempDate = new Date();
                try {
                    tempDate = sdFromat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Musik tempMusik = new Musik (
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6)
                );

                dataMusik.add(tempMusik);
            } while (csr.moveToNext());
        }

        return dataMusik;

    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return  location;
    }

    private void inisialisasiMusikAwal(SQLiteDatabase db) {
        int idMusik = 0;
        Date tempDate = new Date();

        //Menambah data musik ke-1
        try {
            tempDate = sdFromat.parse("09/05/2020 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }



        Musik musik1 = new Musik (
                idMusik,
                "Lathi",
                tempDate,
                storeImageFile(R.drawable.musik1),
                "Weird Genius Ft. Sara fajira",
                "I was born a fool\n" +
                        "Broken all the rules\n" +
                        "Seeing all null\n" +
                        "Denying all of the truth.\n" +
                        "\n" +
                        "Everything has changed\n" +
                        "It all happened for a reason\n" +
                        "Down from the first stage\n" +
                        "It isn’t something we fought for.\n"+
                        "\n" +
                        "Never wanted this kind of pain\n" +
                        "Turned myself so cold and heartless\n" +
                        "But one thing you should know.\n"+
                        "\n" +
                        "'Kowe ra iso mlayu saka kesalahan\n" +
                        "Ajining diri ana ing lathi'\n" +
                        "Pushing through the countless pain\n" +
                        "And all I know that this love’s a bless and curse.\n"+
                        "\n" +
                        "Everything has changed\n" +
                        "It all happened for a reason\n" +
                        "Down from the first stage\n" +
                        "It isn’t something we fought for.\n"+
                        "\n"+
                        "Never wanted this kind of pain\n" +
                        "Turned myself so cold and heartless\n" +
                        "But one thing you should know\n" +
                        "'Kowe ra iso mlayu saka kesalahan\n" +
                        "Ajining diri ana ing lathi'.",
                "https://www.youtube.com/watch?v=mIWr1nDvhMU"
        );

        tambahMusik(musik1, db);
        idMusik++;

        // Data musik ke-2
        try {
            tempDate = sdFromat.parse("29/07/2016 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Musik musik2 = new Musik (
                idMusik,
                "Closer",
                tempDate,
                storeImageFile(R.drawable.musik2),
                "The Chainsmokers\n" +
                        "Halsey",
                "Hey, I was doing just fine before I met you\n" +
                        "I drink too much and that's an issue but I'm okay\n" +
                        "Hey, you tell your friends it was nice to meet them\n" +
                        "But I hope I never see them again\n" +
                        "\n"+
                        "I know it breaks your heart\n" +
                        "Moved to the city in a broke down car\n" +
                        "And four years, no calls\n" +
                        "Now you're looking pretty in a hotel bar\n" +
                        "And I can't stop\n" +
                        "No, I can't stop.\n" +
                        "\n"+
                        "So baby pull me closer in the backseat of your Rover\n" +
                        "That I know you can't afford\n" +
                        "Bite that tattoo on your shoulder\n" +
                        "Pull the sheets right off the corner\n" +
                        "Of the mattress that you stole\n" +
                        "From your roommate back in Boulder\n" +
                        "We ain't ever getting older\n" +
                        "\n"+
                        "We ain't ever getting older\n" +
                        "We ain't ever getting older\n" +
                        "\n"+
                        "You look as good as the day I met you\n" +
                        "I forget just why I left you, I was insane\n" +
                        "Stay and play that Blink-182 song\n" +
                        "That we beat to death in Tucson, okay\n" +
                        "\n"+
                        "I know it breaks your heart\n" +
                        "Moved to the city in a broke down car\n" +
                        "And four years, no call\n" +
                        "Now I'm looking pretty in a hotel bar\n" +
                        "And I can't stop\n" +
                        "No, I can't stop\n" +
                        "\n"+
                        "So baby pull me closer in the backseat of your Rover\n" +
                        "That I know you can't afford\n" +
                        "Bite that tattoo on your shoulder\n" +
                        "Pull the sheets right off the corner\n" +
                        "Of the mattress that you stole\n" +
                        "From your roommate back in Boulder\n" +
                        "We ain't ever getting older\n" +
                        "\n"+
                        "We ain't ever getting older\n" +
                        "We ain't ever getting older\n" +
                        "\n"+
                        "So baby pull me closer in the backseat of your Rover\n" +
                        "That I know you can't afford\n" +
                        "Bite that tattoo on your shoulder\n" +
                        "Pull the sheets right off the corner\n" +
                        "Of the mattress that you stole\n" +
                        "From your roommate back in Boulder\n" +
                        "We ain't ever getting older\n" +
                        "We ain't ever getting older (we ain't ever getting older)\n" +
                        "We ain't ever getting older (we ain't ever getting older)\n" +
                        "We ain't ever getting older (we ain't ever getting older)\n" +
                        "We ain't ever getting older\n" +
                        "\n"+
                        "We ain't ever getting older\n" +
                        "No we ain't ever getting older.",
                "https://www.youtube.com/watch?v=PT2_F-1esPk&list=RDbtrzs54s1Rc&index=24"
        );

        tambahMusik(musik2, db);
        idMusik++;

        //Data musik ke-3
        try {
            tempDate = sdFromat.parse("24/07/2019 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Musik musik3 = new Musik (
                idMusik,
                "Just For a Moment ",
                tempDate,
                storeImageFile(R.drawable.musik3),
                "Gryffin\n" +
                        "Iselin",
                "I'll tell you something you need to know\n" +
                        "Nothing I can say phone to phone\n" +
                        "I'll tell you something but you have to sit close\n" +
                        "You're the one that I miss the most\n" +
                        "\n"+
                        "I've heard that you're happy\n" +
                        "And maybe it's selfish of me\n" +
                        "To want you back this time\n" +
                        "I know we decided it's better if we both were free\n" +
                        "But it kills me now.\n" +
                        "\n"+
                        "We said our last goodbye\n" +
                        "Won't you stay the night?\n" +
                        "Even just for a moment\n" +
                        "Perfect lie, pretend we're fine\n" +
                        "Even just for a moment\n" +
                        "Though we're over, we're so not over\n" +
                        "Pull me closer, I need closure\n" +
                        "This is our last goodbye\n" +
                        "Stay the night, even just for a moment\n" +
                        "\n"+
                        "Even just for a moment\n" +
                        "\n"+
                        "When you're not hurting\n"+
                                "It's hurting me (hurting me)\n" +
                                "I'm blinded by the past\n" +
                                "I'm lost at sea (mm)\n" +
                        "\n"+
                                "I've heard that you're happy\n" +
                                "And maybe it's selfish of me\n" +
                                "To want you back this time (ohh)\n" +
                                "I know we decided it's better if we both were free\n" +
                                "But it kills me now\n" +
                        "\n"+
                                "We said our last goodbye\n" +
                                "Won't you stay the night?\n" +
                                "Even just for a moment\n" +
                                "Perfect lie, pretend we're fine\n" +
                                "Even just for a moment\n" +
                                "Though we're over, we're so not over\n" +
                                "Pull me closer, I need closure\n" +
                                "This is our last goodbye\n" +
                                "Stay the night, even just for a moment\n" +
                        "\n"+
                                "Even just for a moment\n" +
                        "\n"+
                                "Even just for a moment\n" +
                        "\n"+
                                "We said our last goodbye\n" +
                                "Won't you stay the night\n" +
                                "Even just for a moment?\n" +
                                "Perfect lie, pretend we're fine\n" +
                                "Even just for a moment\n" +
                                "Though we're over, we're so not over\n" +
                                "Pull me closer, I need closure\n" +
                                "This is our last goodbye\n" +
                                "Stay the night, even just for a moment\n" +
                                "(Moment, moment, moment, moment, moment)\n" +
                                "Even just for a moment (moment, moment, moment)\n" +
                                "Won't you stay the night\n" +
                                "Even just for a moment?\n" +
                        "\n"+
                                "Even just for a moment\n" +
                        "\n"+
                                "This is our last goodbye\n" +
                                "Stay the night\n" +
                                "Even just for a moment?\n" +
                                "Perfect lie, pretend we're fine\n" +
                                "Even just for a moment.",
                "https://www.youtube.com/watch?v=NgSFun7F8dI"
        );

        tambahMusik(musik3, db);
        idMusik++;

        // Data film ke-4
        try {
            tempDate = sdFromat.parse("18/04/2015 ");
        } catch (ParseException er) {
            er.printStackTrace();
        }
        Musik musik4 = new Musik (
                idMusik,
                "Surrender",
                tempDate,
                storeImageFile(R.drawable.musik4),
                "Natalie Taylor",
                "We let the waters rise\n" +
                        "We drifted to survive\n" +
                        "I needed you to stay\n" +
                        "But I let you drift away\n" +
                        "\n"+
                        "My love where are you?\n" +
                        "My love where are you? \n" +
                        "\n"+
                        "Whenever you're ready, whenever you're ready\n" +
                        "Whenever you're ready, whenever you're ready\n" +
                        "Can we, can we surrender?\n" +
                        "Can we, can we surrender?\n" +
                        "I surrender\n" +
                        "\n"+
                        "No one will win this time\n" +
                        "I just want you back\n" +
                        "I'm running to your side\n" +
                        "Flying my white flag, my white flag\n" +
                        "\n"+
                        "My love where are you?\n" +
                        "My love where are you?\n" +
                        "\n"+
                        "Whenever you're ready, whenever you're ready\n" +
                        "Whenever you're ready, whenever you're ready\n" +
                        "Can we, can we surrender?\n" +
                        "Can we, can we surrender?\n" +
                        "I surrender\n" +
                        "I surrender.",
                "https://www.youtube.com/watch?v=nagMxzLZfLk"
        );

        tambahMusik(musik4, db);
        idMusik++;

        //Data musik ke-5
        try {
            tempDate = sdFromat.parse ( "19/10/2016" );
        } catch (ParseException er) {
            er.printStackTrace ();
        }
        Musik musik5 = new Musik (
                idMusik,
                "Hero",
                tempDate,
                storeImageFile ( R.drawable.musik5 ),
                "Cash Cash\n" +
                        "Christina Perri",
                "I let my soul fall into you\n" +
                        "I never thought I'd fall right through\n" +
                        "I fell for every word you said\n" +
                        "You made me feel I needed you\n" +
                        "And forced my heart to think it's true\n" +
                        "But I found I'm powerless with you\n"+
                        "\n"+
                        "Now I don't need your wings to fly\n" +
                        "No, I don't need a hand to hold in mine this time\n" +
                        "You held me down, but I broke free\n" +
                        "I found the love inside of me\n" +
                        "Now I don't need a hero to survive\n" +
                        "'Cause I already saved my life\n" +
                        "'Cause I already saved my life\n"+
                        "\n"+
                        "I fell into your fantasy\n" +
                        "But that's all our love will ever be\n" +
                        "I lost hope in saving you and me\n" +
                        "You think I'm lost, falling apart\n" +
                        "But your lies just made a stronger heart\n" +
                        "My life is just about to start\n"+
                        "\n"+
                        "Now I don't need your wings to fly\n" +
                        "No, I don't need a hand to hold in mine this time\n" +
                        "You held me down, but I broke free\n" +
                        "I found the love inside of me\n" +
                        "Now I don't need a hero to survive\n" +
                        "'Cause I already saved my life\n" +
                        "Already saved my life\n" +
                        "I already saved my life\n" +
                        "\n"+
                        "Already saved my life\n" +
                        "I already saved my life\n"+
                        "Now I don't need your wings to fly\n" +
                        "No, I don't need a hand to hold in mine this time\n" +
                        "You held me down, but I broke free\n" +
                        "I found the love inside of me\n" +
                        "Now I don't need a hero to survive\n" +
                        "'Cause I already saved my life\n" +
                        "Already saved my life\n" +
                        "I already saved my life\n" +
                        "Already saved my life\n" +
                        "I already saved my life\n"+
                        "\n"+
                        "Now I don't need your wings to fly.",
                "https://www.youtube.com/watch?v=RcaQaz_Ij_Y"
        );

        tambahMusik (musik5,db );

    }
    
}
