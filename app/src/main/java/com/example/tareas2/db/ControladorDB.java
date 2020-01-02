package com.example.tareas2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tareas2.Principal;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.tareas2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS (ID INTEGER PRIMARY KEY AUTOINCREMENT, USUARIO TEXT NOT NULL, PASSWORD TEXT NOT NULL);");

        db.execSQL("CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL,FECHA_INICIO DATE, USER_ID INTEGER NOT NULL, FOREIGN KEY (USER_ID) REFERENCES USUARIOS(ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTask (String task,int user_id){
        ContentValues registro=new ContentValues();
        registro.put("NOMBRE",task);
        registro.put("USER_ID",user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TAREAS",null,registro);
        //db.execSQL("INSERT INTO TAREAS VALUES (null, '+tarea+');'");
        db.close();
    }
    public String[] obtenerTareas(int userID){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select NOMBRE from TAREAS where USER_ID=?",new String[]{String.valueOf(userID)} );
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
    public int nRegistros(int userId){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from TAREAS WHERE USER_ID=?",new String[] {String.valueOf(userId)});
        return cursor.getCount();
    }
    public void borrarTarea(String nombre){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM TAREAS WHERE NOMBRE=?;",new String[] {nombre});
        //db.delete("TAREAS","NOMBRE=?",new String[] {nombre});
        db.close();
    }
    public void editarTarea(String tareaNueva,String tareaAntigua){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE TAREAS SET NOMBRE=? where NOMBRE=?;",new String[] {tareaNueva,tareaAntigua});
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
    public int idUser(String user){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT ID FROM USUARIOS WHERE USUARIO=?;",new String[] {user});
        cursor.moveToFirst();
        System.out.println(cursor.getInt(0));
        return cursor.getInt(0);
    }
    public int existe(String user,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM USUARIOS WHERE USUARIO=? and PASSWORD=?;",new String[] {user,password});
        return cursor.getCount();
    }
}
