public class Admin extends User
{
    public static final int TYPE = 1;
    public static final String TYPE_TEXT = "Admin";
    Admin(int id, String username, String name, int asurite, String email, String phoneNumber) {
        super(id, username, name, Admin.TYPE, asurite, email, phoneNumber);
    }
}
