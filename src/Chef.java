
public class Chef extends User
{
    public static final int TYPE = 2;
    Chef(int id, String username, String name, int asurite, String email, int phoneNumber) {
        super(id, username, name, Chef.TYPE, asurite, email, phoneNumber);
    }
}
