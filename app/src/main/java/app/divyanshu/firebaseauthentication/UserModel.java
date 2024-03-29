package app.divyanshu.firebaseauthentication;

import com.google.firebase.database.Exclude;

public class UserModel {



    public UserModel(String email, String name, String number, String password, String gender,String StoredImageName,String user_id) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.password = password;
        this.gender = gender;
        this.StoredImageName = StoredImageName;
        this.user_id=user_id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   private   String email;
    private  String name;
    private String number;
    private String password;
    private  String gender;

    @Exclude
    public String getStoredImageName() {
        return StoredImageName;
    }

    @Exclude
    public void setStoredImageName(String storedImageName) {
        StoredImageName = storedImageName;
    }

    String StoredImageName;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    String user_id;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
