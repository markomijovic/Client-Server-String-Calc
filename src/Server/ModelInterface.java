package Server;

public class ModelInterface {

    private String myString;
    public String getMyString() {
        return myString;
    }
    public void setMyString(String myString) {
        this.myString = myString;
    }

    public void concatenate (String s1, String s2)
    {
        setMyString(s1 + s2);
    }
    public Boolean invalidInput (String s1, String s2) {
        return (s1.isEmpty() || s2.isEmpty());
    }
    public void toUpper() {setMyString(getMyString().toUpperCase());}
    public void toLower() {setMyString(getMyString().toLowerCase());}
}
