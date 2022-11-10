public class Admin extends User
{
    public static final int TYPE = 1;
    Admin(int id, String username, String name, int asurite, String email, int phoneNumber) {
        super(id, username, name, Admin.TYPE, asurite, email, phoneNumber);
    }
}