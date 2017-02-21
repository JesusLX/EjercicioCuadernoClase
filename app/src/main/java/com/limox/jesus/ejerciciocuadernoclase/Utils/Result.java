package com.limox.jesus.ejerciciocuadernoclase.Utils;

import com.limox.jesus.ejerciciocuadernoclase.Pojo.Incidence;
import com.limox.jesus.ejerciciocuadernoclase.Pojo.Student;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by usuario on 13/02/17.
 */
public class Result implements Serializable {
    boolean code;
    int status;
    String message;
    ArrayList<Student> students;
    ArrayList<Incidence> incidences;
    int last;
    public boolean getCode() {return code;}
    public void setCode(boolean code) { this.code = code;}
    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public ArrayList<Student> getStudents() { return students; }
    public void setStudents(ArrayList<Student> students) { this.students = students; }
    public ArrayList<Incidence> getIncidences() { return incidences; }
    public void setIncidences(ArrayList<Incidence> incidences) { this.incidences = incidences; }
    public int getLast() { return last; }
    public void setLast(int last) { this.last = last; }
}
