package com.example.project_111.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_111.Entity.Student;
import com.example.project_111.Repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public boolean isTimingAvailable(String course, Long teacherId, String timing) {
        List<Student> students = studentRepository.findByCourseAndTeacherIdAndTiming(course, teacherId, timing);
        return students.isEmpty();
    }

    public String save(Student student) {
        if (isTimingAvailable(student.getCourse(), student.getTeacherId(), student.getTiming())) {
            studentRepository.save(student);
            return "Student registered successfully!";
        } else {
            return "Selected timing is not available. Kindly select another timing.";
        }
    }

    public String updateTiming(Long studentId, String newTiming) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (isTimingAvailable(student.getCourse(), student.getTeacherId(), newTiming)) {
                // Check if timing change is allowed (before 12 hours)
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                try {
                    Date selectedTimeDate = sdf.parse(student.getTiming());
                    long selectedTime = selectedTimeDate.getTime();
                    if (selectedTime - currentTime >= 12 * 60 * 60 * 1000) {
                        student.setTiming(newTiming);
                        studentRepository.save(student);
                        return "Timing updated successfully!";
                    } else {
                        return "Sorry, only possible before 12 hours.";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return "Error parsing timing.";
                }
            } else {
                return "No other timing available.";
            }
        } else {
            return "Student not found.";
        }
    }
}
