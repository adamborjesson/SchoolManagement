

public class Main {
    static boolean loop = true;

    public static void main(String[] args) {


        Menu m = new Menu();
        SchoolManagement s = new SchoolManagement();


        while (loop) {

            int choice = m.menu();

            switch (choice) {

                case 1 -> s.addEducation();
                case 2 -> s.addCourse();
                case 3 -> s.addStudent();
                case 4 -> s.addTeacher();
                case 5 -> s.connectCourseToEducation();
                case 6 -> s.showAllEducations();
                case 7 -> s.showAllCources();
                case 8 -> s.connectTeacherToCourse();
                case 9 -> s.showAllTeachers();
                case 10 -> s.showAllStudentsOfEducation();
                case 11 -> s.connectStudentToEducation();
                case 12 -> s.showTeachersOfStudent();
                case 13 -> s.removeEducation();
                case 14 -> s.findStudent();
                case 15 -> s.studentsWithoutEducation();
                case 16 -> s.numberOfCoursesForEducation();
                case 17 -> s.CoursesWithoutTeacher();
                case 18 -> s.disconnectTeacherFromCourse();
                case 19 -> s.NumberOfStudentsPerEducation();
                case 20 -> s.updateStudent();
                case 21 -> s.updateEducation();
                case 22 -> s.updateCourse();
                case 23 -> s.deleteStudent();
                case 24 -> s.deleteCourse();

                case 0 -> loop = false;
                default -> System.out.println("Invalid number");
            }


            }



    }




}
