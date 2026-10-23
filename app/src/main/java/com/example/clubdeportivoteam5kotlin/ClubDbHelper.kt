package com.example.clubdeportivoteam5kotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClubDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE personas (
                dni               TEXT PRIMARY KEY,
                nombre            TEXT NOT NULL,
                apellido          TEXT NOT NULL,
                correo            TEXT NOT NULL,
                tipo              TEXT NOT NULL CHECK(tipo IN ('Socio','No Socio')),
                apto              INTEGER NOT NULL,
                fecha_vencimiento TEXT
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE pagos (
                id        INTEGER PRIMARY KEY AUTOINCREMENT,
                dni       TEXT NOT NULL REFERENCES personas(dni),
                tipo      TEXT NOT NULL CHECK(tipo IN ('cuota','diaria')),
                actividad TEXT,
                monto     REAL NOT NULL,
                medio     TEXT NOT NULL,
                fecha     TEXT NOT NULL
            )
            """.trimIndent()
        )

        seed(db)
    }

    private fun seed(db: SQLiteDatabase) {
        db.execSQL("INSERT INTO personas VALUES ('30111222','Ana','Gomez','ana.gomez@club.com','Socio',1,'2026-10-31')")
        db.execSQL("INSERT INTO personas VALUES ('28998877','Marta','Rios','marta.rios@club.com','Socio',1,'2026-11-30')")
        db.execSQL("INSERT INTO personas VALUES ('34123456','Pedro','Diaz','pedro.diaz@club.com','Socio',1,'2026-08-15')")
        db.execSQL("INSERT INTO personas VALUES ('32456789','Luis','Sosa','luis.sosa@club.com','No Socio',1,NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS pagos")
        db.execSQL("DROP TABLE IF EXISTS personas")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "club.db"
        const val DB_VERSION = 1
    }
}
