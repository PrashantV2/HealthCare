package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthcare_db.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DOCTORS = "doctors";
    public static final String TABLE_PATIENTS = "patients";
    public static final String TABLE_PRESCRIPTIONS = "prescriptions";
    public static final String COLUMN_PRESCRIPTION_DETAILS = "details";
    public static final String TABLE_MEDICINES = "medicines";
    public static final String COLUMN_MEDICINE_NAME = "medicine";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COLUMN_DOCTOR_NAME = "doctor_name";
    public static final String COLUMN_APPOINTMENT_DETAILS = "details";
    public static final String COLUMN_PATIENT_NAME = "patient_name";
    public static final String COLUMN_DOSAGE = "dosage";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDoctorsTable = "CREATE TABLE " + TABLE_DOCTORS + " (id INTEGER PRIMARY KEY, username TEXT, email TEXT, password TEXT, latitude REAL, longitude REAL, doctor_type TEXT)";
        String createPatientsTable = "CREATE TABLE " + TABLE_PATIENTS + " (id INTEGER PRIMARY KEY, username TEXT, email TEXT, password TEXT)";
        String createAppointmentsTable = "CREATE TABLE " + TABLE_APPOINTMENTS + " (id INTEGER PRIMARY KEY, " +
                COLUMN_DOCTOR_NAME + " TEXT, " +
                COLUMN_PATIENT_NAME + " TEXT, " +
                COLUMN_APPOINTMENT_DETAILS + " TEXT)";
        String createPrescriptionsTable = "CREATE TABLE prescriptions (id INTEGER PRIMARY KEY, " + COLUMN_PATIENT_NAME + " TEXT, " + COLUMN_MEDICINE_NAME + " TEXT, " + COLUMN_DOSAGE + " TEXT)";
        String createMedicinesTable = "CREATE TABLE " + TABLE_MEDICINES + " (id INTEGER PRIMARY KEY, " +
                COLUMN_MEDICINE_NAME + " TEXT)";


        db.execSQL(createAppointmentsTable);
        db.execSQL(createDoctorsTable);
        db.execSQL(createPatientsTable);
        db.execSQL(createPrescriptionsTable);
        db.execSQL(createMedicinesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINES);
        onCreate(db);
    }

    public boolean insertDoctor(String username, String email, String password, String latitudeStr, String longitudeStr, String doctorType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Convert String to double
        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            // Invalid latitude or longitude provided
            return false;
        }

        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("doctor_type", doctorType);

        long result = db.insert(TABLE_DOCTORS, null, contentValues);

        // Close the database connection
        db.close();

        // If data is inserted correctly it will return a positive number
        return result != -1;
    }

    public boolean insertPatient(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = db.insert(TABLE_PATIENTS, null, contentValues);
        return result != -1;
    }

    // Change the return type from boolean to String
    public String checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // First, check in the doctors table
            cursor = db.rawQuery("SELECT * FROM " + TABLE_DOCTORS + " WHERE email = ? AND password = ?", new String[]{email, password});
            if (cursor != null && cursor.moveToFirst()) {
                return "Doctor";
            }

            // Close the previous cursor before reusing it
            if (cursor != null) {
                cursor.close();
            }

            // Next, check in the patients table
            cursor = db.rawQuery("SELECT * FROM " + TABLE_PATIENTS + " WHERE email = ? AND password = ?", new String[]{email, password});
            if (cursor != null && cursor.moveToFirst()) {
                return "Patient";
            }

            return null; // indicates no matching user found
        } catch (Exception e) {
            // Log the exception for debugging
            Log.e("DatabaseHelper", "Error while checking user", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public List<String> searchDoctors(String query) {
        List<String> doctors = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT username, doctor_type FROM " + TABLE_DOCTORS + " WHERE doctor_type LIKE ?", new String[]{"%" + query + "%"});

        int nameIndex = cursor.getColumnIndex("username");
        int typeIndex = cursor.getColumnIndex("doctor_type");

        while (cursor.moveToNext()) {
            if (nameIndex != -1 && typeIndex != -1) {
                String name = cursor.getString(nameIndex);
                String type = cursor.getString(typeIndex);
                doctors.add("Dr. " + name + " - " + type);
            }
        }

        cursor.close();
        return doctors;
    }

    public List<String> getAllDoctorNames() {
        List<String> doctorNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + TABLE_DOCTORS, null);

        int columnIndex = cursor.getColumnIndex("username");
        if (columnIndex != -1) {
            while (cursor.moveToNext()) {
                doctorNames.add(cursor.getString(columnIndex));
            }
        }
        cursor.close();
        return doctorNames;
    }

    public boolean createAppointment(String doctorName, String patientName, String details) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DOCTOR_NAME, doctorName);
        contentValues.put(COLUMN_PATIENT_NAME, patientName);
        contentValues.put(COLUMN_APPOINTMENT_DETAILS, details);

        long result = db.insert(TABLE_APPOINTMENTS, null, contentValues);

        return result != -1;
    }

    public List<String> getAppointments() {
        List<String> appointments = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS, null);

        int doctorNameIndex = cursor.getColumnIndex(COLUMN_DOCTOR_NAME);
        int detailsIndex = cursor.getColumnIndex(COLUMN_APPOINTMENT_DETAILS);

        while (cursor.moveToNext()) {
            if (doctorNameIndex != -1 && detailsIndex != -1) {
                String doctorName = cursor.getString(doctorNameIndex);
                String details = cursor.getString(detailsIndex);
                appointments.add("Doctor: " + doctorName + "\nDetails: " + details);
            }
        }
        cursor.close();
        return appointments;
    }
    public List<String> getPrescriptions() {
        List<String> prescriptions = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_PATIENT_NAME + ", " + COLUMN_MEDICINE_NAME + ", " + COLUMN_DOSAGE + " FROM " + TABLE_PRESCRIPTIONS, null);

        int patientNameIndex = cursor.getColumnIndex(COLUMN_PATIENT_NAME);
        int medicineNameIndex = cursor.getColumnIndex(COLUMN_MEDICINE_NAME);
        int dosageIndex = cursor.getColumnIndex(COLUMN_DOSAGE);

        while (cursor.moveToNext()) {
            String patientName = cursor.getString(patientNameIndex);
            String medicineName = cursor.getString(medicineNameIndex);
            String dosage = cursor.getString(dosageIndex);

            String prescriptionDetail = "Patient: " + patientName + ", Medicine: " + medicineName + ", Dosage: " + dosage;
            prescriptions.add(prescriptionDetail);
        }
        cursor.close();
        return prescriptions;
    }


    public List<String> getMedicines() {
        List<String> medicines = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MEDICINE_NAME + " FROM " + TABLE_MEDICINES, null);

        int columnIndex = cursor.getColumnIndex(COLUMN_MEDICINE_NAME);
        if (columnIndex != -1) {
            while (cursor.moveToNext()) {
                medicines.add(cursor.getString(columnIndex));
            }
        }
        cursor.close();
        return medicines;
    }
    public List<String> getAllPatientNames() {
        List<String> patientNames = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + TABLE_PATIENTS, null);

        while (cursor.moveToNext()) {
            patientNames.add(cursor.getString(0));
        }
        cursor.close();
        return patientNames;
    }

    public boolean createPrescription(String patientName, String medicine, String dosage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("patient_name", patientName);
        contentValues.put("medicine", medicine);
        contentValues.put("dosage", dosage);

        long result = db.insert(TABLE_PRESCRIPTIONS, null, contentValues);
        return result != -1;
    }

}