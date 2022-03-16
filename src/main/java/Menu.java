
public class Menu {


    public static int menu() {

        Input i = new Input();

        System.out.println();
        System.out.println("Make your choice");
        System.out.println("-----------------");
        System.out.println("1. Add education");
        System.out.println("2. Add course");
        System.out.println("3. Add student");
        System.out.println("4. Add teacher");
        System.out.println("5. Connect course to education");
        System.out.println("6. Show all educations");
        System.out.println("7. Show all courses");
        System.out.println("8. Add teacher to course");
        System.out.println("9. Show all teachers");
        System.out.println("10. Show all students for an education");
        System.out.println("11. Connect student to education");
        System.out.println("12. Show teachers of student");
        System.out.println("13. Remove education");
        System.out.println("14. Remove student from education");
        System.out.println("15. Check how many students don't have an education");
        System.out.println("16. Check number of courses for an education");
        System.out.println("17. Check number of courses with no teacher");
        System.out.println("18. Remove teacher from course");
        System.out.println("19. Check number of students per education");
        System.out.println("20. Update student");
        System.out.println("21. Update education");
        System.out.println("22. Update course");
        System.out.println("23. Remove student");
        System.out.println("24. Remove course");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Answer: ");

        int choice = i.intInput();

        return choice;
    }


}

