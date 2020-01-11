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
                "PASSWORD INTEGER NOT NULL);");

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
    public String[] obtenerTareas(int userID, int terminada){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select CASE WHEN REALIZADA=1 THEN NOMBRE || '\n' || FECHA || ' a las ' || HORA || '\n' || FECHA_FIN || ' a las '|| HORA_FIN " +
                        "ELSE NOMBRE || '\n' || FECHA || ' a las ' || HORA END "+
                        "from TAREAS " +
                        "where USER_ID=? AND REALIZADA=?",
                new String[]{String.valueOf(userID),String.valueOf(terminada)} );
        System.out.println("TERMINADAS:   "+cursor.getCount());
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
    public String[] devolverTareaCompletada(int idTarea){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] res=new String[4];
        Cursor cursor=db.rawQuery("SELECT NOMBRE, FECHA_FIN, HORA_FIN, USER_ID FROM TAREAS WHERE ID=?",
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
        db.close();
    }
    public void guardarTareaTerminada(int idTarea,String nombre,String fecha,String hora){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE TAREAS SET NOMBRE=?,FECHA_FIN=?,HORA_FIN=? WHERE ID=?",
                new String[] {nombre,fecha,hora,String.valueOf(idTarea)});
        db.close();
    }
    public void borrarTarea(int idTarea){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM TAREAS WHERE ID=?;",new String[] {String.valueOf(idTarea)});
        db.close();
    }
    public int nRegistros(int userId, int realizada){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from TAREAS WHERE USER_ID=? AND REALIZADA=?",
                new String[] {String.valueOf(userId),String.valueOf(realizada)});
        return cursor.getCount();
    }
    public void realizarTarea(int realizada, int idTarea,String fecha,String hora){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE TAREAS SET REALIZADA=?, FECHA_FIN=?, HORA_FIN=? WHERE ID=?;",
                new String[] {String.valueOf(realizada),fecha,hora,String.valueOf(idTarea)});
        db.close();
    }

    public void addUser(String user, int password){
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
    public int existe(String user,int password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM USUARIOS WHERE USUARIO=? and PASSWORD=?;",
                new String[] {user,String.valueOf(password)});
        return cursor.getCount();
    }

}
