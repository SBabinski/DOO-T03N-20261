package model.entities;

public class User {

    // Attribute
    private String nickName;

    // Constructor
    public User(String nickName) {
        setNickName(nickName);
    }

    // Getter
    public String getNickName() {
        return nickName;
    }

    // Setter
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    // Util
    @Override
    public String toString() {
        return nickName;
    }
}
