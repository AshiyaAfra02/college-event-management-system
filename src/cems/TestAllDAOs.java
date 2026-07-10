package cems;

import java.sql.Timestamp;
import java.util.List;

public class TestAllDAOs {
    public static void main(String[] args) {
        System.out.println("DB test starting...");

        // Test DBconnection
        try {
            DBconnection.getConnection().close();
            System.out.println("DB connected OK.");
        } catch (Exception e) { e.printStackTrace(); }

        // Users
        System.out.println("Users:");
        List<User> users = UserDAO.getAllUsers();
        for (User u : users) {
            System.out.println(u.getUserId() + " | " + u.getUsername() + " | " + u.getFullName() + " | " + u.getRole());
        }

        // Add a test user (if needed)
        boolean added = UserDAO.addUser("testuser_"+System.currentTimeMillis()%1000, "pwd123", "Test User", "t@test.com", "student", 1);
        System.out.println("Added test user? " + added);

        // Venues
        System.out.println("Venues:");
        List<Venue> vs = VenueDAO.getAllVenuesList();
        for (Venue v : vs) System.out.println(v.getVenueId() + " - " + v.getName() + " cap:" + v.getCapacity());

        // Departments
        System.out.println("Departments:");
        List<Department> ds = DepartmentDAO.getAllDepartments();
        for (Department d : ds) System.out.println(d.getDeptId() + " - " + d.getName());

        // Events
        System.out.println("Events:");
        List<Event> evs = EventDAO.getAllEvents();
        for (Event e : evs) {
            System.out.println(e.getEventId() + " | " + e.getTitle() + " | seats_left:" + e.getSeatsLeft());
        }

        // Add a test event
        boolean evAdded = EventDAO.addEvent("Test Event "+System.currentTimeMillis()%1000, "Desc", 2,
                vs.size()>0?vs.get(0).getVenueId():1, new Timestamp(System.currentTimeMillis()+86400000), 50);
        System.out.println("Added test event? " + evAdded);

        // Registration: register first student for first event (if exists)
        List<Event> all = EventDAO.getAllEvents();
        List<User> allUsers = users;
        int firstEventId = all.size()>0?all.get(0).getEventId():-1;
        int firstStudentId = -1;
        for (User u : users) if ("student".equals(u.getRole())) { firstStudentId = u.getUserId(); break; }

        if (firstEventId!=-1 && firstStudentId!=-1) {
            boolean reg = RegistrationDAO.registerStudent(firstEventId, firstStudentId);
            System.out.println("Registered student? " + reg);
            List<Registration> regs = RegistrationDAO.getRegistrationsByStudent(firstStudentId);
            System.out.println("My regs count: " + regs.size());
        } else System.out.println("No event or student to test registration.");

        // Notifications
        boolean n = NotificationDAO.addNotification(null, "System test notification");
        System.out.println("Added notification? " + n);

        // Final printouts
        System.out.println("Final events after operations:");
        evs = EventDAO.getAllEvents();
        for (Event e : evs) System.out.println(e.getEventId() + " | " + e.getTitle() + " | seats_left:" + e.getSeatsLeft());

        System.out.println("DB tests finished.");
    }
}
