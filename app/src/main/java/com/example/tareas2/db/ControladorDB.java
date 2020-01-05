package com.example.tareas2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.tareas2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USUARIO TEXT NOT NULL, " +
                "PASSWORD TEXT NOT NULL);");

        db.execSQL("CREATE TABLE TAREAS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOMBRE TEXT NOT NULL," +
                "REALIZADA INTEGER, " +
                "USER_ID INTEGER NOT NULL, " +
                "FECHA TEXT, " +
                "HORA TEXT, " +
                "FECHA_FIN TEXT, "+
                "HORA_FIN TEXT, "+
                "FOREIGN KEY (USER_ID) REFERENCES USUARIOS(ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask (String task,String fecha,String hora,int user_id){
        ContentValues registro=new ContentValues();
        registro.put("NOMBRE",task);
        registro.put("USER_ID",user_id);
        registro.put("REALIZADA",0);
        registro.put("FECHA",fecha);
        registro.put("HORA",hora);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TAREAS",null,registro);
        //db.execSQL("INSERT INTO TAREAS VALUES (null, '+tarea+');'");
        db.close();
    }
    public String[] obtenerTareas(int userID){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select NOMBRE || '\n' || FECHA || ' a las ' || HORA " +
                        "from TAREAS " +
                        "where USER_ID=? AND REALIZADA=0",
                new String[]{String.valueOf(userID)} );

        if (cursor.getCount()==0){
            db.close();
            return null;
        }else{
            String[] tar=new String[cursor.getCount()];
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                tar[i]=cursor.getString(0);
                cursor.moveToNext();
            }
            db.close();
            return tar;
        }
    }
    public String[] devolverTarea(int idTarea){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] res=new String[4];
        Cursor cursor=db.rawQuery("SELECT NOMBRE, FECHA, HORA, USER_ID FROM TAREAS WHERE ID=?",
                new String[]{String.valueOf(idTarea)});
        cursor.moveToFirst();
        for (int i=0;i<res.length;i++)
            res[i]=cursor.getString(i);
        cursor.close();
        return res;
    }
    public void guardarTarea(int idTarea,String nombre,String fecha,String hora){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE TAREAS SET NOMBRE=?,FECHA=?,HORA=? WHERE ID=?",
                new String[] {nombre,fecha,hora,String.valueOf(idTarea)});
    }
    public int nRegistros(int userId){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from TAREAS WHERE USER_ID=? AND REALIZADA=0",
                new String[] {String.valueOf(userId)});
        return cursor.getCount();
    }
    public void realizarTarea(int idTarea){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE TAREAS SET REALIZADA=1 WHERE ID=?;",new String[] {String.valueOf(idTarea)});
        db.close();
    }

    public void addUser(String user, String password){
        ContentValues registro=new ContentValues();
        registro.put("USUARIO",user);
        registro.put("PASSWORD",password);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("USUARIOS",null,registro);
        //db.execSQL("INSERT INTO USUARIOS VALUES (null,?,?)",new String[] {user,password});
        db.close();
    }
    public int idTarea(String nombreTarea,String fecha,String hora){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID FROM TAREAS WHERE NOMBRE=? AND FECHA=? AND HORA=?;",
                new String[] {nombreTarea,fecha,hora});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
    public int idUser(String user){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID FROM USUARIOS WHERE USUARIO=?;",
                new String[] {user});
        cursor.moveToFirst();
        System.out.println(cursor.getInt(0));
        return cursor.getInt(0);
    }
    public int existe(String user,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM USUARIOS WHERE USUARIO=? and PASSWORD=?;",
                new String[] {user,password});
        return cursor.getCount();
    }
}
