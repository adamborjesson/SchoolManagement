import entity.Course;
import entity.Education;
import entity.Student;
import entity.Teacher;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.persistence.*;
import java.util.ConcurrentModificationException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

public class SchoolManagement {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    static Input i = new Input();

    public void addTeacher() {
        System.out.println("Existing teachers");
        showAllTeachers();
        System.out.println("Name of new teacher");
        System.out.print("Answer: ");
        String name = i.stringInput();

        Teacher t = new Teacher(name);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
    }

    public void addStudent() {
        System.out.println("Existing students");
        showAllStudents();
        System.out.println("Name of new student");
        System.out.print("Answer: ");
        String name = i.stringInput();

        Student s = new Student(name);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();
    }

    public void showAllTeachers() {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Teacher> q = em.createQuery("select t from Teacher t", Teacher.class);
        q.getResultStream().forEach(System.out::println);
        em.close();
    }

    public void connectTeacherToCourse() {
        System.out.println("Id of teacher");
        showAllTeachers();
        System.out.print("Answer: ");
        int teacherId = i.intInput();

        System.out.println("Id of course");
        showAllCources();
        System.out.print("Answer: ");
        int courseId = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();

            Teacher t = em.find(Teacher.class, teacherId);

            em.getTransaction().begin();
            t.addCourse(em.find(Course.class, courseId));
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("Teacher or course is null");
        }

    }

    public void showAllCources() {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Course> q = em.createQuery("select c from Course c", Course.class);
        q.getResultStream().forEach(System.out::println);
        em.close();
    }

    public void showAllEducations() {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Education> q = em.createQuery("select e from Education e", Education.class);
        q.getResultStream().forEach(System.out::println);
        em.close();
    }

    public void connectCourseToEducation() {

        System.out.println("Id of education");
        showAllEducations();
        System.out.print("Answer: ");
        int educationId = i.intInput();

        System.out.println("Id of course");
        showAllCources();
        System.out.print("Answer: ");
        int courseId = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, educationId);

            em.getTransaction().begin();
            e.addCourse(em.find(Course.class, courseId));
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("Education or course is null");
        }catch(RollbackException r) {
            System.out.println("Duplicate entry");
        }

    }

    public void addCourse() {
        System.out.println("Existing courses");
        showAllCources();
        System.out.println("Name of new course");
        System.out.print("Answer: ");
        String name = i.stringInput();

        Course c = new Course(name);

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    public void addEducation() {
        System.out.println("Existing educations");
        showAllEducations();
        System.out.println("Name of new education");
        System.out.print("Answer: ");
        String name = i.stringInput();

        Education e = new Education(name);

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        em.close();
    }

    public void showAllStudentsOfEducation() {

        System.out.println("Id of education");

        showAllEducations();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, id);

            List<Student> students = e.getStudents();

            System.out.println();
            for (Student s : students) {
                System.out.println(s);
            }

            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such educationid");
        }

    }

    public void connectStudentToEducation() {

        System.out.println("Id of student");
        showAllStudents();
        System.out.print("Answer: ");
        int studentId = i.intInput();

        System.out.println("Id of education");
        showAllEducations();
        System.out.print("Answer: ");
        int educationId = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, educationId);

            em.getTransaction().begin();
            e.addStudent(em.find(Student.class, studentId));
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("Student or education is null");
        }catch(RollbackException r) {
            System.out.println("Duplicate entry");
        }
    }

    public void showTeachersOfStudent() {
        System.out.println("Id of student");
        showAllStudents();
        System.out.print("Answer: ");
        int id = i.intInput();
        Student s = null;

        try {
            EntityManager em = emf.createEntityManager();
            s = em.find(Student.class, id);
            Education e = s.getEducation();
            List<Course> c = e.getCourses();
            for (Course course : c) {
                System.out.println("Teacher: " + course.getTeacher().getName());

            }
        }catch(NullPointerException e){
                System.out.println("No such studentid");
            }


    }

    private void showAllStudents() {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Student> q = em.createQuery("select s from Student s", Student.class);
        q.getResultStream().forEach(System.out::println);
        em.close();
    }

    public void removeEducation() {

        System.out.println("Id of education");
        showAllEducations();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, id);
            List<Student> s = e.getStudents();
            for (Student student : s) {
                disconnectStudentFromEducation(student.getId());
            }

            em.getTransaction().begin();
            em.remove(e);
            em.getTransaction().commit();
            em.close();
        }catch(Exception e) {
            System.out.println("No such educationid");
        }
    }

    public void disconnectStudentFromEducation(int id) {


                EntityManager em = emf.createEntityManager();
                Student s = em.find(Student.class, id);

                if (s != null) {
                    em.getTransaction().begin();
                    s.setEducation(null);
                    em.getTransaction().commit();
                    em.close();
                } else if(s == null){
                    System.out.println("Something went wrong");
                }



    }

    public void findStudent() {
            System.out.println("Id of student");
            showAllStudents();
            System.out.print("Answer: ");

               int id = i.intInput();

                    disconnectStudentFromEducation(id);


    }

    public void studentsWithoutEducation() {

        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select s from Student s where s.education is null");
        System.out.println(q.getResultStream().count());

        em.close();

    }

    public void numberOfCoursesForEducation() {

        System.out.println("Id of education");
        showAllEducations();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, id);

            List<Course> course = e.getCourses();
            System.out.println("Courses: " + course.size());

            System.out.println("And they are: ");

            for (Course c : course) {
                System.out.println(c.getName());
            }

            em.close();
        }catch(Exception e) {
            System.out.println("No such educationid");
        }
    }

    public void CoursesWithoutTeacher() {

        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select c from Course c where c.teacher is null");

        System.out.println("Number of courses without teacher are: "+q.getResultStream().count());

        if(q.getResultStream().count() > 0) {
            System.out.println("And they are: ");
            q.getResultStream().forEach(System.out::println);
        }
        em.close();
    }

    public void disconnectTeacherFromCourse() {

        System.out.println("Id of course");
        showAllCources();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            Course c = em.find(Course.class, id);

            em.getTransaction().begin();
            c.setTeacher(null);
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such courseid");
        }
    }

    public void NumberOfStudentsPerEducation() {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Education> q = em.createQuery("select e from Education e", Education.class);
        List<Education> education = q.getResultList();
        for (Education e : education) {
            System.out.println(e.getName()+": "+e.getStudents().size()+" students");
            List<Student> students = e.getStudents();

            if(students.size() > 0) {
                System.out.println();
                System.out.println("Students are:");
                for (Student s : students) {
                    System.out.println(s.getName());
                }

            }
            System.out.println();

        }
        em.close();
    }

    public void updateStudent() {

        System.out.println("Id of student");
        showAllStudents();
        System.out.print("Answer: ");
        int id = i.intInput();
        System.out.println("New name");
        System.out.print("Answer: ");
        String name = i.stringInput();

        try {
            EntityManager em = emf.createEntityManager();
            Student s = em.find(Student.class, id);

            em.getTransaction().begin();
            s.setName(name);
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such studentid");
        }

    }

    public void updateEducation() {

        System.out.println("Id of education");
        showAllEducations();
        System.out.print("Answer: ");
        int id = i.intInput();
        System.out.println("New name");
        System.out.print("Answer: ");
        String name = i.stringInput();

        try {
            EntityManager em = emf.createEntityManager();
            Education e = em.find(Education.class, id);

            em.getTransaction().begin();
            e.setName(name);
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such educationid");
        }

    }

    public void updateCourse() {

        System.out.println("Id of course");
        showAllCources();
        System.out.print("Answer: ");
        int id = i.intInput();

        System.out.println("New name");
        System.out.print("Answer: ");
        String name = i.stringInput();

        try {
            EntityManager em = emf.createEntityManager();
            Course c = em.find(Course.class, id);

            em.getTransaction().begin();
            c.setName(name);
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such courseid");
        }
    }




    public void deleteStudent() {

        System.out.println("Id of student");
        showAllStudents();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Student.class, id));
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such studentid");
        }
    }

    public void deleteCourse() {

        System.out.println("Id of course");
        showAllCources();
        System.out.print("Answer: ");
        int id = i.intInput();

        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Course.class, id));
            em.getTransaction().commit();
            em.close();
        }catch(NullPointerException e) {
            System.out.println("No such courseid");
        }
    }
}
