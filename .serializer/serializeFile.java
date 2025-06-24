import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class serializeFile {
    String fileName = "user.txt";
    void writeObject(Employee user){
        try {
            
            FileOutputStream outFile = new FileOutputStream(fileName);
            ObjectOutputStream outStream = new ObjectOutputStream(outFile);
            System.out.println("im writing \n" + user);
            outStream.writeObject(user);
            outStream.close();
            outFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void readFile(){
        try{

            FileInputStream inFile = new FileInputStream(fileName);
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            Employee user = (Employee) inStream.readObject();
            System.out.println("i readed \n" + user);
            System.out.println(
                user.name + "\n" +
                user.username + "\n" +
                user.password + "\n" +
                user.address + "\n" +
                user.years + "\n" +
                user.skills.skillMap.keySet()
            );
            inStream.close();
            inFile.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        serializeFile file = new serializeFile();
        try {
            
            file.writeObject(Employee.getEmployee());
            int sleepTime = 5000;
            System.out.println("wait " + sleepTime/1000 + "secs to read");
            Thread.sleep(sleepTime);
            file.readFile();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User implements Serializable // Serializable is just a marker interface 
                                // that just marks any object that it is serializable now
{
    private static final long serialVersionUID = 1L; // used to identify the same object even 
                                            // if some changes are made in class class will be identified by UID
    String name;
    String username;
    transient String password; // won't save transient (cause security reasons)
    static String address; // no need to save (cause its hardcoded or any value common to all)

    User(){
        this("pineapple", "pass123");
    }

    User(String name, String password){
        this.name = name;
        this.username = this.name + "user";
        this.password = password;
        address = "garden";
    }

    // just for knowledge 
    public static List<User> users = IntStream.rangeClosed(1, 10)
    .mapToObj(i -> {
        return new User("user"+i, "pass@"+i);
    }).collect(Collectors.toList());
}

class Employee extends User// child of serialized parent don't need to serialized again
{
    private static final long serialVersionUID = 1L;  // if UID not present it'll have a generated UID by default
                                                     // parent's UID won't affect it anyway
    Skill skills;
    String years;

    public Employee(Skill skills, String years, User user) {
        super(user.name, user.password);
        this.skills = skills;
        this.years = years;
    }
    public Employee(String name, String password, Skill skills, String years) {
        super(name, password);
        this.skills = skills;
        this.years = years;
    }

    public static Employee getEmployee(){
        Map<String, String> map = new HashMap<>();
        map.put("java", "intermediate");
        map.put("python", "expert");
        map.put("ts", "beginner");
        Skill skill = new Skill(map);
        Employee emp = new Employee(skill, "5", new User());
        return emp;
    }
}

class Skill implements Serializable // needs to be serialized cause saving as an argument in Employee class
{
    private static final long serialVersionUID = 2L; // this can be same or different doesn't metter
    Map<String,String> skillMap;
    public Skill(Map<String,String> skillMap) {
        this.skillMap = skillMap;
    }
}