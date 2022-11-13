
public class Chef extends User
{
    public static final int TYPE = 2;
    public static final String TYPE_TEXT = "Chef";

    Chef(int id, String username, String name, int asurite, String email, String phoneNumber) {
        super(id, username, name, Chef.TYPE, asurite, email, phoneNumber);
    }
}
